package org.tool.server.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import org.tool.server.utils.MD5Util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class FileVersionManager implements IFileVersionManager {
	
	private final String path;
	
	private final FilenameFilter filter;
	
	private Map<String, String> versions;
	
	public FileVersionManager(String path, FilenameFilter filter) {
		this.path = path;
		this.filter = filter == null ? (dir, name) -> {return true;} : filter;
	}

	@Override
	public String getVersion(String fileName) {
		return versions.get(fileName);
	}

	@Override
	public boolean compare(String fileName, String version) {
		return version.equals(versions.get(fileName));
	}

	@Override
	public void load() throws Exception {
		File dir = new File(path);
		Map<String, String> versions = Maps.newHashMap();
		for (File file : dir.listFiles(filter)) {
			versions.put(file.getName(), MD5Util.getMD5(file));
		}
		this.versions = ImmutableMap.copyOf(versions);
	}

}
