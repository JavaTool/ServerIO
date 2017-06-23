package org.tool.server.thread;

import org.tool.server.io.message.IMessageSender;

public interface IMessagePackage {
	
	int getMessageId();
	
	int getSerial();
	
	byte[] getDatas();
	
	IMessageSender getMessageSender();

}
