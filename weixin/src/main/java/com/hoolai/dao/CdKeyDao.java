package com.hoolai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hoolai.util.JNDIUtil;

public class CdKeyDao {

	private static final String naming = "jdbc/weixin";

	// 一个微信号最多领取一个cd_key,且游戏角色不允许重复使用cd_key
	private static final String KEY_ONLY = "您已领取过cd_key,请继续关注相关活动.";

	private static Connection conn;

	static {
		conn = JNDIUtil.getConnection(naming);
	}
	
	// -->> 初始化
	public CdKeyDao() {
		try {
			if(conn == null || conn.isClosed()){
				JNDIUtil.getConnection(naming);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String askForKey(String fromUser) {

		String key = "";

		String sql = "select cd_key from  weixin where stat='0' "
				+ " and to_user is null and to_date is null";

		if (!hasKeyBefore(fromUser)) {

			ResultSet rs;

			try {
				rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					key = "成功向您发放cd_key: 【" + rs.getString(1) + "】, 请及时登录游戏兑换.";
					// 更新表
					nazouKey(fromUser, rs.getString(1));
				} else {
					key = "服务器发送cd_key失败, 或活动已经结束.";
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			key = KEY_ONLY;
		}

		return key;
	}

	// -->> 检查是否已经有号了
	public boolean hasKeyBefore(String fromUser) {

		String sql = "select * from weixin where 1=1"
				+ " and to_user='" + fromUser + "'";

		boolean exits = false;
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			if (rs.next())
				exits = true;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exits;
	}
	
	// -->> 拿走一个cd_key
	public int nazouKey(String fromUser, String cdKey) {
		int rs = 0;
		String sql = "update weixin set stat=?,to_user=?,to_date=now()"
					+ " where cd_key = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "1");
			ps.setString(2, fromUser);
			ps.setString(3, cdKey);
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public void closeConection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
