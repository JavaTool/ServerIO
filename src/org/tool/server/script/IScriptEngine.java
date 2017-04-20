package org.tool.server.script;

import java.io.File;

public interface IScriptEngine {
	
	Object loadScript(File script) throws Exception;
	
	Object invokeFunction(String name, Object... args) throws Exception;

}
