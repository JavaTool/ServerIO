package org.tool.server.word;

import java.util.List;

import org.tool.server.io.http.client.HttpConnectorFactory;
import org.tool.server.io.http.client.IHttpConnector;
import org.tool.server.io.http.server.BaseServlet;

import com.alibaba.fastjson.JSONObject;

public class RemoteI18NDictionary extends I18NDictionary {
	
	//private final IStreamCoder coder;

	public RemoteI18NDictionary(String path) {
		super(path);
		//coder = StreamCoders.newProtoStuffCoder();
	}

	@Override
	protected List<String> read() throws Exception {
		IHttpConnector<JSONObject> httpConnector = HttpConnectorFactory.createTrueJSONObject();
		httpConnector.setUrl(path);
		JSONObject jsonObject = httpConnector.get();
		return BaseServlet.readList(jsonObject);
	}
	
//	private List<String> bytesToStringList(byte[] bytes) throws Exception {
//		List<byte[]> byteList = coder.read(bytes);
//		List<String> stringList = Lists.newArrayListWithCapacity(byteList.size());
//		for (byte[] datas : byteList) {
//			stringList.add(coder.read(datas));
//		}
//		return stringList;
//	}

}
