package org.tool.server.io.proto;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.IContentHandler;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.message.IMessage;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.message.IMessageSender;
import org.tool.server.io.message.MessageSender;
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

public class ProtoHandler extends IOC implements IContentHandler {

	private static final Logger log = LoggerFactory.getLogger(ProtoHandler.class);
	
	private static final String METHOD_HEAD = "process";
	
	private static final String REQUEST_HEAD = "MI_CS_";
	
	private static final String MESSAGE_SENDER_NAME = IMessageSender.class.getName();
	
	private final IMessageIdTransform messageIdTransform;
	
	private final String noProcessorError;
	
	private TIntObjectMap<ProcessorMethod> methods;
	
	private ListMultimap<String, Integer> aloneMethods;
	
	private TIntList fireMessages;
	
	private IErrorHandler errorHandler;
	
	public ProtoHandler(IMessageIdTransform messageIdTransform, String noProcessorError) {
		this.messageIdTransform = messageIdTransform;
		this.noProcessorError = noProcessorError;
	}

	@Override
	public void handle(byte[] bytes, ISender sender) throws Exception {
		try (DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))) {
			// 解析
			int serial = dis.readInt(); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
			int messageId = dis.readShort();
			byte[] datas = new byte[dis.available()];
			dis.read(datas);
			// 获取或创建消息发送器
			IMessageSender messageSender = sender.getAttribute(MESSAGE_SENDER_NAME, IMessageSender.class);
			if (messageSender == null) {
				messageSender = new MessageSender(sender);
				sender.setAttribute(MESSAGE_SENDER_NAME, IMessageSender.class, messageSender);
			}
			// 处理消息
			if (methods.containsKey(messageId)) {
				methods.get(messageId).invoke(messageId, serial, datas, messageSender);
			} else {
				IMessage error = createNoProcessorResponse(messageId, serial);
				messageSender.send(error);
			}
		}
	}

	private IMessage createNoProcessorResponse(int messageId, int serial) throws Exception {
		log.error("NoProcessor : {}.", messageId);
		return createErrorResponse(messageId, serial, noProcessorError);
	}

	private IMessage createErrorResponse(int messageId, int serial, String error) throws Exception {
		return errorHandler.createErrorResponse(messageId, serial, error);
	}
	
	@Override
	public void load(String pkg, Class<? extends IOCBean> annotation, String type, ClassToInstanceMap<Object> objects) throws Exception {
		super.load(pkg, annotation, type, objects);
		loadMethods();
	}
	
	public void loadMethods() throws Exception {
		TIntObjectMap<ProcessorMethod> methods = new TIntObjectHashMap<>();
		ListMultimap<String, Integer> aloneMethods = LinkedListMultimap.create();
		TIntList fireMessages = new TIntLinkedList();
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
		this.methods = new TUnmodifiableIntObjectMap<>(methods);
		this.aloneMethods = ImmutableListMultimap.copyOf(aloneMethods);
		this.fireMessages = new TUnmodifiableIntList(fireMessages);
	}

	public <X, Y extends X> void addProcessor(Class<X> clz, Y processor) {
		beans.put(clz, processor);
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
	public void setErrorHandler(IErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
