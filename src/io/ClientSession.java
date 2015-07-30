package io;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.common.IoSession;

/**
 * @brief 此接口代表一个客户端的连接，一旦客户端跟服务器端建立起一个连接，那么相应的会建立一个此接口实现类的实例
 * @note 客户端连接在服务器上的对应体，通过此对应体，服务器端能够接受客户端发送上来的包，并且下发包给客户端，还能知道客户端连接的状态
 * @author Jeffrey
 */
public interface ClientSession {
	
	AtomicInteger id_gen = new AtomicInteger(0);
	
	/**
	 * @note 连接的Id号，每个连接的Id号都是不相同的，含义跟文件的句柄有些类似
	 * @return 连接的Id号
	 */
	int getId();
	/**
	 * @note 当前连接是否处于连接状态
	 */
	boolean isConnected();
	/**
	 * @note 发送包给客户端
	 * 
	 * @param packet 要发送的包
	 */
	void send(Packet packet);
	/**
	 * @note 关闭连接
	 */
	void close();
	/**
	 * @note 获取此连接的包处理器
	 */
	PacketHandler getHandler();
	/**
	 * @note 获取ClientSession对应的Client，Client可以是任何实现Client接口的对象，比如玩家上线以后，Client对象可以返回Player
	 */
	Client getClient();
	/**
	 * @note 设置当前的Client对象
	 */
	void setClient(Client client);
	/**
	 * @note 更新ClientSession
	 */
	void update();
	
	void keepLive();
	/**
	 * @note 添加包，一般用在网络层收取到客户端包以后用次方法添加到接受队列中
	 * @param packet 服务器收到的客户端上传的包
	 */
	void addPacket(Packet packet);
	/**
	 * @note 传入一个Identity对象，一般用来在认证成功以后，设置账号对象使用
	 * @throws SecurityException 如果当前ClientSession的状态错误，比如已经处于认证成功状态，抛出SecurityException
	 */
	void authenticate(Identity identity) throws SecurityException;
	/**
	 * @note 获取当前的认证对象
	 */
	Identity getIdentity();
	/**
	 * @note 取客户端IP地址
	 */
	String getClientIP();
	/**
	 * @note 检查是否允许登录（如果在线数过多则不允许登录）
	 */
	boolean checkOnlineCount(int currentLoginedAccounts);
	
	IoSession getIoSession();
	
}
