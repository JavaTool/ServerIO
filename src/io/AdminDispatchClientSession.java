package io;

import org.apache.mina.common.IoSession;

/**
 * 通过代理服务器连接的管理会话。
 * @author 	lighthu
 */
public abstract class AdminDispatchClientSession extends AbstractClientSession {
	
	/**
	 * 会话ID，高32位是代理服务器连接ID，低32位是代理服务器会话ID
	 */
	protected long id;
	/**
	 * 关闭时是否需要通知PROXY
	 */
	protected boolean notify;
	/**
	 * IP地址
	 */
	protected String ip = "";

	public AdminDispatchClientSession(long id, AdminDispatchClientSessionService service, IoSession session, PacketHandler handler, IOLog log) {
		super(service, session, handler, id_gen.incrementAndGet(), log);
		
		this.id = id;
		notify = true;
		service.addClientSession(this);
	}

	@Override
	public void send(Packet packet) {
		if (state == State.CONNECTED || state == State.AUTHENTICATED) {
			session.write(new DispatchPacket((int) id, packet));
//			log.info("send:" + packet.opCode);
		}
	}

	@Override
	public void close() {
		super.close();
		if (notify) {
			// 向proxy发送PROXY_SESSION_DISCONNECT包通知关闭连接
			sessionDisconnect();
//			Packet packet = new Packet(OpCode.PROXY_SESSION_DISCONNECT);
//			session.write(new DispatchPacket((int) id, packet));
		}
	}
	
	protected abstract void sessionDisconnect();
	
	public void silentClose() {
		notify = false;
	}

	@Override
	public boolean isConnected() {
		return state == State.CONNECTED || state == State.AUTHENTICATED;
	}
	
	/**
	 * 取客户端IP地址
	 */
	@Override
	public String getClientIP() {
		return ip;
	}
	
	/**
	 * 设置IP地址
	 * @param ip
	 */
	public void setIP(int ipnum) {
		ip = IOUtil.ip2str(ipnum);
	}

    /**
     * 检查是否允许登录（如果在线数过多则不允许登录）
     */
	@Override
    public boolean checkOnlineCount(int currentLoginedAccounts) {
        return true;
    }
	
}
