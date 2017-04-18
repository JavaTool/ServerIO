package org.tool.server.io.rpc;

public interface IRPCManager {
	
	String RMI = "RMI";
	
	String GRPC = "GRPC";
	
	void bind(String ip, int port, String name, Object service) throws Exception;
	
	<S> S getService(String ip, int port, String name) throws Exception;

}
