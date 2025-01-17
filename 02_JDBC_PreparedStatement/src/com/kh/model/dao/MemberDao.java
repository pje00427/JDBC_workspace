package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

public class MemberDao {
	/*
	 * * Statement와 PrepareStatement 차이점
	 * 
	 *  - Statement같은 경우 완성된 sql문을 바로 실행하는 객체(sql문을 완성형태로 만들어둬야됨)
	 *  - PreparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체 
	 *    해당sql문 실행하기 전 완성형태로 만든 후 실행하면 됨 
	 *  
	 * * 기존의 Statement 방식
	 * Connection 객체를 통해 Statement 객체 생성	: stmt = conn.createStatement();
	 * Statement 객체를 통해 완성된 sql문 실행 및 결과 	: 결과     = stmt.executeXXX(완성된 sql);
	 * 
	 * * PreparedStatement 방식
	 *  Connection 객체를 통해 preparedStatement 객체 생성
	 *  			(단, 미완성된 sql문을 담은채로 생성)      : pstmt = conn.prepareStatement(미완성된sql);
	 *  PreparedStatement 에서 제공하는 메소드를 통해 완성형태로   : pstmt.setXXX(1, "대체할값");...
	 *  그리고 나서 해당 완성된 sql문 실행 및 결과 					: 결과 = pstmt.executeXXX();
	 *  
	 * 
	 */
	public int insertMember(Member m) { // insert문 --> 처리된 행의 개수(int)
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행하고자 하는 sql문 미완성된 형태로 둘 수 있음! --> 미리 사용자가 입력한 값들 들어갈 수 있게 공간(?) 확보
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); //PreparedStatement 객체생성시 sql문담은채로 생성
			
			// 현재 담긴 sql문 미완성된 상태기 때문에 바로 실행은 불가! --> 각각의 공간에 실제 값 대체한 후 실행
			pstmt.setString(1, m.getUserId()); // -> setString으로 대체시 'user01'
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt   (5, m.getAge());	// --? setInt로 대체시 20 (홑따옴표 안붙어서 들어간다)
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby()); // 완성시키고 나서 결과 받기 
			
			result = pstmt.executeUpdate(); // sql문 전달하지 않고 실행만 하면 됨 ! 왜? pstmt에 완성된 형태가 담겨있기 때문에
			
			if (result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return result;
		
	}


	public ArrayList<Member> selectList() {
		// 처리된 결과(조회된 회원들== 여러행) 들을 담아줄 ArrayList생성	
		ArrayList<Member> list = new ArrayList<>();
		// DB의 연결정보를 담는 객체 선언
		Connection conn = null;
		// SQL문 실행 후 결과 받는 객체 선언
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		// Select문 실행 후 조회 결과값들이 실질적으로 담길 ResultSet 객체 선언
		ResultSet rset = null;	
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); //PreparedStatement 객체생성시 sql문담은채로 생성
			
			// 현재 pstmt에 담긴 sql문이 애초에 완성형태이기때문에 바로 실행 가능
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
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		// 처리결과 (한회원)을 담을 변수
		Member m = null;
		// db 연결정보담는 객체
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		// 조회결과 담기는 객체
		ResultSet rset = null;		
		// 미완성형태로
		String sql = "SELECT * FROM MEMBER WHERE USERID = ? ";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
		
	}
	
	
	
	public ArrayList<Member> selectByUserName(String keyword){
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'";
		
		// 위와 같이 sql문 제시 한 후  pstmt.setString(1, "강"); 하게 되면.. 
		// SELECT * FROM MEMBER WHERE USERNAME LIKE '%'강'%'		=>  요렇게 될것..
		
		// 해결 방법1.
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		// 해결 방법2.
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		// '%'|| '강' || '%'  => '%강%'
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			// 해결 방법1 의 sql문일 경우.
			//pstmt.setString(1, "%" + keyword + "%");	
			
			// 해결 방법2 의 sql문일 경우.
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery(); // pstmt에 완성형이 담겨있기 때문에 이때 실행하면되고, sql을 매개변수로 담을 필요 없다
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("USERNO"), // 컬럼명대신 1,2,로 기입해도 된다
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
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return list;
	}
	
	public int updateMember(Member m) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET USERPWD=?, EMAIL=?, PHONE=?, ADDRESS=? WHERE USERID=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
		
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	public boolean setlogin(String myid, String mypwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean m = false;
		String sql = "SELECT * FROM MEMBER WHERE MYID=? AND MYPWD=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KH", "KH");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, myid);
			pstmt.setString(2, mypwd);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				 m = rset.next();
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} return m;
	}
	public Member loginMember(String userId, String userPwd) {
		
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID=? AND USERPWD=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
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
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}
	
}
