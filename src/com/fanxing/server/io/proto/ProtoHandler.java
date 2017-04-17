package com.fanxing.server.io.proto;

import static java.lang.System.currentTimeMillis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.dispatch.IContent;
import com.fanxing.server.io.dispatch.IContentHandler;
import com.fanxing.server.ioc.IOC;
import com.fanxing.server.ioc.IOCBean;
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
	
	private static final String REQUEST_HEAD = "MICS_";
	
	private final IMessageIdTransform messageIdTransform;
	
	private final String noProcessorError;

	private int longTime;
	
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
		long time = currentTimeMillis();
		int messageId = content.getMessageId();
		Response response = methods.containsKey(messageId) ? methods.get(messageId).invoke(content) : createNoProcessorResponse(content);
		
		time = currentTimeMillis() - time;
		String sessionId = content.getSessionId();
		if (time > longTime) {
			log.warn("Too long time {} ms on {}, sessionId={}", time + "/" + longTime, messageId, sessionId);
		} else {
			log.info("Use time {} ms on {}, sessionId={}", time, messageId, sessionId);
		}

		response.build(time, content.getSerial());
	}

	private Response createNoProcessorResponse(IContent content) throws Exception {
		log.error("NoProcessor : {}.", content.getMessageId());
		return createErrorResponse(content, noProcessorError);
	}

	private Response createErrorResponse(IContent content, String error) throws Exception {
		return errorHandler.createErrorResponse(new RedirectRequest(content), error);
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
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
		
		private final Constructor<Request> requestConstructor;
		
		private final Constructor<Response> responseConstructor;
		
		@SuppressWarnings("unchecked")
		public ProcessorMethod(Object processor, Method method) throws Exception {
			this.processor = processor;
			this.method = method;
			Class<?>[] types = method.getParameterTypes();
			requestConstructor = (Constructor<Request>) types[0].getConstructor(IContent.class);
			responseConstructor = (Constructor<Response>) types[1].getConstructor(Request.class);
		}
		
		public Response invoke(IContent content) throws Exception {
			try {
				Request request = requestConstructor.newInstance(content);
				Response response = responseConstructor.newInstance(request);
				method.invoke(processor, request, response);
				return response;
			} catch (Exception e) {
				log.error("", e);
				String error = e.getCause() == null ? null : e.getCause().getMessage();
				error = error == null || error.length() == 0 ? "Unknow exception." : error;
				return createErrorResponse(content, error);
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
