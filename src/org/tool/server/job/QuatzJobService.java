package org.tool.server.job;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuatzJobService implements IJobService {
	
	private static final Logger log = LoggerFactory.getLogger(QuatzJobService.class);
	
	private static final String DEFAULT_GROUP = QuatzJobService.class.getName() + "_Group";
	
	private static final String DEFAULT_NAME = QuatzJobService.class.getName() + "_";
	
	private final SchedulerFactory schedulerFactory;
	
	private int index;
	
	public QuatzJobService() {
		schedulerFactory = new StdSchedulerFactory();
		index = 0;
	}

	@Override
	public String work(IJob job, IJobInfo jobInfo) throws Exception {
		String name = makeName(jobInfo);
		String group = makeGroup(jobInfo);
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setJobClass(QuatzJob.class);
		jobDetail.setKey(JobKey.jobKey(name, group));
		SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setStartTime(jobInfo.getStartTime());
        simpleTrigger.setRepeatInterval(jobInfo.getRepeatInterval());
        simpleTrigger.setRepeatCount(jobInfo.getRepeatCount());
        simpleTrigger.setEndTime(jobInfo.getEndTime());
        simpleTrigger.setName(name);
        simpleTrigger.setGroup(group);
        simpleTrigger.setJobName(name);
        simpleTrigger.setJobGroup(group);
        JobDataMap map = simpleTrigger.getJobDataMap();
        map.put(QuatzJob.class.getName(), job);
        jobInfo.getAttributes().forEach((k, v) -> map.put(k, v));
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
        log.info("Work [group={}],[name={}] start.", group, name);
        return name;
	}
	
	private String makeName(IJobInfo jobInfo) {
		String name = jobInfo.getName();
		return name == null || name.length() == 0 ? DEFAULT_NAME + addIndex() : name;
	}
	
	private String makeGroup(IJobInfo jobInfo) {
		String group = jobInfo.getGroup();
		return group == null || group.length() == 0 ? DEFAULT_GROUP : group;
	}
	
	private synchronized int addIndex() {
		return index++;
	}
	
	public static void main(String[] args) {
		IJobService jobService = new QuatzJobService();
		try {
			String group = "group";
			for (int i = 0;i < 10;i++) {
				SimpleJobInfo jobInfo = new SimpleJobInfo();
				jobInfo.setStartTime(new Date());
				jobInfo.setEndTime(new Date(jobInfo.getStartTime().getTime() + 1000));
				jobInfo.setRepeatInterval(500);
				jobInfo.setName("test_" + i);
				jobInfo.setGroup(group);
				jobInfo.getAttributes().put("key", i);
				jobInfo.setRepeatCount(SimpleJobInfo.REPEAT_INDEFINITELY);
				jobService.work(context -> System.out.println(context.getName() + " : " + context.getAttribute("key")), jobInfo);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void delete(String group) throws Exception {
		Scheduler scheduler = schedulerFactory.getScheduler();
		for (JobKey key : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
			delete(scheduler, key);
		}
	}

	@Override
	public void delete(String group, String name) throws Exception {
		Scheduler scheduler = schedulerFactory.getScheduler();
		delete(scheduler, JobKey.jobKey(name, group));
	}
	
	private static void delete(Scheduler scheduler, JobKey key) throws Exception {
		scheduler.deleteJob(key);
		log.info("Delete job {}.", key);
	}

}
