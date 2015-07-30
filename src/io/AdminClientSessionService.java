package io;

import org.apache.commons.configuration.Configuration;

public class AdminClientSessionService extends DirectClientSessionService {
	
	public AdminClientSessionService(Configuration config, PacketHandler handler, SessionManager sessionManager){
		super(config, handler, sessionManager);
	}
	
}
