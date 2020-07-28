package com.kh.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {

	/** Connection 즉, DB와 연결 시켜주는 메소드
	 * @return 객체 생성해서 반환시켜주는 메소드
	 */
	public static Connection getConnection() {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("resources/driver.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection conn = null;
		
		try {
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"),
												prop.getProperty("username"),
												prop.getProperty("password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;	
		
	}
	
	
	// 트랜잭션 처리해주는 메소드 
	// commit 
	
	public static void commit(Connection conn) { // 전달받은 connection 객체 가지고 commit시켜주는 
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
}
