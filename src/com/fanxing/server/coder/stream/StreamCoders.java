package com.fanxing.server.coder.stream;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

public final class StreamCoders {
	
	private static final ClassToInstanceMap<IStreamCoder> map = MutableClassToInstanceMap.create();
	
	private StreamCoders() {}
	
	public static IStreamCoder newByteArrayCoder() {
		return map.getOrDefault(ByteArrayMessageCoder.class, new ByteArrayMessageCoder());
	}
	
	public static IStreamCoder newSerialableCoder() {
		return map.getOrDefault(SerialableCoder.class, new SerialableCoder());
	}
	
	public static IStreamCoder newStreamableCoder() {
		return map.getOrDefault(StreamableCoder.class, new StreamableCoder());
	}
	
	public static IStreamCoder newStreamableCoder(Class<? extends IStreamable> clz) {
		return map.getOrDefault(StreamableTCoder.class, new StreamableTCoder(clz));
	}
	
	public static IStreamCoder newProtoStuffCoder() {
		return map.getOrDefault(ProtoStuffCoder.class, new ProtoStuffCoder());
	}

}
