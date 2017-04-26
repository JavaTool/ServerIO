package org.tool.server.io.http.server;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.google.common.collect.Lists.asList;
import static com.google.common.collect.Lists.newLinkedList;
import static org.tool.server.io.IOParam.CODE_ERROR;
import static org.tool.server.io.IOParam.CODE_OK;
import static org.tool.server.io.IOParam.RET_CODE;
import static org.tool.server.io.IOParam.RET_LIST_SIZE;
import static org.tool.server.io.IOParam.RET_MSG;
import static org.tool.server.utils.HttpConnectUtil.getRequestProtoContent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tool.server.coder.stream.IStreamCoder;
import org.tool.server.coder.stream.StreamCoders;
import org.tool.server.utils.Services;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public abstract class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final IStreamCoder coder = StreamCoders.newProtoStuffCoder();
	
	protected static final Logger log = LoggerFactory.getLogger(Logger.class);
	
	protected static final List<byte[]> EMPTY_LIST = newLinkedList();
	
	protected static final String ENC = "utf-8";
	
	protected <X, Y extends X> Y getService(HttpServletRequest request, Class<X> clz) {
		return ((Services) request.getServletContext().getAttribute(Services.class.getName())).getService(clz);
	}
	
	protected void work(HttpServletRequest req, HttpServletResponse resp, Work work) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type","text/html;charset=UTF-8");
		JSONObject jsonObject = new JSONObject();
		List<byte[]> list = null;
		try {
			list = work.work(req, resp, jsonObject);
		} catch (Exception e) {
			log.error("", e);
			writeError(jsonObject, e.getMessage());
			list = EMPTY_LIST;
		}
		writeBytesList(jsonObject, list);
		PrintWriter pw = resp.getWriter();
		pw.append(jsonObject.toJSONString());
		pw.flush();
	}
	
	protected void writeError(JSONObject jsonObject, String msg) {
		jsonObject.put(RET_CODE, CODE_ERROR);
		jsonObject.put(RET_MSG, msg);
	}
	
	protected void writeOK(JSONObject jsonObject) {
		jsonObject.put(RET_CODE, CODE_OK);
	}
	
	protected void writeOK(JSONObject jsonObject, String msg) {
		jsonObject.put(RET_CODE, CODE_OK);
		jsonObject.put(RET_MSG, msg);
	}
	
	protected void writeBytesList(JSONObject jsonObject, List<byte[]> list){
		jsonObject.put(RET_LIST_SIZE, list.size());
		for(int i = 0; i < list.size(); i++){
			jsonObject.put(String.valueOf(i), list.get(i));
		}
	}
	
	protected static <T> List<byte[]> write(T t) throws Exception {
		return t != null ? asList(coder.write(t), new byte[0][]) : newLinkedList();
	}
	
	protected static <T> List<byte[]> write(List<T> list) throws Exception {
		List<byte[]> ret = newLinkedList();
		for (T t : list) {
			ret.addAll(write(t));
		}
		return ret;
	}
	
	protected static <T> T read(byte[] data) {
		try {
			return coder.read(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> List<T> readList(JSONObject jsonObject){
		List<T> list = Lists.newArrayList();
		int size = jsonObject.getInteger(RET_LIST_SIZE);
		for(int i = 0; i < size; i++){
			byte[] data = jsonObject.getBytes(String.valueOf(i));
			if(data!=null){
				list.add(read(data));
			}
		}
		return list;
	}
	
	protected static <T> T readObject(HttpServletRequest request) throws Exception {
		byte[] data = getRequestProtoContent(request);
		return read(data);
	}
	
	protected static JSONObject readJson(HttpServletRequest request) throws Exception {
		return parseObject(new String(getRequestProtoContent(request), "utf-8"));
	}
	
	protected static interface Work {
		
		List<byte[]> work(HttpServletRequest req, HttpServletResponse resp, JSONObject jsonObject) throws Exception;
		
	}

}
