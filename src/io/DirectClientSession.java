package io;

import java.net.InetSocketAddress;

import org.apache.mina.common.IoSession;

public class DirectClientSession extends AbstractClientSession {
	
	protected String ip;
	
	public DirectClientSession(DirectClientSessionService service, IoSession session, PacketHandler handler, IOLog log){
		super(service, session, handler, id_gen.incrementAndGet(), log);
		
		InetSocketAddress addr = (InetSocketAddress)session.getRemoteAddress();
		ip = addr.getAddress().getHostAddress();
	}

	@Override
	public void close() {
		super.close();
		session.close();
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
     * 检查是否允许登录（如果在线数过多则不允许登录）
     */
	@Override
    public boolean checkOnlineCount(int currentLoginedAccounts) {
        return true;
    }
	
}
