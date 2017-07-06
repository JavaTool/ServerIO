package org.tool.server.io.netty.server.http;

import org.tool.server.anthenticate.IEncrypt;
import org.tool.server.io.message.IConncetHandler;
import org.tool.server.io.message.IMessageIdTransform;
import org.tool.server.io.netty.server.INettyServerConfig;

public final class NettyServerConfig implements INettyServerConfig {
	
	private IConncetHandler conncetHandler;
	
	private IMessageIdTransform messageIdTransform;
	
	private IEncrypt encrypt;
	
	private int parentThreadNum;
	
	private int childThreadNum;
	
	private int port;
	
	private int soBacklog;
	
	private String ip;
	
	private long readerIdleTime;
	
	private long writerIdleTime;
	
	private long allIdleTime;

	@Override
	public IConncetHandler getConncetHandler() {
		return conncetHandler;
	}

	public void setConncetHandler(IConncetHandler conncetHandler) {
		this.conncetHandler = conncetHandler;
	}

	@Override
	public int getParentThreadNum() {
		return parentThreadNum;
	}

	public void setParentThreadNum(int parentThreadNum) {
		this.parentThreadNum = parentThreadNum;
	}

	@Override
	public int getChildThreadNum() {
		return childThreadNum;
	}

	public void setChildThreadNum(int childThreadNum) {
		this.childThreadNum = childThreadNum;
	}

	@Override
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public long getReaderIdleTime() {
		return readerIdleTime;
	}

	public void setReaderIdleTime(long readerIdleTime) {
		this.readerIdleTime = readerIdleTime;
	}

	@Override
	public long getWriterIdleTime() {
		return writerIdleTime;
	}

	public void setWriterIdleTime(long writerIdleTime) {
		this.writerIdleTime = writerIdleTime;
	}

	@Override
	public long getAllIdleTime() {
		return allIdleTime;
	}

	public void setAllIdleTime(long allIdleTime) {
		this.allIdleTime = allIdleTime;
	}

	@Override
	public int getSoBacklog() {
		return soBacklog;
	}

	public void setSoBacklog(int soBacklog) {
		this.soBacklog = soBacklog;
	}

	@Override
	public IEncrypt getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(IEncrypt encrypt) {
		this.encrypt = encrypt;
	}

	@Override
	public IMessageIdTransform getMessageIdTransform() {
		return messageIdTransform;
	}

	public void getMessageIdTransform(IMessageIdTransform messageIdTransform) {
		this.messageIdTransform = messageIdTransform;
	}

}
