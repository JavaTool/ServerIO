package org.tool.server.coder.string;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class IntToStringCoder implements IStringCoder<Integer> {

	@Override
	public String code(Integer s) {
		return valueOf(s);
	}

	@Override
	public Integer decode(String t) {
		return t == null ? null : parseInt(t);
	}

}
