package com.fanxing.server.io.http;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.dispatch.ISender;
import com.fanxing.server.io.proto.MessageHandle;
import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.proto.protocol.StructProtos.VO_Error;
import com.fanxing.server.utils.HttpConnectUtil;

/**
 * Proto消息接收器
 * @author 	fuhuiyuan
 */
public class HttpProtoReceiver extends HttpServlet implements HttpStatus {
	
	private static final Logger log = LoggerFactory.getLogger(HttpProtoReceiver.class);

	private static final long serialVersionUID = 1L;
	
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
			log.info("Session id is {}.", session.getId());
			String messageId = req.getHeader(MessageId.class.getSimpleName());
			byte[] decrypt = HttpConnectUtil.getRequestProtoContent(req);
			MessageHandle opcodeHandle = ((MessageHandle) req.getServletContext().getAttribute(MessageHandle.class.getName()));
			opcodeHandle.handle(decrypt, ip, messageId, session.getId(), sender);
		} catch (Exception e) {
			error(e, response, os);
			os.flush();
			os.close();
			return;
		}
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
		VO_Error.Builder builder = VO_Error.newBuilder();
		builder.setErrorCode(0);
		builder.setErrorMsg("Unprocessor exception.");
		response.setContentType("text/plain; charset=UTF-8; " + MessageId.class.getSimpleName() + "=" + MessageId.MIVO_Error.name());
		response.setStatus(HTTP_STATUS_SUCCESS);
		os.write(builder.build().toByteArray());
	}
	
	/**
	 * 获取解析的地址
	 * @param 	request
	 * 			请求
	 * @return	地址
	 */
	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
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
