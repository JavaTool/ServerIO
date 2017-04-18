package org.tool.server.lock;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.concurrent.Callable;

public class LocalFileLock extends SimpleLock<File> {
	
	/**
	 * 尝试获取一个文件锁，除非超时，否则一直等待。获取成功后执行 call，超时会抛出异常。
	 * @param 	file
	 * 			文件
	 * @param 	waitTime
	 * 			超时等待时间
	 * @param 	call
	 * 			执行内容
	 * @return	执行结果
	 * @throws 	Exception
	 */
	@Override
	public <V> V work(File file, long waitTime, Callable<V> call) throws Exception {
		try (RandomAccessFile out = new RandomAccessFile(file, "rw")) {
			FileLock lock = null;
			long totalTime = 0;
			while (true) {
				try {
					lock = out.getChannel().tryLock();
					logLock();
					break;
				} catch (Exception e) {
					totalTime = wait(waitTime, totalTime);
				}
			}
			
			try {
				return call.call();
			} finally {
				lock.release();
				lock.close();
				logUnlock();
			}
		}
	}
	
	/**
	 * 尝试获取一个文件锁，获取成功则执行call，否则返回null。
	 * @param 	file
	 * 			文件
	 * @param 	call
	 * 			执行内容
	 * @return	执行结果
	 * @throws 	Exception
	 */
	@Override
	public <V> V work(File file, Callable<V> call) throws Exception {
		try (RandomAccessFile out = new RandomAccessFile(file, "rw")) {
			FileLock lock = null;
			try {
				lock = out.getChannel().tryLock();
				logLock();
				return call.call();
			} catch (Exception e) {
				return null;
			} finally {
				lock.release();
				lock.close();
				logUnlock();
			}
		}
	}

}
