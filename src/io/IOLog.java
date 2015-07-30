package io;

public interface IOLog {
	
	void info(String paramString);
	
	void error(Exception e);
	
	void debug(String paramString, Throwable paramThrowable);

}
