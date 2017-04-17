package com.fanxing.server.coder.string;

public interface IStringCoder<S> {
	
	String code(S s);
	
	S decode(String t);
	
	@SuppressWarnings("unchecked")
	default Object[] code(Object... objects) {
		String[] ret = new String[objects.length];
		for (int i = 0;i < objects.length;i++) {
			ret[i] = code((S) objects[i]);
		}
		return ret;
	}

}
