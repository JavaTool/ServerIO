package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 信任IP服务。
 * @author 	lighthu
 */
public class TrustIp {
	
	private int[][] trustIps;
	
	private long lastModified;
	
	private long lastCheckTime;
	
	private File file;
	
	private IOLog log;
	
	public TrustIp(String name, IOLog log) throws Exception {
		this.log = log;
		file = new File(System.getProperty("user.dir"), name);
		load();
		lastModified = file.lastModified();
		lastCheckTime = System.currentTimeMillis();
	}

	private void load() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<int[]> retList = new ArrayList<int[]>();
		String line;
		while ((line = reader.readLine()) != null) {
			String[] secs = line.split("-");
			if (secs.length != 2) {
				continue;
			}
			int[] arr = new int[2];
			arr[0] = strToIP(secs[0]);
			arr[1] = strToIP(secs[1]);
			retList.add(arr);
		}
		reader.close();
		synchronized (this) {
			trustIps = new int[retList.size()][2];
			retList.toArray(trustIps);
		}
	}

	protected void check() {
		long now = System.currentTimeMillis();
		// 最多1分钟检查一次
		if (now - lastCheckTime < 60000) {
			return;
		}
		lastCheckTime = now;
		if (file.lastModified() != lastModified) {
			// 如果文件修改时间变化，重新载入
			lastModified = file.lastModified();
			try {
				load();
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	public int strToIP(String s) {
		String[] secs = s.split("\\.");
		return ((Integer.parseInt(secs[0]) << 24) & 0xFF000000)
				| ((Integer.parseInt(secs[1]) << 16) & 0xFF0000)
				| ((Integer.parseInt(secs[2]) << 8) & 0xFF00)
				| (Integer.parseInt(secs[3]) & 0xFF);
	}

	public int addressToIP(InetSocketAddress address) {
		byte[] bytes = address.getAddress().getAddress();
		return (((bytes[0] & 0xFF) << 24) & 0xFF000000)
				| (((bytes[1] & 0xFF) << 16) & 0xFF0000)
				| (((bytes[2] & 0xFF) << 8) & 0xFF00) | (bytes[3] & 0xFF);
	}

	/**
	 * 判断一个地址是否在信任表中
	 * @param 	address
	 * 			
	 * @return	
	 */
	public boolean isTrustIp(InetSocketAddress address) {
		long ip = addressToIP(address) & 0xFFFFFFFFL;
		synchronized (this) {
			for (int i = 0; i < trustIps.length; i++) {
				long start = trustIps[i][0] & 0xFFFFFFFFL;
				long end = trustIps[i][1] & 0xFFFFFFFFL;
				if (ip >= start && ip <= end) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 强制添加一个信任地址段
	 * @param 	begin
	 * 			
	 * @param 	end
	 * 			
	 */
	public void addTrustIp(int begin, int end) {
		int[][] newTrustIps = new int[trustIps.length + 1][2];
		System.arraycopy(trustIps, 0, newTrustIps, 0, trustIps.length);
		newTrustIps[trustIps.length][0] = begin;
		newTrustIps[trustIps.length][1] = end;
		trustIps = newTrustIps;
	}
	
}
