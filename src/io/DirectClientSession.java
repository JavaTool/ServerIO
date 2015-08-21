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
	 * ȡ�ͻ���IP��ַ
	 */
	@Override
	public String getClientIP() {
		return ip;
	}

    /**
     * ����Ƿ������¼����������������������¼��
     */
	@Override
    public boolean checkOnlineCount(int currentLoginedAccounts) {
        return true;
    }
	
}
