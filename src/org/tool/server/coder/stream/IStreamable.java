package org.tool.server.coder.stream;

public interface IStreamable {
	
	byte[] toByteArray() throws Exception;
	
	void readFromByteArray(byte[] bytes) throws Exception;

}
