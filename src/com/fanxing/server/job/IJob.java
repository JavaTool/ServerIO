package com.fanxing.server.job;

public interface IJob {
	
	void execute(IJobContext context) throws Exception;

}
