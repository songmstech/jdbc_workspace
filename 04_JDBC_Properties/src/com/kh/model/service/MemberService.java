package com.kh.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

// Service : DB와 연결시키는 Connection 객체 생성 후 
//			 Dao 호출 (이때, 생성된 Connextion객체와 Controller로 부터 전달된 값 같이 넘겨줌)
//           돌아오는 반환값 받아서 만약에 트랜잭션 처리가 필요한 경우 트랜잭션 처리도 진행

public class MemberService {
	
	public int insertMember(Member m) {
		/*
		// 처리결과 받을 변수
		int result = 0;
		
		// DB 연결정보를 담는 객체
		Connection conn = null;
		
		// jdbc driver
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			result = new MemberDao().insertMember(conn, m);
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		*/
		
		
		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	
	public ArrayList<Member> selectList() {
		
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().selectList(conn);
		
		close(conn);
		
		return list;
	}

	public Member selectByUser(String userId) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().selectByUserId(conn,userId);
		
		return m;
	}

	public ArrayList<Member> selectByUserName(String keyword){
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		close(conn);
		
		return list;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public Member loginMember(String userId, String userPwd) {
		Connection conn = getConnection();
		
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		close(conn);
		
		return m;
	}

}































