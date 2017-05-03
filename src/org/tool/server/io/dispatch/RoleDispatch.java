package org.tool.server.io.dispatch;

import org.tool.server.io.dispatch.BlockDispatch;
import org.tool.server.io.message.IMessageHandler;

public class RoleDispatch extends BlockDispatch implements IRoleCounter {
	
	private final int key;
	
	private volatile int count;

	public RoleDispatch(IMessageHandler handler, int key) {
		super(handler);
		this.key = key;
	}
	
	@Override
	public synchronized void changeCount(boolean add) {
		count += add ? 1 : -1;
	}

	@Override
	public int getCount() {
		return count;
	}
	
	public int getKey() {
		return key;
	}

}
