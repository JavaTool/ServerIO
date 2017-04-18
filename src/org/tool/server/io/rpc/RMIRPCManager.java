package org.tool.server.io.rpc;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RMIRPCManager implements IRPCManager {
	
	private static final Logger log = LoggerFactory.getLogger(RMIRPCManager.class);
	
	private static class SingletonHolder {
		
        private static RMIRPCManager instance = new RMIRPCManager();
        
    }
	
	public static RMIRPCManager getInstance() {
		return SingletonHolder.instance;
	}
	
	private RMIRPCManager() {}

	@Override
	public void bind(String ip, int port, String name, Object service) throws Exception {
		Registry registry = LocateRegistry.createRegistry(port);
        registry.bind(name, (Remote) service);
        log.info("Bind rpc {}({}) on {}:{}.", name, service.getClass().getSimpleName(), ip, port);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> S getService(String ip, int port, String name) throws Exception {
		Registry registry = LocateRegistry.getRegistry(ip, port);
		return (S) registry.lookup(name);
	}

}
