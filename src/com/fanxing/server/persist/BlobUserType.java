package com.fanxing.server.persist;

import java.util.Comparator;

import org.hibernate.usertype.UserType;

public interface BlobUserType extends UserType, Comparator<Object> {
	
	Object makeObject(byte[] bytes, Object owner) throws Exception;
	
	byte[] makeBytes(Object object);
	
}
