package com.hoolai.jndi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.hoolai.util.JNDIUtil;

public class TestJNDI {

	@Test
	public void testDS() {

		DataSource ds = JNDIUtil.getDataSource("jdbc/weixin");
		try {
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select id, name from tb_test");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
