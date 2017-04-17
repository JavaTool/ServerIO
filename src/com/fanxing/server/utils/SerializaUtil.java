package com.fanxing.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 序列化工具
 * @author 	fuhuiyuan
 */
public final class SerializaUtil {
	
	private SerializaUtil() {}
	
	/**
	 * 序列化
	 * @param 	object
	 * 			被序列化的对象
	 * @return	序列化结果
	 * @throws 	Exception
	 */
	public static byte[] serializable(Serializable object) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			return baos.toByteArray();
		} finally {
			baos.close();
		}
	}
	
	/**
	 * 反序列化
	 * @param 	datas
	 * 			序列化内容
	 * @return	反序列化的对象
	 * @throws 	Exception
	 */
	public static Serializable deserializable(byte[] datas) throws Exception {
		if (datas == null) {
			return null;
		} else {
			ByteArrayInputStream bais = new ByteArrayInputStream(datas);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);
				return (Serializable) ois.readObject();
			} finally {
				bais.close();
			}
		}
	}
	
	/**
	 * 序列化
	 * @param 	objects
	 * 			被序列化的对象集合
	 * @return	序列化结果
	 * @throws 	Exception
	 */
	public static byte[][] serializable(Serializable... objects) throws Exception {
		int size = objects.length;
		byte[][] keyBytes = new byte[size][];
		for (int i = 0;i < size;i++) {
			keyBytes[i] = serializable(objects[i]);
		}
		return keyBytes;
	}
	
	/**
	 * 序列化
	 * @param 	objects
	 * 			被序列化的对象集合
	 * @return	序列化结果
	 * @throws 	Exception
	 */
	public static byte[][] serializable(String... objects) throws Exception {
		int size = objects.length;
		byte[][] keyBytes = new byte[size][];
		for (int i = 0;i < size;i++) {
			keyBytes[i] = serializable(objects[i]);
		}
		return keyBytes;
	}
	
	/**
	 * 反序列化
	 * @param 	list
	 * 			序列化内容集合
	 * @return	反序列化的内容列表
	 * @throws 	Exception
	 */
	public static List<Serializable> deserializable(List<byte[]> list) throws Exception {
		int size = list.size();
		List<Serializable> ret = Lists.newArrayListWithCapacity(size);
		for (int i = 0;i < size;i++) {
			ret.add(deserializable(list.get(i)));
		}
		return Collections.unmodifiableList(ret);
	}

}
