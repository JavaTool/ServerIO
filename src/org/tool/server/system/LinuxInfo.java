package org.tool.server.system;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.parseLong;
import static java.lang.Thread.sleep;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class LinuxInfo implements ISystemInfo {
	
	private static final String CPU_FILE = "/proc/stat";
	
	private static final String CPU_USER = "user";
	
	private static final int CPU_USER_INDEX = 1;
	
	private static final String CPU_NICE = "nice";

	private static final int CPU_NICE_INDEX = 2;
	
	private static final String CPU_SYSTEM = "system";
	
	private static final int CPU_SYSTEM_INDEX = 3;
	
	private static final String CPU_IDLE = "idle";
	
	private static final int CPU_IDLE_INDEX = 4;
	
	private static final String CPU_IOWAIT = "iowait";
	
	private static final int CPU_IOWAIT_INDEX = 5;
	
	private static final String CPU_IRQ = "irq";
	
	private static final int CPU_IRQ_INDEX = 6;
	
	private static final String CPU_SOFTIRQ = "softirq";
	
	private static final int CPU_SOFTIRQ_INDEX = 7;
	
	private static final String CPU_STEALSTOLEN = "stealstolen";
	
	private static final int CPU_STEALSTOLEN_INDEX = 8;
	
	private static final String CPU = "cpu";
	
	private static final String MEMORY_FILE = "/proc/meminfo";
	
	private static final String MEMORY_SPLIT = ":";
	
	private static final String MEMORY_FILE_UNIT = "kB";
	
	private static final String MEMORY_TOTAL = "MemTotal";
	
	private static final String MEMORY_FREE = "MemFree";
	
	private static final String MEMORY_BUFFERS = "Buffers";
	
	private static final String MEMORY_CACHED = "Cached";
	
	private static final String BLANK = "";

	@Override
	public int getCpuRatio() throws Exception {
		Map<String, String> map1 = cpuinfo();
		sleep(CPUTIME);
		Map<String, String> map2 = cpuinfo();
		
		long user1 = parseLong(map1.get(CPU_USER));
        long nice1 = parseLong(map1.get(CPU_NICE));
        long system1 = parseLong(map1.get(CPU_SYSTEM));
        long idle1 = parseLong(map1.get(CPU_IDLE));

        long user2 = parseLong(map2.get(CPU_USER));
        long nice2 = parseLong(map2.get(CPU_NICE));
        long system2 = parseLong(map2.get(CPU_SYSTEM));
        long idle2 = parseLong(map2.get(CPU_IDLE));

        long total1 = user1 + system1 + nice1;
        long total2 = user2 + system2 + nice2;
        float total = total2 - total1;

        long totalIdle1 = user1 + nice1 + system1 + idle1;
        long totalIdle2 = user2 + nice2 + system2 + idle2;
        float totalidle = totalIdle2 - totalIdle1;

        float cpusage = (total / totalidle) * 100;
        return (int) cpusage;
	}

    /**
     * 功能：CPU使用信息
     */
    private static Map<String, String> cpuinfo() throws Exception {
        Map<String, String> map = newHashMap();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(CPU_FILE)))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                if (line.equals(CPU)) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = newLinkedList();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put(CPU_USER, temp.get(CPU_USER_INDEX));
                    map.put(CPU_NICE, temp.get(CPU_NICE_INDEX));
                    map.put(CPU_SYSTEM, temp.get(CPU_SYSTEM_INDEX));
                    map.put(CPU_IDLE, temp.get(CPU_IDLE_INDEX));
                    map.put(CPU_IOWAIT, temp.get(CPU_IOWAIT_INDEX));
                    map.put(CPU_IRQ, temp.get(CPU_IRQ_INDEX));
                    map.put(CPU_SOFTIRQ, temp.get(CPU_SOFTIRQ_INDEX));
                    map.put(CPU_STEALSTOLEN, temp.get(CPU_STEALSTOLEN_INDEX));
                    break;
                }
            }
            return map;
        }
    }

	@Override
	public int memoryUsage() throws Exception {
		Map<String, String> map = getMemoryInfo();
        long memTotal = parseLong(map.get(MEMORY_TOTAL));
        long memFree = parseLong(map.get(MEMORY_FREE));
        long memused = memTotal - memFree;
        long buffers = parseLong(map.get(MEMORY_BUFFERS));
        long cached = parseLong(map.get(MEMORY_CACHED));

        double usage = (double) (memused - buffers - cached) / memTotal * PERCENT;
        return (int) usage;
	}
	
	private static Map<String, String> getMemoryInfo() throws Exception {
		Map<String, String> map = newHashMap();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(MEMORY_FILE)))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                int beginIndex = 0;
                int endIndex = line.indexOf(MEMORY_SPLIT);
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace(MEMORY_FILE_UNIT, BLANK).trim();
                    map.put(key, value);
                }
            }
            return map;
        }
	}

}
