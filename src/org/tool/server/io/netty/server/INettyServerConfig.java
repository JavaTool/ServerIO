package org.tool.server.io.netty.server;

import java.io.DataOutputStream;

import org.tool.server.anthenticate.IDataAnthenticate;
import org.tool.server.io.message.IMessageHandler;

public interface INettyServerConfig {

	IMessageHandler getMessageHandler();
	
	IDataAnthenticate<byte[], DataOutputStream> getDataAnthenticate();

	int getParentThreadNum();

	int getChildThreadNum();

	int getPort();

	String getIp();

	long getReaderIdleTime();

	long getWriterIdleTime();

	long getAllIdleTime();

	int getSoBacklog();

}
