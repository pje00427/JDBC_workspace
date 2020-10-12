package com.kh.model.dao;

//import com.kh.common.JDBCTemplate;
import static com.kh.common.JDBCTemplate.close;

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

// DAO에서는 오로지 sql문 실행업무만 집중적으로 !
public class MemberDao {
	
	/*
	 * 기존의 방식 : Dao 클래스에 사용자가 요청할 때 마다 실행해야하는 sql문을 소스코드 내에 작성함(명시적으로 기술) => 정적코딩방식
	 * 	      문제점: sql구문 수정이 필요한 경우 소스코드를 수정하는 셈임 => 수정된 내용을 반영하고자 한다면 프로그램을 재구동 시켜야함
	 * 			==> 유지보수에 불편함 초래 
	 * 해결방식 : sql문들을 별도로 관리하는 외부파일(.properties)로 만들어서 실시간으로 동적으로 sql문을 읽어오게 할 것 임 => 동적코딩방식
	 * 
	 */
	private Properties prop = new Properties();
	
	public MemberDao() {
		// 사용자가 어떤 서비스 요청 할 때 마다 매번 new MemberDao().xxx();
		// 다음과 같이 이 기본생성자를 매번 호출했었음 
		// 파일에 기록된 값을 매번 실시간으로 prop읽어들일 수 있는 구문을 여기서 기술 할 것임
		try {
			prop.load(new FileReader("resources/query.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public int insertMember(Connection conn, Member m) {
		
		//처리된 결과를 받아줄 변수 선언 (처리된 행의 개수)
		int result = 0;
		
		// SQL문 실행시 필요한 객체 [Prepared]Statement 객체 선언 
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태여도 ㄱㅊ)
		//String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?,?,?,?,?,?,?,?,?, SYSDATE)";
		String sql = prop.getProperty("insertMember");
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8,  m.getAddress());
			pstmt.setString(9, m.getHobby());
				
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally { // 어떤예외가 발생하든 무조건 실행하는 구문
			
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Member> selectList(Connection conn) { //select문 여러행 조회
		
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
		
		//String sql = "SELECT * FROM MEMBER WHERE USERID=?";
		String sql = prop.getProperty("selectByUserId");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset =pstmt.executeQuery();
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);

		}
		
		return m;
		
	}
	
	public ArrayList<Member> selectByUserName(Connection conn, String keyword){
		
		ArrayList<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		String sql = prop.getProperty("selectByUserName");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rset =pstmt.executeQuery();
			
			
			if(rset.next()) {
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
				} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);

		}
				
		
		return list;
	}
	
	public int updateMember(Connection conn, Member m) {
		//처리된 결과를 받아줄 변수 선언 (처리된 행의 개수)
		int result = 0;
				
		// SQL문 실행시 필요한 객체 [Prepared]Statement 객체 선언 
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int deleteMember(Connection conn, String userId) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
	//	String sql = "DELETE FROM MEMBER WHERE USERID=?";
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
		
	//	String sql = "SELECT * FROM WHERE USERID=? AND USERPWD=?";
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
	 * 회원 탈퇴 정책이 바뀌었다.
	 * 회원 탈퇴 요청시 Member 테이블로부터 아예 삭제시키는게 아닌 
	 * DEL_FLAG 컬럼값을 기존에 'N'에서 'Y'로 변경하기로 한다. 
	 * 
	 * 또, 회원 조회서비스시 탈퇴한 회원들은 제외하고 조회한다.
	 * 로그인기능 또한 탈퇴한 회원은 조회가 안되게끔 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
}
