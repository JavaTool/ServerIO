package com.fanxing.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件工具类
 * @author 	fuhuiyuan
 */
public class FileUtil {
	
	/**
	 * 读取文件到输出流
	 * @param 	file
	 * 			文件
	 * @param 	os
	 * 			输出流
	 * @throws 	Exception
	 */
	public static void readFileToStream(File file, OutputStream os) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
        int i = -1;
        try {
	        while ((i = fis.read(buffer)) != -1) {
	        	os.write(buffer, 0, i);
	        }
        } finally {
        	fis.close();
        }
	}
	
	/**
	 * 创建一个文件，删除原有文件
	 * @param 	path
	 * 			文件名和路径全称
	 * @return	文件
	 * @throws 	IOException
	 */
	public static File createFileOnDelete(String path) throws IOException {
		File file = new File(path);
		createFileOnDelete(file);;
		return file;
	}
	
	/**
	 * 创建一个文件，删除原有文件
	 * @param 	file
	 * 			文件
	 * @throws 	IOException
	 */
	public static void createFileOnDelete(File file) throws IOException {
		file.delete();
		file.createNewFile();
	}
	
	/**
	 * 产生一个文件输出流
	 * @param 	file
	 * 			文件
	 * @return	输出流
	 * @throws 	IOException
	 */
	public static OutputStream createFileOutputStream(File file) throws IOException {
		return new FileOutputStream(file);
	}
	
	/**
	 * 复制文件
	 * @param 	file
	 * 			被复制的文件
	 * @param 	targetPath
	 * 			目标文件名和路径全称
	 * @return	复制后的文件
	 * @throws 	IOException
	 */
	public static File copyFile(File file, String targetPath) throws IOException {
		File target = createFileOnDelete(targetPath);
		copyFile(file, target);
		return target;
	}
	
	/**
	 * 复制文件
	 * @param 	file
	 * 			被复制的文件
	 * @param 	target
	 * 			目标文件
	 * @throws 	IOException
	 */
	public static void copyFile(File file, File target) throws IOException {
		OutputStream os = createFileOutputStream(target);
		try {
			readFileToStream(file, os);
		} finally {
			os.flush();
			os.close();
		}
	}

}
