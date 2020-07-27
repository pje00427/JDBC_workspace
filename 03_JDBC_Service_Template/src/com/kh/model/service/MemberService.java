package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
// Service : DB와 연결시키는 connection 객체 생성 후 
//			DAO 호출 (이때, 생성된 Connection 객체와 Controller로 부터 전달된 값 같이넘겨줄 거임)
//			돌아오는 반환값 받아서 만약에 트랜잭션 처리가 필요할 경우 트랜잭션 처리도 여기서 진행	
	public int insertMember(Member m) {
		/*
		// 처리 결과를 받을 변수
		int result = 0;
		
		// DB의 연결정보를 담는 객체
		Connection conn = null;
		
		try {
			// jdbc driver 등록처리
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			result = new MemberDao().insertMember(conn, m);
			
			// 트랜잭션 처리
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		*/
		
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		// 트랜잭션
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	
	public ArrayList<Member> selectList() {
		
		Connection conn = JDBCTemplate.getConnection();  // 알아서db와연결된 커넥션객체 반환해준다
		ArrayList<Member>list = new MemberDao().selectList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
		
	}
	
	
	public Member selectByUserId(String userId) {
		
		Connection conn = JDBCTemplate.getConnection();
		Member m = new MemberDao().selectByUserId(conn, userId); // 커넥션객체 꼭 같이전달
		
		JDBCTemplate.close(conn);
		
		return m;
	}

	public ArrayList<Member> selectByUserName(String keyword){
		
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		JDBCTemplate.close(conn);
		return list;
	}
	public int updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().updateMember(conn, m);
		
		JDBCTemplate.close(conn);
		
		return result;
		
	}
}