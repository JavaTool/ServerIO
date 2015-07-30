package com.fanxing.server.io.proto;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanxing.server.io.proto.protocol.MessageIdProto.MessageId;
import com.fanxing.server.io.proto.protocol.StructProtos.VO_Error;

/**
 * Proto消息接收器
 * @author 	fuhuiyuan
 */
public class ProtoReceiver extends HttpServlet implements HTTPStatus {
	
	private static final Logger log = LoggerFactory.getLogger(ProtoReceiver.class);
	
	private static final byte[] NULL_REQUEST = new byte[0];

	private static final long serialVersionUID = 1L;
	/**最大数据读取次数*/
	private static final int CONTENT_MAX_READ_TIMES = 5;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		OutputStream os = response.getOutputStream();
		try {
			log.info("Session id is {}.", req.getSession().getId());
			String messageId = req.getHeader(MessageId.class.getSimpleName());
			byte[] decrypt = getRequestProtoContent(req);
			MessageHandle opcodeHandle = ((MessageHandle) req.getServletContext().getAttribute(MessageHandle.class.getName()));
			
			Response resp = opcodeHandle.handle(decrypt, getIpAddr(req), messageId, req.getSession());
			String sendMessageId = resp.getSendMessageId();
			response.setContentType("text/plain; charset=UTF-8; " + MessageId.class.getSimpleName() + "=" + sendMessageId);
			log.info("sendMessageId = " + sendMessageId);
			response.setStatus(resp.getStatus());
			os.write(resp.getSendDatas());
		} catch (Exception e) {
			log.error("", e);
			VO_Error.Builder builder = VO_Error.newBuilder();
			builder.setErrorCode(0);
			builder.setErrorMsg("Unprocessor exception.");
			response.setContentType("text/plain; charset=UTF-8; " + MessageId.class.getSimpleName() + "=" + MessageId.MIVO_Error.name());
			response.setStatus(HTTP_STATUS_SUCCESS);
			os.write(builder.build().toByteArray());
		} finally {
			os.flush();
			os.close();
		}
	}
	
	/**
	 * 读取数据
	 * @param 	request
	 * 			请求
	 * @return	数据
	 * @throws 	Exception
	 */
	private static final byte[] getRequestProtoContent(HttpServletRequest request) throws Exception {
		// get request content length
		final int contentLength = request.getContentLength();
		if (contentLength < 0) {
			throw new Exception("contentLength < 0");
		} else if (contentLength == 0) {
			return NULL_REQUEST;
		} else {
			// get request content
			byte[] data = new byte[contentLength];
			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			int readLength = bis.read(data, 0, contentLength);
			
			int count = 0;
			while (readLength < contentLength) {
				// 读取次数超过最大设置读取次数时还没有读取全部请求内容，返回错误
				if ((++count) > CONTENT_MAX_READ_TIMES) {
					throw new Exception();
				}
				readLength += bis.read(data, readLength, contentLength - readLength);
			}
			return data;
		}
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
