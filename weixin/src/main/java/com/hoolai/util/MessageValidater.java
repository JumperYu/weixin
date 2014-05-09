package com.hoolai.util;

import java.util.Map;

public class MessageValidater {

	/**
	 * 验证消息来源是否符合规则
	 * 
	 * @param fromMsg
	 * @return
	 */
	public static boolean correctMsgType(Map<String, Object> fromMsg) {
		if (fromMsg != null && !fromMsg.isEmpty()) {
			try {
				String msgType = fromMsg.get("MsgType").toString();
				if ("text".equals(msgType)) {
					return true;
				} else {
					return false;
				}//-->> End of msgType text return true.
			} catch (Exception ex) {
				Exceptions.unchecked(ex);
				return false;
			}//-->> End any of Exception
		}//-->> End If Then Return false
		return false;
	}
}
