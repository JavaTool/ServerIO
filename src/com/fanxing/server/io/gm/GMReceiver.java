package com.fanxing.server.io.gm;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanxing.server.utils.HttpConnectUtil;

/**
 * GM命令接收器
 * @author 	fuhuiyuan
 */
public class GMReceiver extends HttpServlet {

	private static final long serialVersionUID = -2336091862034846486L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = (String) request.getSession().getAttribute(GMProcessor.ACCOUNT_KEY);
		String result = "";
		response.setCharacterEncoding("UTF-8");
		OutputStream os = response.getOutputStream();
		if (account != null) {
			try {
				byte[] decrypt = HttpConnectUtil.getRequestProtoContent(request);
				String cmd = new String(decrypt);
				if (cmd != null && cmd.length() > 0) {
					GMProcessor gmProcessor = (GMProcessor) request.getServletContext().getAttribute(GMProcessor.class.getName());
					if (gmProcessor != null) {
						result = gmProcessor.processCmd("", cmd);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		os.write(result.getBytes());
		os.flush();
		os.close();
	}

}
