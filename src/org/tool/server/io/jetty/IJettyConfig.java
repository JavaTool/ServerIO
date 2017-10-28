package org.tool.server.io.jetty;

import org.eclipse.jetty.server.Handler;

public interface IJettyConfig {
	
	void init();
	
	default String getConfigPath() {
		return "config/log4j.properties";
	}
	
	default int getThreadPoolSize() {
		return 20;
	}
	
	int getPort();
	
	default String getResourceBase() {
		return "./" + getWar();
	}
	
	default String getDescriptor() {
		return getResourceBase() + "/WEB-INF/web.xml";
	}
	
	default String getWar() {
		return "WebContent";
	}
	
	default String getContextPath() {
		return "/" + getClass().getSimpleName();
	}
	
	default boolean getParentLoaderPriority() {
		return true;
	}
	
	default String getDefaultsDescriptor() {
		return "config/webdefault.xml";
	}
	
	default int getMaxInactiveInterval() {
		return 86400000;
	}
	
	default Handler getHandler() {
		return null;
	}

}
