package org.tool.server.coder.stream;

import java.io.Serializable;

import org.tool.server.utils.SerializaUtil;

class SerialableCoder implements IStreamCoder {

	@Override
	public byte[] write(Object value) throws Exception {
		return SerializaUtil.serializable((Serializable) value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(byte[] stream) throws Exception {
		return (T) SerializaUtil.deserializable(stream);
	}

}
