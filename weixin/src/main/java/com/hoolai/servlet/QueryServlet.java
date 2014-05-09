package com.hoolai.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hoolai.util.JNDIUtil;

/**
 * Servlet implementation class QueryServlet
 */
//@WebServlet("/query")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		conn = JNDIUtil.getConnection("jdbc/weixing");
	}

	/**
	 *  doGet
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//DataSource ds = JNDIUtil.getDataSource("jdbc/weixin");
		try {
			//Connection conn = ds.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select id, name from tb_test");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  doPost
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	@Override
	public void destroy() {
		super.destroy();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
