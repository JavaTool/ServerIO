package org.tool.server.io.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.anthenticate.DefaultEncrypt;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.dispatch.ISender;
import org.tool.server.io.http.HttpStatus;
import org.tool.server.io.message.IMessageHandler;
import org.tool.server.utils.HttpConnectUtil;

/**
 * Proto消息接收器
 * @author 	fuhuiyuan
 */
public class HttpProtoReceiver extends HttpServlet implements HttpStatus {
	
	private static final Logger log = LoggerFactory.getLogger(HttpProtoReceiver.class);

	private static final long serialVersionUID = 1L;
	
	private static final String KEY = "MessageId";
	
	private static final IEncrypt ENCRYPT = new DefaultEncrypt();
	
	private static final String NAME = IMessageHandler.class.getName();
	
	public static final String SESSION_IP = "sessionIp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		OutputStream os = response.getOutputStream();
		HttpSession session = req.getSession();
		String ip = getIpAddr(req);
		
		if (session.getAttribute(SESSION_IP) == null) {
			session.setAttribute(SESSION_IP, ip);
		}
		
		ISender sender = new HttpResponseSender(response, session);
		try {
			int messageId = Integer.parseInt(req.getHeader(KEY));
			byte[] decrypt = ENCRYPT.deEncrypt(HttpConnectUtil.getRequestProtoContent(req));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write(messageId);
			baos.write(decrypt);
			log.info("Servlet http receive : [MessageId : {}] [SessionId : {}] [Ip : {}]", messageId, getSessionId(req), ip);
			IMessageHandler contentHandler = (IMessageHandler) req.getServletContext().getAttribute(NAME);
			contentHandler.handle(baos.toByteArray(), sender);
		} catch (Exception e) {
			error(e, response, os);
		}
	}
	
	protected String getSessionId(HttpServletRequest req) {
		return req.getSession().getId();
	}
	
	/**
	 * 处理异常
	 * @param 	e
	 * 			异常
	 * @param 	response
	 * 			响应
	 * @param 	os
	 * 			输出流
	 * @throws 	IOException
	 */
	private void error(Exception e, HttpServletResponse response, OutputStream os) throws IOException {
		log.error("", e);
	}
	
	/**
	 * 获取解析的地址
	 * @param 	request
	 * 			请求
	 * @return	地址
	 */
	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
