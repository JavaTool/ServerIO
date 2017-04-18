package org.tool.server.system;

import java.io.File;
import java.io.FileWriter;

public class SvnVersoinFile extends Svn {
	
	public void createVersionFile(String url, File versionFile) throws Exception {
		int versoin = getVersion(url);
		versionFile.delete();
		versionFile.createNewFile();
		try (FileWriter writer = new FileWriter(versionFile)) {
			writer.append(versoin + "");
			writer.flush();
			writer.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			new SvnVersoinFile().createVersionFile(args[0], new File(args[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
