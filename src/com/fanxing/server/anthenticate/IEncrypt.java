package com.fanxing.server.anthenticate;

public interface IEncrypt {
	
	byte[] encrypt(byte[] src);
	
	byte[] deEncrypt(byte[] src);

}
