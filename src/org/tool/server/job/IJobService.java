package org.tool.server.job;

public interface IJobService {
	
	String work(IJob job, IJobInfo jobInfo) throws Exception;
	
	void delete(String group) throws Exception;
	
	void delete(String group, String name) throws Exception;

}
