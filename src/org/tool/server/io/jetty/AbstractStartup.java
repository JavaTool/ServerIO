package org.tool.server.io.jetty;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.tool.server.shutdown.MakeKillShutdownExecutor;
import org.tool.server.shutdown.ShutdownExecutor;
import org.tool.server.utils.LogUtil;

public abstract class AbstractStartup implements ServletContextListener {

	protected static final Logger log = LogUtil.getLogger(AbstractStartup.class);
	
	private ShutdownExecutor shutdownExecutor;

	@Override
	public final void contextInitialized(ServletContextEvent sce) {
		// 关闭处理器
		shutdownExecutor = new MakeKillShutdownExecutor();
		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(), "ShutdownHook"));
		try {
			init(sce);
		} catch (Exception e) {
			log.error("", e);
			System.exit(1);
		}
	}
	
	protected abstract void init(ServletContextEvent sce) throws Exception;

	@Override
	public final void contextDestroyed(ServletContextEvent sce) {
		shutdown(sce);
	}
	
	protected abstract void shutdown(ServletContextEvent sce);

	private class ShutdownHook implements Runnable {

		@Override
		public void run() {
			shutdownExecutor.shutdown();
		}
		
	}

}
