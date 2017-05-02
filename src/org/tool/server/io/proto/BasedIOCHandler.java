package org.tool.server.io.proto;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.message.IMessage;
import org.tool.server.io.message.IMessageHandler;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.message.IMessageSender;
import org.tool.server.io.message.MessageHandler;
import org.tool.server.ioc.IOC;
import org.tool.server.ioc.IOCBean;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import gnu.trove.impl.unmodifiable.TUnmodifiableIntList;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntObjectMap;
import gnu.trove.list.TIntList;
import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class BasedIOCHandler extends MessageHandler implements IMessageHandler {

	private static final Logger log = LoggerFactory.getLogger(BasedIOCHandler.class);
	
	private static final String METHOD_HEAD = "process";
	
	private static final String REQUEST_HEAD = "MI_CS_";
	
	private final IMessageIdTransform messageIdTransform;
	
	private final String noProcessorError;
	
	private final ProcessorIOC ioc;
	
	private TIntObjectMap<ProcessorMethod> methods;
	
	private ListMultimap<String, Integer> aloneMethods;
	
	private TIntList fireMessages;
	
	public BasedIOCHandler(IMessageIdTransform messageIdTransform, String noProcessorError) {
		this.messageIdTransform = messageIdTransform;
		this.noProcessorError = noProcessorError;
		ioc = new ProcessorIOC();
	}

	private IMessage createNoProcessorResponse(int messageId, int serial) throws Exception {
		log.error("NoProcessor : {}.", messageId);
		return createErrorResponse(messageId, serial, noProcessorError);
	}

	private IMessage createErrorResponse(int messageId, int serial, String error) throws Exception {
		return errorHandler.createErrorResponse(messageId, serial, error);
	}
	
	public void load(String pkg, Class<? extends IOCBean> annotation, String type, ClassToInstanceMap<Object> objects) throws Exception {
		ioc.load(pkg, annotation, type, objects);
		loadMethods();
	}
	
	public void loadMethods() throws Exception {
		TIntObjectMap<ProcessorMethod> methods = new TIntObjectHashMap<>();
		ListMultimap<String, Integer> aloneMethods = LinkedListMultimap.create();
		TIntList fireMessages = new TIntLinkedList();
		for (Class<?> clz: ioc.getAll().keySet()) {
			for (Method method : clz.getMethods()) {
				String key = method.getName().replace(METHOD_HEAD, REQUEST_HEAD);
				int messageId = messageIdTransform.transform(key);
				methods.put(messageId, new ProcessorMethod(ioc.getBean(clz), method));
				if (method.isAnnotationPresent(Alone.class)) {
					aloneMethods.put(clz.getSimpleName(), messageId);
				} else if (method.isAnnotationPresent(Fire.class)) {
					fireMessages.add(messageId);
				}
			}
		}
		this.methods = new TUnmodifiableIntObjectMap<>(methods);
		this.aloneMethods = ImmutableListMultimap.copyOf(aloneMethods);
		this.fireMessages = new TUnmodifiableIntList(fireMessages);
	}
	
	public TIntList getFireMessages() {
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
		
		public void invoke(int messageId, int serial, byte[] datas, IMessageSender sender) throws Exception {
			try {
//				if (fromMethod == null) {
//					method.invoke(processor, serial, sender);
//				} else {
//					IMessage message = (IMessage) fromMethod.invoke(null, datas);
//					method.invoke(processor, message, sender);
//				}
				method.invoke(processor, fromMethod == null ? serial : (IMessage) fromMethod.invoke(null, datas), sender);
			} catch (Exception e) {
				log.error("", e);
				String error = e.getCause() == null ? null : e.getCause().getMessage();
				error = error == null || error.length() == 0 ? "Unknow exception." : error;
				sender.send(createErrorResponse(messageId, serial, error));
			}
		}
		
	}

	public ListMultimap<String, Integer> getAloneMethods() {
		return aloneMethods;
	}

	@Override
	protected void handle(int messageId, int serial, byte[] datas, IMessageSender messageSender) throws Exception {
		if (methods.containsKey(messageId)) {
			methods.get(messageId).invoke(messageId, serial, datas, messageSender);
		} else {
			IMessage error = createNoProcessorResponse(messageId, serial);
			messageSender.send(error);
		}
	}
	
	public <X, Y extends X> void addProcessor(Y processor, Class<X> clz) {
		ioc.addProcessor(processor, clz);
	}
	
	private final class ProcessorIOC extends IOC {
		
		public  <X, Y extends X>void addProcessor(Y processor, Class<X> clz) {
			beans.put(clz, processor);
		}
		
	}

}