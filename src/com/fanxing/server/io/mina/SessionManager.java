package com.fanxing.server.io.mina;

public interface SessionManager {
	
	void add(ClientSession session);
	
	void remove(ClientSession session);
	
	TrustIp getTrustIp();

}
