package com.hoolai.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIUtil {

	private static DataSource dataSource;
	private static Connection conn;

	public static DataSource getDataSource(String naming) {

		Context initContext;
		Context envContext;
		try {
			if (dataSource == null) {
				initContext = new InitialContext();
				envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup(naming);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return dataSource;

	}

	public static Connection getConnection(String naming) {
		try {
			if (conn == null)
				conn = getDataSource(naming).getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
