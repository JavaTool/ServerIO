package com.fanxing.server.system;

interface ISystemInfo {
	
	int CPUTIME = 500;
	
	int PERCENT = 100;
	
	int getCpuRatio() throws Exception;
	
	int memoryUsage() throws Exception;

}
