package org.tool.server.coder.stream;

public interface IStreamCoder {
	
	<T> byte[] write(T value) throws Exception;
	
	<T> T read(byte[] stream) throws Exception;

}
