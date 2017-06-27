package org.tool.server.io.netty.server;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IMessageHandler;
import org.tool.server.io.message.IMessageIdTransform;

public interface INettyServerConfig {

	IMessageHandler getMessageHandler();
	
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
