package org.tool.server.utils;

import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.google.common.base.Stopwatch;

public class JSUtil {

	public static void main(String[] args) throws Exception {
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine engine = sem.getEngineByName("nashorn");

	    //向上下文中存入变量
	    engine.put("msg", "just a test");
	    //定义类user
	    String str = "msg += '!!!';var user = {name:'tom',age:23,hobbies:['football','basketball']}; ";
	    engine.eval(str);

	    //从上下文引擎中取值
	    String msg = (String) engine.get("msg");
//	    String name = (String) engine.get("name");
//	    String[] hb = (String[]) engine.get("hobbies");
	    System.out.println(msg);
//	    System.out.println(name + ":" + hb[0]);

	    //定义数学函数
	    engine.eval("function add (a, b, c, d) {e = a + b * c * d; return e; }");
	    //取得调用接口
//	    Invocable jsInvoke = (Invocable) engine;
	    //定义加法函数
	    Stopwatch timeOutputer = Stopwatch.createStarted();
	    for (int i = 0;i < 10;i++) {
//	    	Object result1 = jsInvoke.invokeFunction("add", i, i, i, i);
//	    System.out.println(result1);
//	    	timeOutputer.timing("js " + i);
	    }
//	    int re = 10 + 5 * 3 * 4;
//	    timeOutputer.timing("java");
//	    timeOutputer.outputAll();
	    timeOutputer.stop();

	    //调用加法函数,注意参数传递的方法
//	    Adder adder = jsInvoke.getInterface(Adder.class);
//	    int result2 = adder.add(10, 35);
//	    System.out.println(result2);

	    //定义run()函数
	    engine.eval("function run() {print('www.java2s.com');}");
	    Invocable invokeEngine = (Invocable) engine;
	    Runnable runner = invokeEngine.getInterface(Runnable.class);
	    //定义线程运行之
	    Thread t = new Thread(runner);
	    t.start();
	    t.join();
	    //导入其他java包
	    String jsCode = "Java.type(\"java.util.List\");var Arrays = Java.type(\"java.util.Arrays\");var list2 = Arrays.asList(['A', 'B', 'C']); ";
	    engine.eval(jsCode);
	    @SuppressWarnings("unchecked")
	    List<String> list2 = (List<String>) engine.get("list2");
	    for (String val : list2) { System.out.println(val);}
	}
	
}
