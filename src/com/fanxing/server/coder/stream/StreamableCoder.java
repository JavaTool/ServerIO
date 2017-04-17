package com.fanxing.server.coder.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

class StreamableCoder implements IStreamCoder {

	@Override
	public byte[] write(Object value) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeUTF(value.getClass().getName());
		byte[] classBytes = baos.toByteArray();
		IStreamable streamable = (IStreamable) value;
		return appendArray(classBytes, streamable.toByteArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(byte[] stream) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(stream);
        DataInputStream dis = new DataInputStream(bis);
		Class<IStreamable> clz = (Class<IStreamable>) Class.forName(dis.readUTF());
        IStreamable value = clz.newInstance();
        value.readFromByteArray(subArray(stream, bis.available()));
		return (T) value;
	}
	
	private static byte[] appendArray(byte[] array1, byte[] array2) {
		int length1 = array1.length;
		int length2 = array2.length;
		byte[] array = new byte[length1 + length2];
		System.arraycopy(array1, 0, array, 0, length1);
		System.arraycopy(array2, 0, array, length1, length2);
		return array;
	}
	
	private static byte[] subArray(byte[] array, int available) {
		int offset = array.length - available;
		byte[] ret = new byte[available];
		System.arraycopy(array, offset, ret, 0, available);
		return array;
	}

}
