package org.tool.server.io;

/**
 * A client of net connect.
 * @author 	fuhuiyuan
 */
public interface INetClient<T> {
	
	/**
	 * Connect a server.
	 * @throws Exception
	 */
	void connect(String ip, int port) throws Exception;
	/**
	 * Send data of byte array.
	 * @param 	data
	 * 			T
	 */
	void send(T data);
	/**
	 * Close the connect.
	 * @throws 	Exception
	 */
	void close() throws Exception;

}
