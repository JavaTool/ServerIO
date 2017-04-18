package org.tool.server.shutdown;

public interface ShutdownExecutor extends IShutdown {
	
	void addShutdown(IShutdown shutdown);

}
