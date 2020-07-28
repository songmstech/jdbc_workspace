package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Member;

// DAO에서는 오로지 SQL문 실행 업무만 집중적으로
public class MemberDao {
	
	/*
	 * 기존의 방식 : Dao 클래스에 사용자가 요청할때 마다 실행해야되는 sql문을 소스코드 내에 작성함(명시적으로 기술) => 정적코딩방식
	 * 문제점 : SQL구문 수정이 필요한 경우 소스코드를 수정하는 셈 => 수정된 내용을 반영하고자 한다면 프로그램을 재구동 해야함
	 * 		 => 유지보수 불편
	 * 해결방식 : SQL문들을 별도로 관리하는 외부파일(.properties)로 만들어서 실시간으로 동적으로 SQL문 읽어오게 한다 => 동적코딩
	 * 
	 */
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		
		// 사용자가 어떤 서비스 요청 할 때마다 매번 new MemberDao().xxx();
		// 이 기본생성자 매번 호출
		// 따라서 query.properties 파일에 기록된 값을 매번 실시간으로 prop 읽어들일 수 있는 구문 기술
		
		try {
			
			prop.load(new FileReader("resources/query.properties"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int insertMember(Connection conn, Member m) { // insert 문
		
		// 처리된 결과를 받아줄 변수 선언 (처리된 행의 갯수)
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		// sql문
		// String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		String sql = prop.getProperty("insertMember");
		
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
		
		//String sql = "SELECT * FROM MEMBER ORDER BY ENROLLDATE ASC";
		String sql = prop.getProperty("selectList");
		
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
		
		//String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("selectByUserId");
		
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

	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		String sql = prop.getProperty("selectByUserName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
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
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public int updateMember(Connection conn, Member m) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		//String sql = "UPDATE MEMBER SET USERPWD=?, EMAIL=?, PHONE=?, ADDRESS=? WHERE USERID=?";
		String sql = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	public int deleteMember(Connection conn, String userId) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		// String sql = "DELETE FROM MEMBER WHERE USERID=?";
		String sql = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// String sql = "SELECT * FROM WHERE USERID=? AND USERPWD=?";
		String sql = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
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
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}
	
	/*
	 * 회원탈퇴 정책이 바뀌었다
	 * 회원탈퇴 요청시 MEMBER 테이블로부터 아예 삭제시키는게 아닌
	 * DEL_FLAG 컬럼값을 기존에 'N'에서 'Y'로 변경하기로 한다
	 * 
	 * 또, 회원조회 서비스시 탈퇴한 회원들은 제외하고 조회
	 * 로그인 기능 또한 탈퇴한 회원은 조회 안되게
	 */

}





























