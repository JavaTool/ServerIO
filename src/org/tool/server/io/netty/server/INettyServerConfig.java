package org.tool.server.io.netty.server;

import org.tool.server.io.dispatch.IContentFactory;
import org.tool.server.io.dispatch.IDispatchManager;

public interface INettyServerConfig {

	IDispatchManager getDispatchManager();

	IContentFactory getNettyContentFactory();

	int getParentThreadNum();

	int getChildThreadNum();

	int getPort();

	String getIp();

	long getReaderIdleTime();

	long getWriterIdleTime();

	long getAllIdleTime();

	int getSoBacklog();

}
