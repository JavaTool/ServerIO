package com.fanxing.server.utils;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

public class XmlUtil {
	
	/**
	 * 加载一个XML文档，以节点名称(key)-节点内容(value)组织成一个Map。
	 * 该XML文档只支持一层节点(不重复节点名称)。
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> loadOne(File dir) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(dir);
		Element element = document.getDocumentElement();
		NodeList childNodes = element.getChildNodes();
		for (int i = 0;i < childNodes.getLength();i++) {
			Node node = childNodes.item(i);
			String nodeName = node.getNodeName();
			if (!nodeName.startsWith("#")) {
				map.put(nodeName, node.getTextContent());
			}
		}
		return map;
	}
	
	/**
	 * 加载某一目录下所有XML文档，以文件名称(row)-节点名称(column)-节点内容(value)组织成一个Table。
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public static Table<String, String, String> loadGroup(File dir) throws Exception {
		Table<String, String, String> table = HashBasedTable.create();
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".xml")) {
				String rowName = file.getName().split("\\.")[0];
				loadOne(file).forEach((k, v) -> table.put(rowName, k, v));
			}
		}
		return table;
	}

}
