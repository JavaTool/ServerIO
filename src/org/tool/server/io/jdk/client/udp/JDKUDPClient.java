package org.tool.server.io.jdk.client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.io.INetClient;

public class JDKUDPClient implements INetClient<byte[]> {
	
	private static final Logger log = LoggerFactory.getLogger(JDKUDPClient.class);
	
	private DatagramSocket client;
	
	private InetAddress addr;
	
	private int port;
	
	public JDKUDPClient() {
		try {
			client = new DatagramSocket();
		} catch (SocketException e) {
			log.error("", e);
		}
	}

	@Override
	public void connect(String ip, int port) throws Exception {
		addr = InetAddress.getByName(ip);
		this.port = port;
	}

	@Override
	public void send(byte[] data) {
		DatagramPacket sendPacket = new DatagramPacket(data, data.length, addr, port);
        try {
			client.send(sendPacket);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	@Override
	public void close() throws Exception {
		client.close();
	}
	
	public static void main(String[] args) {
		try {
			INetClient<byte[]> client = new JDKUDPClient();
			client.connect("115.159.181.147", 10001);
			client.send("MoneyFlow|7001|2016-10-27 11:38:57||0|0||100181|5|血斧奥顿|0|1674|186|23|-1|0|0".getBytes());
	        System.out.println("发送结束");
	        client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
