package org.tool.server.io.file;

public interface IFileVersionManager {
	
	String getVersion(String fileName);
	
	boolean compare(String fileName, String version);
	
	void load() throws Exception;

}
