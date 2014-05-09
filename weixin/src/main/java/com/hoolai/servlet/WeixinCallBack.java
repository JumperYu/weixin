package com.hoolai.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hoolai.dao.CdKeyDao;

//@WebServlet("/callback")
public class WeixinCallBack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TOKEN = "hoolaiweixin";
	
	private static final String KEY_MSG = "全民斩仙";

	private static final String WELCOME_MSG = "1.输入【" + KEY_MSG + "】即可获得一个6位激活码;\n"
			+ "2.每个微信账户或游戏角色限领取和使用一次;\n";
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		HashMap<String, String[]> params = new HashMap<String, String[]>(
				req.getParameterMap());

		//System.out.println(params);

		String[] paramStr = params.get("echostr");
		String echo = "";
		if (paramStr != null && paramStr.length > 0) {
			if (validate(params))
				echo = paramStr[0];
		} else {
			this.doPost(req, resp);
		}

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");

		PrintWriter out = resp.getWriter();
		out.write(echo);
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String xmlData = readFromInputStream(req.getInputStream(),
				req.getContentLength());
		
		
		//xmlData = xmlData.substring(2);
		//System.out.println("from xml: " + xmlData);

		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String echo = "";

		try {
			if (xmlData != null && !xmlData.equals("")) {

				Document document = DocumentHelper.parseText(xmlData)
						.getDocument();

				Document retDoc = DocumentHelper.createDocument();
				Element root = document.getRootElement();
				Element retRoot = retDoc.addElement("xml");

				String msgType = root.element("MsgType").getText();
				String FromUserName = root.elementText("FromUserName");
				String ToUserName = root.elementText("ToUserName");
				String retContent = "";
				if ("event".equals(msgType)) {
					// 第一次关注出发
					retContent = WELCOME_MSG;
				} else if("text".equalsIgnoreCase(msgType)){
					// 接收文本
					String content = root.element("Content").getText();
					// 返回文本
					
					if(KEY_MSG.equals(content)){
						CdKeyDao keyDao = new CdKeyDao();
						retContent = keyDao.askForKey(FromUserName);
					} else{
						retContent = "输入【" + KEY_MSG + "】获取激活码";
					}
				} else{
					retContent = "输入【" + KEY_MSG + "】获取激活码";
				}

				retRoot.addElement("ToUserName").setText(FromUserName);
				retRoot.addElement("FromUserName").setText(ToUserName);
				retRoot.addElement("CreateTime").setText(
						System.currentTimeMillis() / 1000 + "");
				retRoot.addElement("MsgType").setText("text");
				retRoot.addElement("Content").setText(retContent);

				echo = retDoc.asXML();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//System.out.println("to xml: " + echo);
		out.write(echo);
		out.flush();
		out.close();
	}

	private static boolean validate(Map<String, String[]> params) {
		String signature = params.get("signature")[0];
		String timestamp = params.get("timestamp")[0];
		String nonce = params.get("nonce")[0];

		String[] arry = new String[] { timestamp, nonce, TOKEN };
		Arrays.sort(arry);

		StringBuffer sb = new StringBuffer();
		for (String s : arry) {
			sb.append(s);
		}

		String sha1Mine = DigestUtils.sha1Hex(sb.toString());
		if (sha1Mine.equals(signature)) {
			return true;
		} else {
			return false;
		}
	}

	private static String readFromInputStream(InputStream in, int size) {
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

}
