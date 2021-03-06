package io;

import java.util.HashMap;
import java.util.Map;

public class HandlerDispatch implements PacketHandler {
	
	public static final String PLAYER = "player_dispatch";
	
	public static final String ADMIN = "admin_dispatch";
	
	public static int OPCODE_TIME_THRESHOLD = 10; // 处理opcode的警告时间，超过这个时间系统将会打出日志
	
	public static boolean flag = true; 
	
	protected Map<Integer, PacketHandler> handlers;
	/**
	 * dispatch的id，在注册的时候需要此id定位
	 */
	protected String id;
	
	protected IOLog log;
	
	public HandlerDispatch(String id, IOLog log){
		this.id = id;
		this.log = log;
		handlers = new HashMap<Integer, PacketHandler>();
	}
	
	public String getId(){
		return this.id;
	}

	public void register(int opcode,PacketHandler handler){
		handlers.put(opcode, handler);
	}

	public void register(int[] opcodes,PacketHandler handler){
		for(int opcode:opcodes){
			register(opcode,handler);
		}
	}
	
	public void unRegister(int opcode){
		handlers.remove(opcode);
	}
	
	public void unRegister(int[] opcodes){
		for(int opcode:opcodes){
			handlers.remove(opcode);
		}
	}

	@Override
	public void handle(Packet packet, ClientSession session) throws Exception {
		int opcode = packet.getOpCode();
		PacketHandler handler = handlers.get(opcode);
		if (handler != null) {
			if (flag) {
				long l1 = System.nanoTime();
				handler.handle(packet, session);
				long l2 = System.nanoTime();
				long t = (l2 - l1) / 1000000L;
				if(t > OPCODE_TIME_THRESHOLD){
					log.info("[OPCODETOOLONG][{" + opcode + "},{" + t + "}]");
				}
			} else {
				handler.handle(packet, session);
			}
		}
	}

}
