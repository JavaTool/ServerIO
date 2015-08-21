package io;

public interface Client {
	
	void setSession(ClientSession session);
	
	ClientSession getSession();
	
	void setDisconnecting();
	
	Packet createSessionKeyPacket();
	
}
