package org.tool.server.coder.string;

import static org.tool.server.utils.DateUtil.formatDate;
import static org.tool.server.utils.DateUtil.parseDate;

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
