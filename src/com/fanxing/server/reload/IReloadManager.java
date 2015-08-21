package com.fanxing.server.reload;

/**
 * 加载管理器
 * @author 	fuhuiyuan
 */
public interface IReloadManager<T> {
	
	/**
	 * 添加加载器
	 * @param 	t
	 * 			加载器
	 */
	void add(T t);
	/**
	 * 重新加载
	 * @param 	arg
	 * 			加载参数
	 * @throws 	Exception
	 */
	void reload(String arg) throws Exception;

}
