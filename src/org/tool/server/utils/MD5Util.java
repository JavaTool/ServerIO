package org.tool.server.utils;

import static java.lang.Integer.toHexString;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.tool.server.system.SystemUtil;

import sun.nio.ch.FileChannelImpl;

/**
 * MD5工具
 * @author	fuhuiyuan
 */
@SuppressWarnings("restriction")
public final class MD5Util {
	
	private MD5Util() {}
	
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
	 * @param 	bytes
	 * 			byte数组
	 * @return	MD5
	 * @throws 	Exception
	 */
	public static String getMD5(byte[] bytes) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
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
		FileChannel channel = in.getChannel();
		MappedByteBuffer byteBuffer = null;
		try {
			byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			return getMD5(byteBuffer);
		} finally {
			if (SystemUtil.isWindows()) {
				if (byteBuffer != null) {
					Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);  
				    m.setAccessible(true);  
				    m.invoke(FileChannelImpl.class, byteBuffer);
				}
			}
			channel.close();
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
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
        char[] charArray = s.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(toHexString(val));  
        }
        return hexValue.toString();
	}
	
	public static void main(String[] args) {
//		try {
//			System.out.println(getMD5(new File("D:/file/workspace/ServerIO/src/com/fanxing/server/io/MD5Util.java")));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		try {
			System.out.println(getMD5("STz4jrXyorWfHVBf1476000077"));
			System.out.println(getMD5("STz4jrXyorWfHVBf1476000104"));
//			System.out.println(getMD5("123".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
