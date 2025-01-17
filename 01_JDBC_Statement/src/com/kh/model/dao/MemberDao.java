package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// Dao (Data Access Object) : DB와 직접적으로 접근하는 담당
// 1) DB에 접속(연결)하여
// 2) sql문을 실행 및 결과 받기 (select문일 경우 : 조회된 reseltSet / dml문 일 경우 : 처리된 행의 개수)
// 	    만일 dml문 수행했다면 트랜잭션 처리하기 (성공이면 commit, 실패면 rollback)
// 3) 결과 반환 
public class MemberDao {

	/*
	 * * JDBC 객체
	 * - Connection : DB의 연결정보를 담는 객체 
	 * - [Prepared]Statement : DB에 SQL문을 전달해서 실행하고 그결과를 받아내는 객체 
	 * 			> Statement : 실행시킬 sql문이 완성형태여야됨 ! 
	 * 			> PrerparedStatement : 실행시킬 sql문이 미완성 상태여도됨 
	 * - ResultSet : Select문 수행 후 조회 된 결과들이 담겨있는 객체 
	 * 
	 * * 처리순서
	 * 1) jdbc driver 등록 : 해당 DBMS가 제공하는 클래스로 등록 
	 * 2) Connection 객체 생성 : 연결하고자 하는 DB정보를 입력해서 DB와 연결하며 생성
	 * 3) Statement 객체 생성 : Connection 객체를 이용해서 생성(sql문 실행 및 결과를 담당 하는 객체)
	 * 4) SQL문 전송 후 실행 및 결과 받기 : Statement 객체를 이용해서 sql문 실행 후 결과 받기 
	 * 5) 실행결과 --> select문 일 경우 --> ResultSet객체 (조회된 데이터들이 담겨있음) --> 6_1)
	 * 			--> dml문일 경우 	 --> int (처리된 행의 개수)				 --> 6_2)
	 * 
	 * 6_1) ResultSet에 담겨있는 데이터들 하나씩 뽑아서 vo객체에 주섬주섬 담기
	 * 6_2) 트랜잭션 처리(성공이면 commit, 실패면 rollback)
	 * 7) 다 쓴 JDBC용 객체들 반드시 자원 반납(close) --> 생성된 역순으로
	 * 
	 */
	public int insertMember(Member m) { // insert문 
		
		// 필요한 변수들 먼저 셋팅
		
		// 처리된 결과(처리된 행의 갯수)를 받아줄 변수
		int result = 0;
		// DB의 연결 정보를 담는 객체 선언
		Connection conn = null;
		// SQL문을 전송해서 실행 후 결과 받는 객체 선언
		Statement stmt = null;
		
		// 실행할 sql문 (완성형태로 만들어줄 것!!) --> 끝에 세미콜론 있으면 안됨!!!
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'user02', 'pass02', '홍길녀', 'F', 20, .....)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
										   + "'" + m.getUserId()   + "', "
										   + "'" + m.getUserPwd()  + "', "
										   + "'" + m.getUserName() + "', "
										   + "'" + m.getGender()   + "', "
										   + 	   m.getAge()      + ", "
										   + "'" + m.getEmail()    + "', "
										   + "'" + m.getPhone()    + "', "
										   + "'" + m.getAddress()  + "', "
										   + "'" + m.getHobby()    + "', SYSDATE)";
		
		//System.out.println(sql);
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 오타있거나, ojdbc6.jar 파일이 추가되어있지 않을 경우 ClassNotFoundException
		
			// 2) Connection 객체 생성 (DB에 연결 --> url, 계정명, 계정비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) sql문 해당 db에 전달 후 실행 --> 결과 받기 (처리된 행의 갯수)
			result = stmt.executeUpdate(sql); // --> insert, update, delete 구문일 경우 executeUpdate 메소드로 sql문 실행
			
			System.out.println(result);
			
			// 6_2) 트랜잭션 처리
			if(result > 0) { // 성공했을 경우 commit
				conn.commit();
			}else { // 그게 아닐 경우 rollback
				conn.rollback();
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다쓴 JDBC용 객체 자원 반납 (close) 단, 생성된 역순으로
			try {
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
		
	}
	
	public ArrayList<Member> selectList() { //select문
		
		
		// 처리된 결과(조회된 회원들== 여러행) 들을 담아줄 ArrayList생성
		ArrayList<Member> list = new ArrayList<>();
		// DB의 연결정보를 담는 객체 선언
		Connection conn = null;
		// SQL문 실행 후 결과 받는 객체 선언
		Statement stmt = null;
		// Select문 실행 후 조회 결과값들이 실질적으로 담길 ResultSet 객체 선언
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
			// 1) jdbc driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connectrion 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "JDBC", "JDBC");
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) SQL문 전송해서 실행 후 결과받기 
			rset = stmt.executeQuery(sql); // select문은 executeQuery 메소드를 이용 
											// insert, update, delete문은 executeUpdate 메소드 이용 
			
			// 6_1) ResultSet에 담겨있는 데이터 하나씩하나씩 뽑아서 주섬주섬 담기
			while(rset.next()) {
				
				Member m = new Member();
				// 현재 rset의 커서가 가리키고 있는 행의 데이터를 뽑아서 담기 (컬럼명 제시해서 해당 컬럼값만 뽑기)
				m.setUserNO(rset.getInt("USERNO")); 
				// rset 첫번쨰 행전체중에 USERNO을 getint메소드로 가져온다.int형으로
				// 가져온 값을 set메소드를 통해 필드값으로 넣어준다.
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("username"));
				m.setGender(rset.getString("gender"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setPhone(rset.getString("phone"));
				m.setAddress(rset.getString("address"));
				m.setHobby(rset.getString("hobby"));
				m.setEnrollDate(rset.getDate("enrolldate"));
				
				// 리스트에 해당 완성된 회원 한행씩 추가 
				list.add(m);
				
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다쓴 jdbc용 객체 자원반납하기
			
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	public Member selectByUserId(String userId) { // select문
		
		// 필요한 변수를 세팅
		
		// 처리결과 (한회원)을 담을 변수
		Member m = null;
		// db 연결정보담는 객체
		Connection conn = null;
		// sql문 실행 후 결과 받는 객체
		Statement stmt = null;
		// 조회결과 담기는 객체
		ResultSet rset = null;
		
		// 실행할 sql문 완성형태로 
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
			// 1)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			// 3)
			stmt = conn.createStatement();
			
			// 4,5)
			rset = stmt.executeQuery(sql); // 조회가 되었다면 한 행 담겨있을 것 
			// 6_1)
			// 조회 결과가 한행이기 때문에 반복문 없이 조건문만으로 !
			if(rset.next()) {
				// 조회가 되었다면 이때 생성해줄 것임 
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
				
				//m.setUserNO(rset.getInt("userno"));
				//m.setUserId(rset.getString("userid"));
				
			}
			
		} catch (ClassNotFoundException e) {	
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return m;
	}
	public ArrayList<Member> selectByUserName(String keyword) {
		
		// 필요한 변수를 세팅
		
		
		// 처리결과를 최종적으로 담아줄 ArrayList생성
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// sql 완성형ㅌㅐ로
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%" 
						+ keyword + "%'" ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC","JDBC");
			stmt = conn.createStatement();
			rset =  stmt.executeQuery(sql);
			
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
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	public int updateMember(Member m) {// update문
		
		// 처리 결과를 담아줄 변수 
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문 완성형태로'
		String sql = "UPDATE MEMBER "// 공백 중요함
				   + "SET USERPWD = '" + m.getUserPwd() + "', "
				   + 	   "EMAIL = '" + m.getEmail() 	+ "',"
				   +	   "PHONE = '" + m.getPhone() 	+"', "
				   +	 "ADDRESS = '" + m.getAddress() +"' "
				   +"WHERE USERID = '" + m.getUserId()	+"' ";
		
		try { // jdbc 과정 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC","JDBC");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			
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
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				  
		
		return result;
		
	}
	
	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID='" + userId +"'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC","JDBC");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
		
	}
}
