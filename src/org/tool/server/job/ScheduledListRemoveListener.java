package org.tool.server.job;

public interface ScheduledListRemoveListener<T extends ScheduledObject> {
	
	void onRemove(T element);

}
