package com.hoolai.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

public class URLUtil {

	/**
	 * 加密算法 参数 是否排序
	 * 
	 * @param digest
	 * @param param
	 * @param sort
	 * @return
	 */
	public static String encodeString(String digest,
			Map<String, String[]> param, boolean sort) {

		Object[] toArray = param.keySet().toArray();

		if (sort)
			Arrays.sort(toArray);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < toArray.length; i++) {
			Object key = toArray[i];
			String[] values = param.get(key);
			if (values != null) {
				sb.append((i == 0 ? "" : "&") + key + "=" + values[0]);
			} else {
				sb.append((i == 0 ? "" : "&") + key + "=");
			}
		}
		System.out.println(sb.toString());
		// 算法判断
		if ("sha1".equalsIgnoreCase(digest))
			return DigestUtils.sha1Hex(sb.toString());
		else if ("md5".equalsIgnoreCase(digest))
			return DigestUtils.md5Hex(sb.toString());
		else
			return "";
	}
	
}
