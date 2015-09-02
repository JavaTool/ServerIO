package com.fanxing.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * MD5工具
 * @author	fuhuiyuan
 */
public class MD5Util {
	
	/**16进制字符*/
	private static final char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	/**
	 * 获取MD5
	 * @param 	byteBuffer
	 * 			数据缓冲器
	 * @return	MD5
	 * @throws 	Exception
	 */
	public static String getMD5(ByteBuffer byteBuffer) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(byteBuffer);
        BigInteger bi = new BigInteger(1, md.digest());
        return bi.toString(16);
	}

	/**
	 * 获取MD5
	 * @param 	file
	 * 			文件
	 * @return	MD5
	 * @throws 	Exception
	 */
	public static String getMD5(File file) throws Exception {
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			return getMD5(byteBuffer);
		} finally {
			in.close();
		}
	}

	/**
	 * 获取MD5
	 * @param 	s
	 * 			字符串
	 * @return	MD5
	 * @throws 	Exception
	 */
	public static String getMD5(String s) throws Exception {
		byte[] btInput = s.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getMD5(new File("D:/file/workspace/ServerIO/src/com/fanxing/server/io/MD5Util.java")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(getMD5("D:/file/workspace/ServerIO/src/com/fanxing/server/io/MD5Util.java"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
