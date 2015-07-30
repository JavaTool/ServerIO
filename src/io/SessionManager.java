package io;

public interface SessionManager {
	
	void add(ClientSession session);
	
	void remove(ClientSession session);
	
	TrustIp getTrustIp();

}
