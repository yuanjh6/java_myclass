package com.yuan.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DBase{


	private String driver=null;
	private String url=null;
	private Connection conn;

	/**单例模式
	 * @param args
	 * @return 
	 * @throws ClassNotFoundException
	 *             驱动类名：com.mysql.jdbc.Driver
	 *             URL格式：jdbc:mysql://servername:port/database
	 * @throws SQLException
	 */
	private DBase() {
		try {
			Class.forName(driver.trim());
			conn = DriverManager.getConnection(url.trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static class Holder {   
        static DBase instance = new DBase();   
    } 

    public static DBase getInstance() { 
        return Holder.instance;   
    }   

	public void close() throws SQLException {
		this.conn.close();
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	public ArrayList<Map<String, String>> RSToMap(ResultSet rs)
			throws SQLException {
		ArrayList<Map<String, String>> tmpret = new ArrayList<Map<String, String>>();

		ResultSetMetaData rsMeta = rs.getMetaData();
		int b = rsMeta.getColumnCount();
		String[] fieldList = new String[b];
		for (int i = 1; i <= b; i++) {
			fieldList[i - 1] = rsMeta.getColumnName(i);
		}

		while (rs.next()) {
			Map<String, String> map = new HashMap<String, String>();
			for (String field : fieldList) {
				map.put(field, rs.getString(field));
			}
			tmpret.add(map);
		}
		return tmpret;
	}

}

