package com.kh.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	// 이 클래스에서의 모든 메소드들 다 static으로 작업할 것
	// SingleTon Pattern : 메모리영역에 단 한번 올라간거 재사용
	
	// Connection 객체 생성해서 반환시켜주는 메소드
	// 즉, DB와 연결시켜주는 메소드
	
	public static Connection getConnection() {
		
		Properties prop = new Properties();
		
		try {
			
			prop.load(new FileReader("resources/driver.properties"));
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
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
	
	public static void commit(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				
				conn.commit();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void rollback(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void close(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) { 
		
		try {
			
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void close(ResultSet rset) {
		
		try {
			
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}















