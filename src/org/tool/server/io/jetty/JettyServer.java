package org.tool.server.io.jetty;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.tool.server.io.INetServer;
import org.tool.server.shutdown.MakeKillShutdownExecutor;
import org.tool.server.utils.LogUtil;

/**
 * Jetty服务器
 * @author 	fuhuiyuan
 */
public final class JettyServer implements INetServer {
	
	private static final Logger log = LogUtil.getLogger(JettyServer.class);
	
	private final IJettyConfig jettyConfig;
	
	private Server server;
	
	public JettyServer(IJettyConfig jettyConfig) {
		this.jettyConfig = jettyConfig;
	}
	
	@Override
	public void bind() throws Exception {
		PropertyConfigurator.configure(jettyConfig.getConfigPath());
		new MakeKillShutdownExecutor().makeKill();
		// 初始化配置
		jettyConfig.init();
		// 创建连接器
		server = new Server(new QueuedThreadPool(jettyConfig.getThreadPoolSize()));
		SslContextFactory sslContextFactory = jettyConfig.getSslContextFactory();
		ServerConnector serverConnector = sslContextFactory == null ? 
				new ServerConnector(server) : new ServerConnector(server, sslContextFactory);
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
		Handler handler = jettyConfig.getHandler();
		if (handler != null) {
			context.setHandler(handler);
		}
		// 线程启动
		server.setHandler(context);
		server.start();
		log.info("JettyServer [{}] start.", jettyConfig.getContextPath());
		server.join();
	}

	@Override
	public void shutdown() {
		try {
			server.stop();
			log.info("JettyServer [{}] shutdown.", jettyConfig.getContextPath());
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public int getPort() {
		return jettyConfig.getPort();
	}

}
