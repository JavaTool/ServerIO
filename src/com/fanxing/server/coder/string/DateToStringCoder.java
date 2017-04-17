package com.fanxing.server.coder.string;

import static com.fanxing.server.utils.DateUtil.formatDate;
import static com.fanxing.server.utils.DateUtil.parseDate;

import java.util.Date;

public class DateToStringCoder implements IStringCoder<Date> {

	@Override
	public String code(Date s) {
		return formatDate(s);
	}

	@Override
	public Date decode(String t) {
		return parseDate(t);
	}

}
