package com.fanxing.server.io.mina;

public interface Client {
	
	void setSession(ClientSession session);
	
	ClientSession getSession();
	
	void setDisconnecting();
	
	Packet createSessionKeyPacket();
	
}
