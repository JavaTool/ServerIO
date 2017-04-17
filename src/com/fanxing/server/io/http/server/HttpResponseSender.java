package com.fanxing.server.io.http.server;

import java.io.OutputStream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.fanxing.server.anthenticate.DefaultEncrypt;
import com.fanxing.server.anthenticate.IEncrypt;
import com.fanxing.server.io.NetType;
import com.fanxing.server.io.dispatch.ISender;

/**
 * HTTP响应发送器
 * @author 	fuhuiyuan
 */
public class HttpResponseSender implements ISender {
	
	private static final String CLOSE_EXCEPTION = "Closed";
	
	private static final IEncrypt ENCRYPT = new DefaultEncrypt();
	/**响应*/
	private final ServletResponse response;
	/**Http会话*/
	private final HttpSession session;
	
	public HttpResponseSender(ServletResponse response, HttpSession session) {
		this.response = response;
		this.session = session;
	}

	@Override
	public void send(byte[] datas, int receiveMessageId, int messageId, long useTime) throws Exception {
		OutputStream os = response.getOutputStream();
		try {
			response.setContentType(makeHead(messageId, useTime));
			os.write(ENCRYPT.encrypt(datas));
		} catch (Exception e) {
			if (CLOSE_EXCEPTION.equals(e.getMessage())) {
				// unprocess close exception
			} else {
				throw e;
			}
		} finally {
			os.flush();
			os.close();
		}
	}

	@Override
	public <X, Y extends X> void setAttribute(String key, Class<X> clz, Y value) {
		session.setAttribute(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X getAttribute(String key, Class<X> clz) {
		return (X) session.getAttribute(key);
	}

	@Override
	public NetType getNetType() {
		return NetType.HTTP;
	}

}
