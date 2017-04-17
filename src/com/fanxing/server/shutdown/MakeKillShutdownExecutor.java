package com.fanxing.server.shutdown;

import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.system.SystemUtil;
import com.google.common.base.Preconditions;

public class MakeKillShutdownExecutor extends DefaultShutdownExecutor {
	
	private static final Logger log = LoggerFactory.getLogger(MakeKillShutdownExecutor.class);
	
	private File shutdownFile;
	
	public MakeKillShutdownExecutor(String path) {
		super();
		shutdownFile = new File(path);
	}
	
	public void makeKill() throws Exception {
		if (!SystemUtil.isWindows()) {
			Preconditions.checkArgument(!shutdownFile.exists(), "Another server was running.");
			shutdownFile.createNewFile();
			FileWriter writer = new FileWriter(shutdownFile);
			writer.append("kill " + SystemUtil.getPID());
			writer.flush();
			writer.close();
		}
		log.info("pid is {}.", SystemUtil.getPID());
	}
	
	public MakeKillShutdownExecutor() {
		this("shutdown.sh");
	}

	@Override
	public void shutdown() {
		log.info("Server will shutdown.");
		super.shutdown();
		shutdownFile.deleteOnExit();
		log.info("Server has been shutdown.");
	}

}
