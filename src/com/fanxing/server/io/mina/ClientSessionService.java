package com.fanxing.server.io.mina;

public interface ClientSessionService {
	
	void addClientSession(ClientSession session);
	
	void removeClientSession(ClientSession session);
	
	String getAddress();
	
	int getPort();
	
	String getEncryptionKey();
	
	int getEncryptionLen();
	
	void setIOLog(IOLog log);
	
}
