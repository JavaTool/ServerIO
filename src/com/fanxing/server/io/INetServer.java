package com.fanxing.server.io;

import com.fanxing.server.shutdown.IShutdown;

/**
 * A server of net connect.
 * @author 	fuhuiyuan
 */
public interface INetServer extends IShutdown {
	
	/**
	 * Binding an ip and port, start this server.
	 * @throws 	Exception
	 */
	void bind() throws Exception;
	/**
	 * The port of this server.
	 * @return	port of this server
	 */
	int getPort();

}
