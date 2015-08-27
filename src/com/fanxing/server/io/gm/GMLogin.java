package com.fanxing.server.io.gm;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GM登录
 * @author 	fuhuiyuan
 */
public class GMLogin extends HttpServlet {

	private static final long serialVersionUID = -4231427447545646347L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		boolean checkLogin = false;
		response.setCharacterEncoding("UTF-8");
		OutputStream os = response.getOutputStream();
		if ((account != null && account.length() > 0) && (password != null && password.length() > 0)) {
			GMProcessor gmProcessor = (GMProcessor) request.getServletContext().getAttribute(GMProcessor.class.getName());
			if (gmProcessor != null) {
				checkLogin = gmProcessor.login(account, password);
				if (checkLogin) {
					request.getSession().setAttribute(GMProcessor.ACCOUNT_KEY, account);
				}
			}
		}
		response.setHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; Path=/GatewayServer/; HttpOnly");
		os.write((checkLogin ? "true" : "false").getBytes());
		os.flush();
		os.close();
	}

}
