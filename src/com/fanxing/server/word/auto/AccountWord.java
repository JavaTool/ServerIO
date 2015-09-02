package com.fanxing.server.word.auto;

public interface AccountWord extends ErrorCode {

	String NAME = "name";

	String NOTES = "notes";

	String SHOWINFO = "showinfo";

	String KEY = "key";

	String ACCOUNT_NAME_NULL = "ACCOUNT_NAME_NULL";

	String ACCOUNT_PASSWORD_NULL = "ACCOUNT_PASSWORD_NULL";

	String ACCOUNT_NAME_NOT_EXISTS = "ACCOUNT_NAME_NOT_EXISTS";

	String ACCOUNT_PASSWORD_NOT_CORRECT = "ACCOUNT_PASSWORD_NOT_CORRECT";

	String ACCOUNT_EMAIL_NULL = "ACCOUNT_EMAIL_NULL";

	String ACCOUNT_NAME_IN_USE = "ACCOUNT_NAME_IN_USE";

	String ACCOUNT_EMAIL_ERROR = "ACCOUNT_EMAIL_ERROR";

}
