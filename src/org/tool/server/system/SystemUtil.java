package org.tool.server.system;

import static java.lang.Integer.parseInt;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.lang.management.ManagementFactory.getRuntimeMXBean;
import static java.net.InetAddress.getLocalHost;
import static java.net.NetworkInterface.getByInetAddress;
import static java.util.Locale.getDefault;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
	
	private static final Logger log = LoggerFactory.getLogger(SystemUtil.class);
	
	public static final String ENV_USERNAME = "USERNAME";
	
	public static final String ENV_COMPUTERNAME = "COMPUTERNAME";
	
	public static final String SYSTEM_NAME = "os.name";
	
	public static final int CPU_AMOUNT = getRuntime().availableProcessors();
	
	private static final ISystemInfo systemInfo = createSystemInfo();
	
	private SystemUtil() {}
	
	private static ISystemInfo createSystemInfo() {
		return isWindows() ? new WindowsInfo() : new LinuxInfo();
	}
	
	public static String getSystemName() {
		return getProperty(SYSTEM_NAME);
	}
	
	public static boolean isWindows() {
		return getSystemName().toLowerCase().contains("win");
	}
	
	public static String getProcessName() {
		return getRuntimeMXBean().getName();
	}
	
	public static int getPID() {
		return parseInt(getProcessName().split("@")[0]);
	}
	
	public static Map<String, String> getEnv() {
		return getenv();
	}
	
	public static String getEnvUserName() {
		return getEnv().get(ENV_USERNAME);
	}
	
	public static String getEnvComputerName() {
		return getEnv().get(ENV_COMPUTERNAME);
	}
	
	public static String getLocalIP() throws UnknownHostException {
		InetAddress address = getLocalHost();
		return address.getHostAddress();
	}
	
	public static String getMAC() throws Exception {
		InetAddress address = getLocalHost();
		NetworkInterface ni = getByInetAddress(address);
		byte[] mac = ni.getHardwareAddress();
		String sMAC = "";  
        @SuppressWarnings("resource")
		Formatter formatter = new Formatter();  
        if (mac != null) {
	        for (int i = 0; i < mac.length; i++) {  
	            sMAC += formatter.format(getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();  
	        }
        }
        return sMAC;
	}
	
	public static Process execute(String cmd) throws IOException {
		Runtime runtime = getRuntime();
		return runtime.exec(cmd);
	}
	
	public static int getCpuRatio() throws Exception {
		return systemInfo.getCpuRatio();
	}
	
	public static int memoryUsage() throws Exception {
		return systemInfo.memoryUsage();
	}
	
	public static long getUseMemory() {
		Runtime runtime = getRuntime();
		return runtime.maxMemory() - runtime.freeMemory();
	}
	
	public static void executeBat(String path, Object... params) throws Exception {
		StringBuilder builder = new StringBuilder(path);
		for (Object param : params) {
			builder.append(" ").append(param);
		}
		Process process = execute(builder.toString());
		out(process.getInputStream());
		out(process.getErrorStream());
	}
	
	public static void out(InputStream inputStream) throws Exception {
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(inputStream, "gbk"))) {
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(new String(line.getBytes())).append("\r\n");
			}
			log.info(builder.toString());
		}
	}

}
