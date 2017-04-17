package com.fanxing.server.system;

import static java.lang.Long.valueOf;
import static java.lang.management.ManagementFactory.getOperatingSystemMXBean;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.getenv;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
class WindowsInfo implements ISystemInfo {
	
	private static final int FAULTLENGTH = 10;
	
	private static final String CPU_CAPTION = "Caption";
	
	private static final String CPU_COMMAND_LINE = "CommandLine";
	
	private static final String CPU_READ_OPERATION_COUNT = "ReadOperationCount";
	
	private static final String CPU_USER_MODE_TIME = "UserModeTime";
	
	private static final String CPU_KERNEL_MODE_TIME = "KernelModeTime";
	
	private static final String CPU_WRITE_OPERATION_COUNT = "WriteOperationCount";
	
	private static final String CPU_SYSTEM_IDLE_PROCESS = "System Idle Process";
	
	private static final String CPU_SYSTEM = "System";
	
	private static final String CPU_COMMAND = "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,"
			+ "ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
	
	private static final String WMIC_EXE = "wmic.exe";
	
	private static final String WIN_DIR = "windir";

	@Override
	public int getCpuRatio() throws Exception {
		String procCmd = getenv(WIN_DIR) + CPU_COMMAND;
        // 取进程信息
        long[] c0 = readCpu(getRuntime().exec(procCmd));
        Thread.sleep(CPUTIME);
        long[] c1 = readCpu(getRuntime().exec(procCmd));
        if (c0 != null && c1 != null) {
            long idletime = c1[0] - c0[0];
            long busytime = c1[1] - c0[1];
            double compare = (double) (PERCENT * (busytime) * 1.0 / (busytime + idletime));
            return (int) compare;
        } else {
            return 0;
        }
	}
	
	private static long[] readCpu(final Process proc) throws Exception {
        long[] retn = new long[2];
        proc.getOutputStream().close();
        try (LineNumberReader input = new LineNumberReader(new InputStreamReader(proc.getInputStream()))) {
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf(CPU_CAPTION);
            int cmdidx = line.indexOf(CPU_COMMAND_LINE);
            int rocidx = line.indexOf(CPU_READ_OPERATION_COUNT);
            int umtidx = line.indexOf(CPU_USER_MODE_TIME);
            int kmtidx = line.indexOf(CPU_KERNEL_MODE_TIME);
            int wocidx = line.indexOf(CPU_WRITE_OPERATION_COUNT);
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf(WMIC_EXE) >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals(CPU_SYSTEM_IDLE_PROCESS) || caption.equals(CPU_SYSTEM)) {
                    if (s1.length() > 0) {
                        idletime += valueOf(s1);
                    }
                    if (s2.length() > 0) {
                        idletime += valueOf(s2);
                    }
                    continue;
                }
                if (s1.length() > 0) {
                    kneltime += valueOf(s1);
                }
                if (s2.length() > 0) {
                    usertime += valueOf(s2);
                }
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } 
    }
	
	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 * @param src 要截取的字符串
	 * @param start_idx 开始坐标（包括该坐标)
	 * @param end_idx 截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	@Override
	public int memoryUsage() throws Exception {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) getOperatingSystemMXBean(); 
		// 总的物理内存+虚拟内存 
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize(); 
		// 剩余的物理内存 
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize(); 
		double compare = (double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * PERCENT;
		return (int) compare;
	}
	
	public static void main(String[] args) {
		ISystemInfo systemInfo = new WindowsInfo();
		try {
			System.out.println(systemInfo.getCpuRatio());
			System.out.println(systemInfo.memoryUsage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
