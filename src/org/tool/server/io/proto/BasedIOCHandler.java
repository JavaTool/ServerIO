package org.tool.server.io.proto;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.message.IMessage;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.message.IMessageSender;
import org.tool.server.io.message.MessageHandler;
import org.tool.server.io.message.MessageSender;
import org.tool.server.ioc.IOC;
import org.tool.server.ioc.IOCBean;
import org.tool.server.thread.IMessagePackage;
import org.tool.server.thread.IMessagePackageHandler;
import org.tool.server.thread.IMessageProcessor;
import org.tool.server.thread.IMessageProcessorFactory;
import org.tool.server.thread.IThreadType;
import org.tool.server.thread.MessageProcessorGroup;
import org.tool.server.utils.StringUtil;

import com.google.common.collect.ClassToInstanceMap;

import gnu.trove.impl.unmodifiable.TUnmodifiableIntObjectMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public abstract class BasedIOCHandler extends MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(BasedIOCHandler.class);
	
	private static final String METHOD_HEAD = "PROCESS";
	
	private static final String REQUEST_HEAD = "MI_CS";
	
	private static final String LOG_RECEIVED = "Net {} received : [MessageId : {}({})] [SessionId : {}] [Ip : {}]";
	
	private static final IThreadType DEFAULT_THREAD_ID = () -> { return "DEFAULT_THREAD"; };
	
	private final IMessageIdTransform messageIdTransform;
	
	private final ProcessorIOC ioc;
	
	private IMessageProcessor<IMessagePackage> messageProcessor;
	
	private TIntObjectMap<ProcessorMethod> methods;
	
	private TIntObjectMap<IThreadType> threadTypes;
	
	private IErrorHandler errorHandler;
	
	public BasedIOCHandler(IMessageIdTransform messageIdTransform) {
		this.messageIdTransform = messageIdTransform;
		ioc = new ProcessorIOC();
	}

	@Override
	protected void logReceive(int messageId, ISender sender) {
		log.info(LOG_RECEIVED, sender.getNetType(), messageIdTransform.transform(messageId), messageId, sender.getSessionId(), sender.getIp());
	}

	public void load(String pkg, Class<? extends IOCBean> annotation, String type, ClassToInstanceMap<Object> objects) throws Exception {
		ioc.load(pkg, annotation, type, objects);
		loadMethods();
	}
	
	public void loadMethods() throws Exception {
		TIntObjectMap<ProcessorMethod> methods = new TIntObjectHashMap<>();
		for (Class<?> clz: ioc.getAll().keySet()) {
			for (Method method : clz.getMethods()) {
				String key = StringUtil.uppercaseTo_(method.getName()).replace(METHOD_HEAD, REQUEST_HEAD);
				int messageId = messageIdTransform.transform(key);
				methods.put(messageId, new ProcessorMethod(ioc.getBean(clz), method));
			}
		}
		this.methods = new TUnmodifiableIntObjectMap<>(methods);
		
		IMessageProcessorFactory<IMessagePackage> factory = createMessageProcessorFactory(this::handleMessagePackage);
		messageProcessor = new MessageProcessorGroup(getThreadTypes(), factory);
	}
	
	protected abstract IMessageProcessorFactory<IMessagePackage> createMessageProcessorFactory(IMessagePackageHandler handler);
	
	private void handleMessagePackage(IMessagePackage messagePackage) {
		int messageId = messagePackage.getMessageId();
		int serial = messagePackage.getSerial();
		byte[] datas = messagePackage.getDatas();
		IMessageSender messageSender = messagePackage.getMessageSender();
		if (methods.containsKey(messageId)) {
			methods.get(messageId).invoke(messageId, serial, datas, messageSender);
		} else {
			log.error("Do not have processor handle message {}.", messageId);
		}
	}
	
	private class ProcessorMethod {
		
		private final Object processor;
		
		private final Method method;
		
		private final Method fromMethod;
		
		public ProcessorMethod(Object processor, Method method) throws Exception {
			this.processor = processor;
			this.method = method;
			Class<?>[] types = method.getParameterTypes();
			String firstType = types[0].getName();
			fromMethod = firstType.equals("int") ? null : Class.forName(firstType.replace("interfaces.I", "proto.")).getMethod("from", byte[].class);
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
			return ((IMessage) fromMethod.invoke(null, datas)).setSerial(serial);
		}
		
	}

	@Override
	protected void handle(int messageId, int serial, byte[] datas, IMessageSender messageSender) throws Exception {
		messageProcessor.put(new IMessagePackage() {
			
			@Override
			public int getSerial() {
				return serial;
			}
			
			@Override
			public IMessageSender getMessageSender() {
				return messageSender;
			}
			
			@Override
			public int getMessageId() {
				return messageId;
			}
			
			@Override
			public byte[] getDatas() {
				return datas;
			}
			
		});
	}
	
	public <X, Y extends X> void addProcessor(Class<X> clz, Y processor) {
		ioc.addProcessor(clz, processor);
	}
	
	private final class ProcessorIOC extends IOC {
		
		public <X, Y extends X> void addProcessor(Class<X> clz, Y processor) {
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

	public void setThreadTypes(TIntObjectMap<IThreadType> threadTypes) {
		this.threadTypes = new TUnmodifiableIntObjectMap<>(threadTypes);
	}
	
	private TIntObjectMap<IThreadType> getThreadTypes() {
		if (threadTypes == null) {
			TIntObjectMap<IThreadType> threadTypes = new TIntObjectHashMap<>();
			methods.forEachKey(messageId -> {
				threadTypes.put(messageId, DEFAULT_THREAD_ID);
				return true;
			});
			setThreadTypes(threadTypes);
		}
		return threadTypes;
	}

	@Override
	protected IMessageSender createMessageSender(ISender sender) {
		return new MessageSender(sender, errorHandler, messageIdTransform);
	}

}
