package org.tool.server.io.dispatch;

import java.util.List;

/**
 * 分配器管理器
 * @author 	fuhuiyuan
 */
public interface IDispatchManager extends IDispatch {
	
	void disconnect(ISender sender);
	
	void addAlone(String tag, int... messageIds);
	
	void addFire(List<Integer> messageIds);

}
