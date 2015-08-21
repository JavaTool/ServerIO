package io;

/**
 * 包处理器，处理客户端的上传包用的
 * @author 	Jeffrey
 */
public interface PacketHandler {
	
	/**
	 * @note 系统在处理客户端上传包的时候将会调用此方法，传入上传的包以及包所在的客户端Session
	 * @param packet 客户端上传的包
	 * @param session 包所属的客户端Session
	 * @throws Exception 如果处理时出错，可以抛出异常
	 */
	void handle(Packet packet,ClientSession session) throws Exception;
	
}
