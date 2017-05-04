package org.tool.server.io.proto;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.message.IMessage;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.message.IMessageSender;
import org.tool.server.io.message.MessageHandler;
import org.tool.server.ioc.IOC;
import org.tool.server.ioc.IOCBean;
import org.tool.server.utils.StringUtil;

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

public class BasedIOCHandler extends MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(BasedIOCHandler.class);
	
	private static final String METHOD_HEAD = "PROCESS";
	
	private static final String REQUEST_HEAD = "MI_CS";
	
	private final IMessageIdTransform messageIdTransform;
	
	private final ProcessorIOC ioc;
	
	private TIntObjectMap<ProcessorMethod> methods;
	
	private ListMultimap<String, Integer> aloneMethods;
	
	private TIntList fireMessages;
	
	private IErrorHandler errorHandler;
	
	public BasedIOCHandler(IMessageIdTransform messageIdTransform) {
		this.messageIdTransform = messageIdTransform;
		ioc = new ProcessorIOC();
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
				String key = StringUtil.uppercaseTo_(method.getName()).replace(METHOD_HEAD, REQUEST_HEAD);
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
			fromMethod = types.length == 1 ? null : Class.forName(types[0].getName().replace("interfaces.I", "proto.")).getMethod("from", byte[].class);
		}
		
		public void invoke(int messageId, int serial, byte[] datas, IMessageSender sender) {
			try {
				method.invoke(processor, fromMethod == null ? serial : createMessage(serial, datas), sender);
			} catch (Exception e) {
				log.error("", e);
				String error = e.getCause() == null ? null : e.getCause().getMessage();
				error = error == null || error.length() == 0 ? "Unknow exception." : error;
				sender.send(errorHandler.createErrorResponse(messageId, serial, error));
			}
		}
		
		private IMessage createMessage(int serial, byte[] datas) throws Exception {
			IMessage message = (IMessage) fromMethod.invoke(null, datas);
			message.setSerial(serial);
			return message;
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
			log.error("Do not have processor handle message {}.", messageId);
		}
	}
	
	public <X, Y extends X> void addProcessor(Class<X> clz, Y processor) {
		ioc.addProcessor(clz, processor);
	}
	
	private final class ProcessorIOC extends IOC {
		
		public  <X, Y extends X>void addProcessor(Class<X> clz, Y processor) {
			beans.put(clz, processor);
		}
		
	}
	
	/**
	 * 设置错误处理器
	 * @param 	errorHandler
	 * 			错误处理器
	 */
	public void setErrorHandler(IErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

}
