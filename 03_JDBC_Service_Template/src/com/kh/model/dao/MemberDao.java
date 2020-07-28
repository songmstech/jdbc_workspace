package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

// DAO에서는 오로지 SQL문 실행 업무만 집중적으로
public class MemberDao {

	public int insertMember(Connection conn, Member m) { // insert 문
		
		// 처리된 결과를 받아줄 변수 선언 (처리된 행의 갯수)
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		// sql문
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3,m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstmt);
			
		}
	
		return result;
	}

	public ArrayList<Member> selectList(Connection conn) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				
				list.add(new Member(rset.getInt("USERNO"),
									rset.getString("USERID"),
									rset.getString("USERPWD"),
									rset.getString("USERNAME"),
									rset.getString("GENDER"),
									rset.getInt("AGE"),
									rset.getString("EMAIL"),
									rset.getString("PHONE"),
									rset.getString("ADDRESS"),
									rset.getString("HOBBY"),
									rset.getDate("ENROLLDATE")));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return list;

	}

	public Member selectByUserId(Connection conn, String userId) {
		
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt .setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
						       rset.getString("USERID"),
						       rset.getString("USERPWD"),
						       rset.getString("USERNAME"),
						       rset.getString("GENDER"),
						       rset.getInt("AGE"),
						       rset.getString("EMAIL"),
						       rset.getString("PHONE"),
						       rset.getString("ADDRESS"),
						       rset.getString("HOBBY"),
						       rset.getDate("ENROLLDATE"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			close(pstmt);
			close(rset);
			
		}
		
		return m;
	}

	public ArrayList<Member> selectByUserName(String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE %";
		
		return null;
	}

}





























