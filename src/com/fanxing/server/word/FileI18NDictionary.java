package com.fanxing.server.word;

import java.io.File;
import java.util.List;

import com.fanxing.server.utils.FileUtil;

public class FileI18NDictionary extends I18NDictionary {

	public FileI18NDictionary(String path) {
		super(path);
	}

	@Override
	protected List<String> read() throws Exception {
		return FileUtil.readAllLine(new File(path));
	}

}
