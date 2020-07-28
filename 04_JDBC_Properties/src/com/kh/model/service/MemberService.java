package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

//import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import static com.kh.common.JDBCTemplate.*;


public class MemberService {
// Service : DB와 연결시키는 connection 객체 생성 후 
//			DAO 호출 (이때, 생성된 Connection 객체와 Controller로 부터 전달된 값 같이넘겨줄 거임)
//			돌아오는 반환값 받아서 만약에 트랜잭션 처리가 필요할 경우 트랜잭션 처리도 여기서 진행	
	public int insertMember(Member m) {
		

		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		// 트랜잭션
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	
	public ArrayList<Member> selectList() {
		
		Connection conn = getConnection();  // 알아서db와연결된 커넥션객체 반환해준다
		ArrayList<Member>list = new MemberDao().selectList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	
	public Member selectByUserId(String userId) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().selectByUserId(conn, userId); // 커넥션객체 꼭 같이전달
		
		close(conn);
		
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