package org.tool.server.io.netty.server;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IConncetHandler;
import org.tool.server.io.message.IMessageIdTransform;

public interface INettyServerConfig {

	IConncetHandler getConncetHandler();
	
	IEncrypt getEncrypt();
	
	IMessageIdTransform getMessageIdTransform();

	int getParentThreadNum();

	int getChildThreadNum();

	int getPort();

	String getIp();

	long getReaderIdleTime();

	long getWriterIdleTime();

	long getAllIdleTime();

	int getSoBacklog();

}
