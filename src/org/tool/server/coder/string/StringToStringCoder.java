package org.tool.server.coder.string;

public class StringToStringCoder implements IStringCoder<String> {

	@Override
	public String code(String s) {
		return s;
	}

	@Override
	public String decode(String t) {
		return t;
	}

}
