package com.fanxing.server.anthenticate;

import static java.lang.System.arraycopy;

public class DefaultEncrypt implements IEncrypt {
	
	private static final byte BIT = 2;
	
	private static final int BASE_LENGTH = 1 << BIT;

	@Override
	public byte[] encrypt(byte[] src) {
		int length = src.length;
		if (length < BASE_LENGTH) {
			return src;
		} else {
			int leftLength = length >> BIT;
			int rightLength = length - leftLength;
			return copy(src, leftLength, rightLength);
		}
	}

	@Override
	public byte[] deEncrypt(byte[] src) {
		int length = src.length;
		if (length < BASE_LENGTH) {
			return src;
		} else {
			int rightLength = length >> BIT;
			int leftLength = length - rightLength;
			return copy(src, leftLength, rightLength);
		}
	}
	
	private static byte[] copy(byte[] src, int leftLength, int rightLength) {
		byte[] dest = new byte[leftLength + rightLength];
		arraycopy(src, leftLength, dest, 0, rightLength);
		arraycopy(src, 0, dest, rightLength, leftLength);
		return dest;
	}
	
	public static void main(String[] args) {
		IEncrypt encrypt = new DefaultEncrypt();
		encrypt.deEncrypt(encrypt.encrypt(new byte[]{1, 2, 3, 4, 5}));
	}

}
