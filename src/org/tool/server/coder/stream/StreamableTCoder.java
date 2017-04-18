package org.tool.server.coder.stream;

class StreamableTCoder implements IStreamCoder {
	
	private final Class<? extends IStreamable> clz;
	
	public StreamableTCoder(Class<? extends IStreamable> clz) {
		this.clz = clz;
	}

	@Override
	public byte[] write(Object value) throws Exception {
		return ((IStreamable) value).toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(byte[] stream) throws Exception {
		if (stream == null) {
			return null;
		} else {
			IStreamable streamable = clz.newInstance();
			streamable.readFromByteArray(stream);
			return (T) streamable;
		}
	}

}
