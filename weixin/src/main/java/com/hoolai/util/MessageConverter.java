package com.hoolai.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;

public class MessageConverter {

	public MessageConverter() {
	}
	
	public static String readFromInputStream(InputStream in, int size) {
		String xml = "";
		byte[] buffer = new byte[size];
		byte[] xmldataByte = new byte[size];
		int count = 0;
		int rbyte = 0;
		try {
			while (count < size) {
				rbyte = in.read(buffer);

				for (int i = 0; i < rbyte; i++) {
					xmldataByte[count + i] = buffer[i];
				}
				count += rbyte;
			}
			in.close();
			xml = new String(xmldataByte, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * 解析并封装XML字符串
	 * 
	 * @param xmlData
	 *            POST请求发送过来的数据字符串
	 * @return Map<String, Object>
	 * @throws DocumentException
	 */
	public static Map<String, Object> toMapFromXML(String xmlData) {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (xmlData != null && !xmlData.trim().equals("")) {
			try {
				Document document = DocumentHelper.parseText(xmlData)
						.getDocument();
				Element root = document.getRootElement();
				msg.put("MsgType", root.element("MsgType").getText());
				msg.put("FromUserName", root.elementText("FromUserName"));
				msg.put("ToUserName", root.elementText("ToUserName"));
				msg.put("Content", root.elementText("Content"));
			} catch (DocumentException e) {
				msg.clear();
				Exceptions.unchecked(e);
			}
		}
		return msg;
	}
	
	/**
	 * 查询自动回复的内容
	 * @param fromMsg 来自微信端的key-value
	 * @param reply  从数据库传来的匹配配置
	 * @return  返回xml格式的字符串
	 */
	public static String toStringFromMap(Map<String, Object> fromMsg, Map<String, Object> reply){
		String toMsg = "";
		try{
			if(fromMsg != null && !fromMsg.isEmpty()){
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("xml");
				root.addElement("ToUserName").add(new DefaultCDATA(fromMsg.get("FromUserName").toString()));
				root.addElement("FromUserName").add(new DefaultCDATA(fromMsg.get("ToUserName").toString()));
				root.addElement("CreateTime").setText(
						System.currentTimeMillis() / 1000 + "");
				String msgType = reply.get("key_type").toString();
				root.addElement("MsgType").add(new DefaultCDATA(msgType));
				if("text".equals( msgType)){
					//1.如果是文本类型则返回文本
					root.addElement("Content").setText(reply.get("reply").toString());
				} else if("iamge".equals(msgType)){
					//2.如果是图片类型
				} else if("news".equals(msgType)){
					//3.如果是图文类型
					root.addElement("ArticleCount").setText("1");
					Element articles =root.addElement("Articles");
					Element item = articles.addElement("item");
					item.addElement("Title").add(new DefaultCDATA(reply.get("title").toString()));
					item.addElement("Description").add(new DefaultCDATA(reply.get("image_desc").toString()));
					item.addElement("PicUrl").add(new DefaultCDATA(reply.get("picUrl").toString()));
					item.addElement("Url").add(new DefaultCDATA(reply.get("linked").toString()));
				}
				toMsg = document.asXML();
			}
		} catch(Exception e){
			Exceptions.unchecked(e);
			toMsg = "";
		}
		return toMsg;
	}
	
}
