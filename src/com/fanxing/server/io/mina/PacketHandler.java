package com.fanxing.server.io.mina;

/**
 * ������������ͻ��˵��ϴ����õ�
 * @author 	Jeffrey
 */
public interface PacketHandler {
	
	/**
	 * @note ϵͳ�ڴ���ͻ����ϴ����ʱ�򽫻���ô˷����������ϴ��İ��Լ������ڵĿͻ���Session
	 * @param packet �ͻ����ϴ��İ�
	 * @param session �������Ŀͻ���Session
	 * @throws Exception �����ʱ���?�����׳��쳣
	 */
	void handle(Packet packet,ClientSession session) throws Exception;
	
}
