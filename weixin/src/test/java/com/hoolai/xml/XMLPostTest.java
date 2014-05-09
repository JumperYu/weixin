package com.hoolai.xml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class XMLPostTest {

	@Test
	public void testParam() {
		try {
			String urlStr = "http://localhost/weixin/callback";
			// String urlStr =
			// "http://sjzxwx.app1000000129.twsapp.com/weixin/callback";
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
			String xml = "<xml><ToUserName><![CDATA[gh_c03d7529eec5]]></ToUserName>"
					+ "<FromUserName><![CDATA[33]]></FromUserName>"
					+ "<CreateTime>1397818079</CreateTime>"
					+ "<MsgType><![CDATA[text]]></MsgType>"
					+ "<Content><![CDATA[12]]></Content>"
					+ "<MsgId>6003582935262850944</MsgId>" + "</xml>";
			// content = URLEncoder.encode(content, "UTF-8");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
			out.writeUTF(xml);

			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			System.out.println("=============================");
			System.out.println("Contents of post request");
			System.out.println("=============================");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("=============================");
			System.out.println("Contents of post request ends");
			System.out.println("=============================");
			reader.close();
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testURL() throws UnsupportedEncodingException {
		try {
			// String urlStr = "http://localhost/weixin/listAll";
			String urlStr = "http://cvm-proxy.opencloud.qq.com/zx/send_beans.jsp";
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("openid", "1");
			connection.setRequestProperty("beans", "1");
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n = 0;
				while ((n = in.read(buffer)) != -1) {
					out.write(buffer, 0, n);
				}
				String str = new String(out.toByteArray(), "utf-8");
				System.out.println(str);
			} else {
				String line = "";
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testX() {
		String str = "<xml>"
				+ "<ToUserName><![CDATA[odiTSt5YLqzXRN9U3gbCKdzBIEg0]]></ToUserName>"
				+ "<FromUserName><![CDATA[gh_c03d7529eec5]]></FromUserName>"
				+ "<CreateTime>12345678</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>1</ArticleCount>"
				+ "<Articles>"
				+ "<item>"
				+ "<Title><![CDATA[title1]]></Title>"
				+ "<Description><![CDATA[description1]]></Description>"
				+ "<PicUrl><![CDATA[http://sjzxwx.app1000000129.twsapp.com/weixin/static/images/weixin.jpg]]></PicUrl>"
				+ "<Url><![CDATA[http://sjzxwx.app1000000129.twsapp.com/weixin]]></Url>"
				+ "</item>"
				+ "</Articles>" + "</xml>";
		System.out.println(str);
	}

}
