package io;

import org.apache.mina.common.IoSession;

/**
 * 通过代理服务器连接的用户会话。
 * @author 	lighthu
 */
public abstract class DispatchClientSession extends AbstractClientSession {
	
	/*
	 * 会话ID，高32位是代理服务器连接ID，低32位是代理服务器会话ID
	 */
	/**
	 * 关闭时是否需要通知PROXY
	 */
	protected boolean notify;
	/**
	 * IP地址
	 */
	protected String ip = "";
	
	protected int head; //这个是跟proxy建立的连接的Id，每跟一个proxy连接就增加1
	
	protected int proxyGenId; //这个是proxy给这个Session赋予的Id，也就是proxy给客户端连接分配的Id

	public DispatchClientSession(int id, DispatchClientSessionService service,
			IoSession session, PacketHandler handler,int head,int proxyGenId, IOLog log) {
		super(service, session, handler, id, log);
		this.head = head;
		this.notify = true;
		this.proxyGenId = proxyGenId;
		service.addClientSession(this);
	}

	@Override
	public void send(Packet packet) {
		if (state == State.CONNECTED || state == State.AUTHENTICATED) {
			if (packet.needEncrypt) {
				packet = encrypt(packet);
			}
			DispatchPacket dp = new DispatchPacket(proxyGenId, packet);
			session.write(dp);
		}
	}

	@Override
	public void close() {
		super.close();
		if (notify) {
			// 向proxy发送PROXY_SESSION_DISCONNECT包通知关闭连接
			sessionDisconnect();
//			Packet packet = new Packet(OpCode.PROXY_SESSION_DISCONNECT);
//			session.write(new DispatchPacket(proxyGenId, packet));
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
//	@Override
//    public boolean checkOnlineCount(int currentLoginedAccounts) {
//        SyncInteger ati = (SyncInteger)session.getAttribute(DispatchClientSessionService.SESSION_COUNTER);
//        int count = ati.get();
//        ProxyManagingService pms = Platform.getAppContext().get(ProxyManagingService.class);
//        if (pms != null) {
//            return count <= pms.getMaxPlayer(session);
//        }
//        return true;
//    }
    
    public long getFullId(){
    	return (((long) proxyGenId) << 32) | head;
    }

	public int getHead() {
		return head;
	}

	public int getProxyGenId() {
		return proxyGenId;
	}
}
