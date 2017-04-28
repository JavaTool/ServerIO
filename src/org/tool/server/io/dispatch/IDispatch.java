package org.tool.server.io.dispatch;

/**
 * 消息分配器
 * @author 	fuhuiyuan
 */
public interface IDispatch {
	
	/**
	 * 添加消息
	 * @param 	content
	 * 			消息内容
	 */
	void addDispatch(byte[] datas, ISender sender);
	/**
	 * 立即分配消息
	 * @param 	content
	 * 			消息内容
	 */
	void fireDispatch(byte[] datas, ISender sender) throws Exception;

}
