package com.fanxing.server.io.jetty;

import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.shutdown.MakeKillShutdownExecutor;

/**
 * Jetty服务器
 * @author 	fuhuiyuan
 */
public class JettyServer {
	
	protected static final Logger log = LoggerFactory.getLogger(JettyServer.class);
	
	/**
	 * 启动服务器
	 * @param 	jettyConfig
	 * 			Jetty配置加载对象
	 * @throws 	Exception
	 */
	public void start(IJettyConfig jettyConfig) throws Exception {
		DOMConfigurator.configureAndWatch(jettyConfig.getConfigPath());
		new MakeKillShutdownExecutor().makeKill();
		// 初始化配置
		jettyConfig.init();
		// 创建连接器
		QueuedThreadPool threadPool = new QueuedThreadPool(jettyConfig.getThreadPoolSize());
		Server server = new Server(threadPool);
		ServerConnector serverConnector = new ServerConnector(server);
		serverConnector.setPort(jettyConfig.getPort());
		Connector[] connectors = {serverConnector};
		server.setConnectors(connectors);
		// 初始化路径信息
		WebAppContext context = new WebAppContext();
		context.setResourceBase(jettyConfig.getResourceBase());
		context.setDescriptor(jettyConfig.getDescriptor());
		context.setWar(jettyConfig.getWar());
		context.setContextPath(jettyConfig.getContextPath());
		context.setParentLoaderPriority(jettyConfig.getParentLoaderPriority());
		context.setDefaultsDescriptor(jettyConfig.getDefaultsDescriptor());
		context.getSessionHandler().getSessionManager().setMaxInactiveInterval(jettyConfig.getMaxInactiveInterval());
		// 线程启动
		server.setHandler(context);
		server.start();
		log.info("JettyServer start.");
		server.join();
	}

}
