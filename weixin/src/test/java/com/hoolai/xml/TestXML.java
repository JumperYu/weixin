package com.hoolai.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TestXML {
	
	@Test
	public void testXML() throws DocumentException, IOException, SAXException{
	
		String xml = "<xml>"
				 + "<ToUserName><![CDATA[toUser]]></ToUserName>"
				 + "<FromUserName><![CDATA[fromUser]]></FromUserName>"
				 + "<CreateTime>1348831860</CreateTime>"
				 + "<MsgType><![CDATA[text]]></MsgType>"
				 + "<Content><![CDATA[this is a test]]></Content>"
				 + "<MsgId>1234567890123456</MsgId>"
				 + "</xml>";
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		String FromUserName = root.elementText("FromUserName");
		String ToUserName = root.elementText("ToUserName");
		root.element("ToUserName").setText("![CDATA[" + FromUserName + "]]");
		root.element("FromUserName").addText("![CDATA[" + ToUserName + "]]");
		root.element("Content").setText("![CDATA[CD-KEY正在筹划发放中,请继续关注公众号相关动态.]]");
		//String echo = document.asXML();
		Document retDoc = DocumentHelper.createDocument();
		Element rt = retDoc.addElement("xml");
		rt.addElement("ToUserName").setText(FromUserName);
		rt.addElement("FromUserName").setText("1");
		rt.addElement("CreateTime").setText(System.currentTimeMillis()/1000 + "");
		rt.addElement("MsgType").setText("text");
		rt.addElement("Content").setText("CD-KEY正在筹划发放中,请继续关注公众号相关动态");
		System.out.println(retDoc.asXML());
		
	}
	
	@Test
	public void testBill(){
		long timestamp = System.currentTimeMillis();
		System.out.println(timestamp / 1000);
		String beginDate= 1522901914000l + "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sd = sdf.format(new Date(Long.parseLong(beginDate)));
		System.out.println(sd);
	}
	
	@Test
	public void testLength(){
		String str ="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E5.9B.BE.E7.89.87.E6.B6.88.E6.81.AF";
		System.out.println(str.length());
	}
}
