package com.fanxing.server.io.gm;

/**
 * GM命令处理器
 * @author 	fuhuiyuan
 */
public interface GMProcessor {
	
	/**GM帐号名key*/
	String ACCOUNT_KEY = "account";
	/**GM帐号密码key*/
	String PASSWORD_KEY = "password";
	
	/**
	 * 登录GM帐号
	 * @param 	account
	 * 			帐号名称
	 * @param 	password
	 * 			帐号密码
	 * @return	是否登录成功
	 */
	boolean login(String account, String password);
	/**
	 * 处理GM命令
	 * @param 	cmd
	 * 			命令
	 */
	String processCmd(String account, String cmd);

}
