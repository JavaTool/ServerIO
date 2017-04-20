package org.tool.server.script;

import java.io.File;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JavascriptEngine implements IScriptEngine {
	
	private final ScriptEngine engine;
	
	public JavascriptEngine() {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("javascript");
	}
	
	@Override
	public Object loadScript(File script) throws Exception {
		FileReader reader = new FileReader(script.getAbsolutePath().replaceAll("\\\\", "/"));
		return engine.eval(reader);
	}

	@Override
	public Object invokeFunction(String name, Object... args) throws Exception {
		Invocable invoke = (Invocable) engine;
		return invoke.invokeFunction(name, args);
	}

}
