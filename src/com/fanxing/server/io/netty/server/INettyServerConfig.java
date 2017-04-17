package com.fanxing.server.io.netty.server;

import com.fanxing.server.io.dispatch.IContentFactory;
import com.fanxing.server.io.dispatch.IDispatchManager;

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
