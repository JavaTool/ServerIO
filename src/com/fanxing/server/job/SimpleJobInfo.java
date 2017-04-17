package com.fanxing.server.job;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

public class SimpleJobInfo implements IJobInfo {
	
	private String name;
	
	private String group;
	
	private Map<String, Object> attributes;
	
	private Date startTime;
	
	private Date endTime;
	
	private long repeatInterval;
	
	private int repeatCount;
	
	public SimpleJobInfo() {
		attributes = Maps.newHashMap();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public long getRepeatInterval() {
		return repeatInterval;
	}

	@Override
	public int getRepeatCount() {
		return repeatCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Override
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
