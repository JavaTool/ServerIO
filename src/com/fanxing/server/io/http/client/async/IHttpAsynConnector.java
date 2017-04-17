package com.fanxing.server.io.http.client.async;

import static java.net.URLEncoder.encode;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fanxing.server.anthenticate.IEncrypt;
import com.google.common.collect.ImmutableMap;

/**
 * HTTP连接器
 * @author 	fuhuiyuan
 * @param 	<T>
 * 			请求返回对象类型
 */
public interface IHttpAsynConnector<T> {
	
	/** 空byte数组，用来填充可能为空的参数 */
	byte[] EMPTY_BYTES = new byte[0];
	/** 空参数字符串，用来填充可能为空的参数 */
	String EMPTY_PARAMS = "";
	/** 空头信息集合，用来填充可能为空的参数 */
	Map<String, Object> EMPTY_HEAD = ImmutableMap.of();
	
	/**
	 * 设置连接地址
	 * @param 	url
	 * 			地址
	 */
	void setUrl(String url);
	
	void setTimeout(int timeout);
	/**
	 * 设置响应码处理器
	 * @param 	handler
	 * 			响应码处理器
	 */
	void setResponseCodeHandler(IResponseCodeHandler handler);
	/**
	 * 获取Cookie
	 * @return	
	 */
	String getCookie();
	/**
	 * 设置加密算法
	 * @param 	encrypt
	 * 			加密算法
	 */
	void setEncrypt(IEncrypt encrypt);
	
	byte[] deEncrypt(byte[] src);
	
	void refresh();
	
	// POST
	
	void post(String params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler);
	
	default void post(String params, Map<String, Object> head, IResponseHandler<T> hanler) {
		post(params, EMPTY_BYTES, EMPTY_HEAD, hanler);
	}
	
	default void post(String params, byte[] bytes, IResponseHandler<T> hanler) {
		post(params, bytes, EMPTY_HEAD, hanler);
	}
	
	default void post(byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) {
		post(EMPTY_PARAMS, bytes, head, hanler);
	}
	
	default void post(String params, IResponseHandler<T> hanler) {
		post(params, EMPTY_HEAD, hanler);
	}
	
	default void post(byte[] bytes, IResponseHandler<T> hanler) {
		post(EMPTY_PARAMS, bytes, EMPTY_HEAD, hanler);
	}
	
	default void post(Map<String, Object> params, byte[] bytes, IResponseHandler<T> hanler) throws IOException {
		post(makeParams(params), bytes, EMPTY_HEAD, hanler);
	}
	
	default void post(Map<String, Object> params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		post(makeParams(params), bytes, head, hanler);
	}
	
	default void post(Map<String, Object> params, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		post(makeParams(params), head, hanler);
	}
	
	default void post(Map<String, Object> params, IResponseHandler<T> hanler) throws IOException {
		post(params, EMPTY_HEAD, hanler);
	}
	
	default void post(IResponseHandler<T> hanler) {
		post(EMPTY_PARAMS, EMPTY_BYTES, EMPTY_HEAD, hanler);
	}
	
	// GET
	
	void get(String params, Map<String, Object> head, IResponseHandler<T> hanler);
	
	default void get(String params, IResponseHandler<T> hanler) {
		get(params, EMPTY_HEAD, hanler);
	}
	
	default void get(Map<String, Object> params, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		get(makeParams(params), head, hanler);
	}
	
	default void get(Map<String, Object> params, IResponseHandler<T> hanler) throws IOException {
		get(params, EMPTY_HEAD, hanler);
	}
	
	default void get(IResponseHandler<T> hanler) {
		get(EMPTY_PARAMS, EMPTY_HEAD, hanler);
	}
	
	// PUT
	
	void put(String params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler);
	
	default void put(String params, Map<String, Object> head, IResponseHandler<T> hanler) {
		put(params, EMPTY_BYTES, head, hanler);
	}
	
	default void put(String params, byte[] bytes, IResponseHandler<T> hanler) {
		put(params, bytes, EMPTY_HEAD, hanler);
	}
	
	default void put(byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) {
		put(EMPTY_PARAMS, bytes, head, hanler);
	}
	
	default void put(String params, IResponseHandler<T> hanler) {
		put(params, EMPTY_HEAD, hanler);
	}
	
	default void put(byte[] bytes, IResponseHandler<T> hanler) {
		put(EMPTY_PARAMS, bytes, EMPTY_HEAD, hanler);
	}
	
	default void put(Map<String, Object> params, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		put(makeParams(params), head, hanler);
	}
	
	default void put(Map<String, Object> params, byte[] bytes, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		put(makeParams(params), bytes, head, hanler);
	}
	
	default void put(Map<String, Object> params, byte[] bytes, IResponseHandler<T> hanler) throws IOException {
		put(makeParams(params), bytes, EMPTY_HEAD, hanler);
	}
	
	default void put(Map<String, Object> params, IResponseHandler<T> hanler) throws IOException {
		put(params, EMPTY_HEAD, hanler);
	}
	
	default void put(IResponseHandler<T> hanler) {
		put(EMPTY_PARAMS, EMPTY_HEAD, hanler);
	}
	
	// DELETE
	
	void delete(String params, Map<String, Object> head, IResponseHandler<T> hanler);
	
	default void delete(String params, IResponseHandler<T> hanler) {
		delete(params, EMPTY_HEAD, hanler);
	}
	
	default void delete(Map<String, Object> params, Map<String, Object> head, IResponseHandler<T> hanler) throws IOException {
		delete(makeParams(params), head, hanler);
	}
	
	default void delete(Map<String, Object> params, IResponseHandler<T> hanler) throws IOException {
		delete(params, EMPTY_HEAD, hanler);
	}
	
	default void delete(IResponseHandler<T> hanler) {
		delete(EMPTY_PARAMS, EMPTY_HEAD, hanler);
	}
	
	
	public static String makeParams(Map<String, Object> params) throws IOException {
		StringBuilder builder = new StringBuilder("?");
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.append(entry.getKey()).append("=").append(encodeValue(entry.getValue())).append("&");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}
	
	public static String encodeValue(Object param) throws IOException {
		return encode(String.valueOf(param), "utf-8");
	}
	
	public static interface IResponseCodeHandler {
		
		void handleResponseCode(int code) throws Exception;
		
	}

}
