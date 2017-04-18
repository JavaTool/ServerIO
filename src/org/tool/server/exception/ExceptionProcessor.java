package org.tool.server.exception;

public class ExceptionProcessor {
	
	public static void create() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println("Exception in thread Id = " + thread.getId() + " : " + throwable.getMessage());
            }
            
        });
	}

}
