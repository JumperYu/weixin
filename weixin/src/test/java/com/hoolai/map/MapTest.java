package com.hoolai.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class MapTest {

	@Test
	public void testSort() {

		final Map<String, String> map = new HashMap<String, String>();
		map.put("abc", "123");
		map.put("dbc", "123");
		map.put("bc", "123");
		map.put("ebc", "123");
		map.put("cbc", "123");
		
		sort(map);

		Object[] arrys = map.keySet().toArray();

		Arrays.sort(arrys);
		for (Object o : arrys)
			System.out.println(o);

	}

	@Test
	public void testSha1(){
		
		String signature = "5c3e606008b392ce6a1c853ab8e062d60dc7d40a";
		//String echostr = "4979467411003211355";
		String timestamp = "1397800027";
		String nonce = "1602387238";
		String token = "hoolaiweixin";
		
		String[] arry = new String[]{timestamp, nonce, token};
		Arrays.sort(arry);
		
		StringBuffer sb = new StringBuffer();
		for(String s : arry){
			sb.append(s);
		}
		String sha1Mine = DigestUtils.sha1Hex(sb.toString());		
		System.out.println(sha1Mine);
		System.out.println(signature);
		
	}

	public static String getFormattedText(byte[] bytes) {
		//final String ALGORITHM = "MD5";

		final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
 
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);

			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);

		}

		return buf.toString();

	}
	
	private void sort(Map<String, String> param){
		param = new HashMap<String, String>(param);
		param.remove("abc");
		param.remove("bc");
	}
}
