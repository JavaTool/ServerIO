package com.fanxing.server.coder.stream;

import static io.protostuff.LinkedBuffer.allocate;
import static io.protostuff.ProtostuffIOUtil.mergeFrom;
import static io.protostuff.ProtostuffIOUtil.writeTo;
import static io.protostuff.runtime.RuntimeSchema.getSchema;
import static java.lang.Class.forName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;

class ProtoStuffCoder implements IStreamCoder {

	@Override
	public <T> byte[] write(T value) throws Exception {
		if (value instanceof String) {
			value.hashCode();
		}
		@SuppressWarnings("unchecked")
		Class<T> clz = (Class<T>) value.getClass();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bout);
		dos.writeUTF(clz.getName());
		// 序列化
    	if (clz.equals(byte[].class)) {
    		dos.write((byte[]) value);
    	} else if(clz.equals(int[].class)){
    		int[] arr = (int[])value;
    		dos.writeShort(arr.length);
    		for(int v : arr){
    			dos.writeInt(v);
    		}
    	} else {
    		Schema<T> schema = getSchema(clz);
    		LinkedBuffer buffer = allocate(4096);
    	    try {
	    		writeTo(bout, value, schema, buffer);
    	    } finally {
    	        buffer.clear();
    	    }
    	}
        return bout.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(byte[] stream) throws Exception {
		if (stream == null) {
			return null;
		} else {
			ByteArrayInputStream bais = new ByteArrayInputStream(stream);
			DataInputStream dis = new DataInputStream(bais);
			String className = dis.readUTF();
			if (className.equals(byte[].class.getName())) {
				byte[] ret = new byte[dis.available()];
				dis.read(ret);
				return (T) ret;
			} else if(className.equals(int[].class.getName())){
				short len = dis.readShort();
				int[] ret = new int[len];
				for(int i = 0;i < len; i++){
					ret[i] = dis.readInt();
				}
				return (T)ret;
			} else {
				Schema<T> schema = (Schema<T>) getSchema(forName(className));
				T ret = schema.newMessage();
				mergeFrom(dis, ret, schema);
				return ret;
			}
		}
	}

}
