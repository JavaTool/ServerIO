package org.tool.server.io.http.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.anthenticate.DefaultEncrypt;
import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.NetType;
import org.tool.server.io.dispatch.ISender;

/**
 * HTTP响应发送器
 * @author 	fuhuiyuan
 */
public final class HttpResponseSender implements ISender {
	
	private static final Logger log = LoggerFactory.getLogger(HttpResponseSender.class);
	
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
	public void send(byte[] datas, int serial, int messageId) throws Exception {
		try (OutputStream os = response.getOutputStream()) {
			response.setContentType(makeHead(messageId));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeInt(messageId);
			dos.writeInt(serial); // 客户端的协议序列号，如果是需要返回消息的协议，则该值原样返回
			dos.write(datas);
			os.write(ENCRYPT.encrypt(baos.toByteArray()));
		} catch (Exception e) {
			log.info("Exception message[{}], serial[{}].", messageId, serial);
			throw e;
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

	@Override
	public String getIp() {
		return getAttribute(HttpProtoReceiver.SESSION_IP, String.class);
	}

	@Override
	public String getSessionId() {
		return session.getId();
	}

}
