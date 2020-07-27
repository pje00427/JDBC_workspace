package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;
//DAO에서는 오로지 SQL문 실행 업무만 집중적으로!
public class MemberDao {

	public int insertMember(Connection conn, Member m) {
		
		//처리된 결과를 받아줄 변수 선언 (처리된 행의 개수)
		int result = 0;
		
		// SQL문 실행시 필요한 객체 [Prepared]Statement 객체 선언 
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태여도 ㄱㅊ)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?,?,?,?,?,?,?,?,?, SYSDATE)";
		
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
			
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<Member> selectList(Connection conn) { //select문 여러행 조회
		
		ArrayList<Member> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER ORDER BY ENROLLDATE DESC";
		
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}
	public Member selectByUserId(Connection conn, String userId) {
		
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset =pstmt.executeQuery();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);

		}
		
		return m;
		
	}
	
	public ArrayList<Member> selectByUserName(Connection conn, String keyword){
		
		ArrayList<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);

		}
				
		
		return list;
	}
	
	public int updateMember(Connection conn, Member m) {
		//처리된 결과를 받아줄 변수 선언 (처리된 행의 개수)
		int result = 0;
				
		// SQL문 실행시 필요한 객체 [Prepared]Statement 객체 선언 
		PreparedStatement pstmt = null;
		String sql = "UPDATE MEMBER SET USERPWD=?, EMAIL=?, PHONE=?, ADDRESS=? WHERE USERID=?";
		
		
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
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
}
