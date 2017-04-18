package org.tool.server.account;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 服务器信息
 * @author 	fuhuiyuan
 */
public class ServerInfo implements Serializable {
	
	private static final long serialVersionUID = -4626009568741795031L;
	/**服务器id*/
	private int id;
	/**服务器名称*/
	private String name;
	/**服务器链接*/
	private String url;
	/**状态*/
	private int status;
	
	private boolean recommend;
	
	private String redisInfo;
	
	private int group;

	public int getId() {
		return id;
	}

	/**
	 * 设置服务器id
	 * @param 	id
	 * 			服务器id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取服务器名称
	 * @return	服务器名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置服务器名称
	 * @param 	name
	 * 			服务器名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取服务器链接
	 * @return	服务器链接
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置服务器链接
	 * @param 	url
	 * 			服务器链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取服务器状态
	 * @return	服务器状态
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 设置服务器状态
	 * @param 	status
	 * 			服务器状态
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return MessageFormat.format("[{0}] {1} {2}", getId() + "", getName(), getStatus());
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
	
	public String getRedisInfo(){
		return redisInfo;
	}
	
	public void setRedisInfo(String redisInfo){
		this.redisInfo = redisInfo;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
}
