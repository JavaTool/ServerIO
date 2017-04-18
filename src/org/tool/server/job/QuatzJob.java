package org.tool.server.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuatzJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(QuatzJob.class);
	
	private final JobContext context;
	
	public QuatzJob() {
		context = new JobContext();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			JobDataMap map = context.getTrigger().getJobDataMap();
			this.context.setMap(map);
			this.context.setName(context.getTrigger().getKey().getName());
			((IJob) map.get(getClass().getName())).execute(this.context);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	private static class JobContext implements IJobContext {
		
		private JobDataMap map;
		
		private String name;

		@Override
		public Object getAttribute(String key) {
			return map.get(key);
		}

		public void setMap(JobDataMap map) {
			this.map = map;
		}

		@Override
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
	}

}
