package org.tool.server.system;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class Svn {
	
	public int getVersion(String url) throws Exception {
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(SystemUtil.execute("svn info " + url).getInputStream(), "gbk"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("最后修改的版本: ")) {
					return Integer.parseInt(line.replace("最后修改的版本: ", "").trim());
				} else if(line.startsWith("Last Changed Rev: ")){
					return Integer.parseInt(line.replace("Last Changed Rev: ", "").trim());
				}
			}
			throw new Exception("Do not have version.");
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new Svn().getVersion("http://server1/svn/TankPrj/TankServer/LogServer/trunk"));
			System.out.println(new Svn().getVersion("http://server1/svn/TankPrj/TankServer/TankServer/trunk"));
			System.out.println(new Svn().getVersion("http://server1/svn/TankPrj/TankServer/AuthenticateServer/trunk"));
			System.out.println(new Svn().getVersion("http://server1/svn/TankPrj/TankServer/Metadata/trunk"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
