package org.tool.server.io.proto;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.IContent;
import org.tool.server.io.dispatch.IContentHandler;
import org.tool.server.ioc.IOC;
import org.tool.server.ioc.IOCBean;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ProtoHandler extends IOC implements IContentHandler {

	private static final Logger log = LoggerFactory.getLogger(ProtoHandler.class);
	
	private static final String METHOD_HEAD = "process";
	
	private static final String REQUEST_HEAD = "MI_CS_";
	
	private final IMessageIdTransform messageIdTransform;
	
	private final String noProcessorError;
	
	private Map<Integer, ProcessorMethod> methods;
	
	private ListMultimap<String, Integer> aloneMethods;
	
	private List<Integer> fireMessages;
	
	private IErrorHandler errorHandler;
	
	public ProtoHandler(IMessageIdTransform messageIdTransform, String noProcessorError) {
		this.messageIdTransform = messageIdTransform;
		this.noProcessorError = noProcessorError;
	}

	@Override
	public void handle(IContent content) throws Exception {
		int messageId = content.getMessageId();
		if (methods.containsKey(messageId)) {
			methods.get(messageId).invoke(content);
		} else {
			IMessage error = createNoProcessorResponse(content);
			content.getSender().send(error.toByteArray(), 0, error.getMessageId(), 0);
		}
	}

	private IMessage createNoProcessorResponse(IContent content) throws Exception {
		log.error("NoProcessor : {}.", content.getMessageId());
		return createErrorResponse(content, noProcessorError);
	}

	private IMessage createErrorResponse(IContent content, String error) throws Exception {
		return errorHandler.createErrorResponse(content, error);
	}
	
	@Override
	public void load(String pkg, Class<? extends IOCBean> annotation, String type, ClassToInstanceMap<Object> objects) throws Exception {
		super.load(pkg, annotation, type, objects);
		loadMethods();
	}
	
	public void loadMethods() throws Exception {
		Map<Integer, ProcessorMethod> methods = Maps.newHashMap();
		ListMultimap<String, Integer> aloneMethods = LinkedListMultimap.create();
		List<Integer> fireMessages = Lists.newLinkedList();
		for (Class<?> clz: beans.keySet()) {
			for (Method method : clz.getMethods()) {
				String key = method.getName().replace(METHOD_HEAD, REQUEST_HEAD);
				int messageId = messageIdTransform.transform(key);
				methods.put(messageId, new ProcessorMethod(beans.get(clz), method));
				if (method.isAnnotationPresent(Alone.class)) {
					aloneMethods.put(clz.getSimpleName(), messageId);
				} else if (method.isAnnotationPresent(Fire.class)) {
					fireMessages.add(messageId);
				}
			}
		}
		this.methods = ImmutableMap.copyOf(methods);
		this.aloneMethods = ImmutableListMultimap.copyOf(aloneMethods);
		this.fireMessages = ImmutableList.copyOf(fireMessages);
	}

	public <X, Y extends X> void addProcessor(Class<X> clz, Y processor) {
		beans.put(clz, processor);
	}
	
	public List<Integer> getFireMessages() {
		return fireMessages;
	}
	
	private class ProcessorMethod {
		
		private final Object processor;
		
		private final Method method;
		
		private final Method fromMethod;
		
		public ProcessorMethod(Object processor, Method method) throws Exception {
			this.processor = processor;
			this.method = method;
			Class<?>[] types = method.getParameterTypes();
			fromMethod = types.length == 1 ? null : Class.forName(types[0].getName().substring(1)).getMethod("from", byte[].class);
		}
		
		public void invoke(IContent content) throws Exception {
			try {
				if (fromMethod == null) {
					method.invoke(processor, new MessageSender(content));
				} else {
					IMessage request = (IMessage) fromMethod.invoke(null, content.getDatas());
					method.invoke(processor, request, new MessageSender(content));
				}
			} catch (Exception e) {
				log.error("", e);
				String error = e.getCause() == null ? null : e.getCause().getMessage();
				error = error == null || error.length() == 0 ? "Unknow exception." : error;
				IMessage response = createErrorResponse(content, error);
				content.getSender().send(response.toByteArray(), 0, response.getMessageId(), 0);
			}
		}
		
	}
	
	private static class MessageSender implements IMessageSender {
		
		private static final Map<Integer, IMessage> EMPTY_MESSAGES = Maps.newConcurrentMap();
		
		private static final byte[] EMPTY_DATAS = new byte[0];
		
		private final IContent content;
		
		public MessageSender(IContent content) {
			this.content = content;
		}

		@Override
		public void send(IMessage message) {
			try {
				content.getSender().send(message.toByteArray(), content.getSerial(), message.getMessageId(), 0);
			} catch (Exception e) {
				ProtoHandler.log.error("", e);
			}
		}

		@Override
		public String getSessionId() {
			return content.getSessionId();
		}

		@Override
		public void send(final int messageid) {
			IMessage message = EMPTY_MESSAGES.get(messageid);
			if (message == null) {
				message = new IMessage() {
					
					@Override
					public byte[] toByteArray() {
						return EMPTY_DATAS;
					}
					
					@Override
					public int getMessageId() {
						return messageid;
					}
					
				};
				EMPTY_MESSAGES.put(messageid, message);
			}
			send(message);
		}

		@Override
		public <X> X getAttribute(String key, Class<X> clz) {
			return content.getSender().getAttribute(key, clz);
		}
		
	}

	public ListMultimap<String, Integer> getAloneMethods() {
		return aloneMethods;
	}

	@Override
	public void setErrorHandler(IErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
