package com.kh.model.vo;

import java.sql.Date;

/*
 * * VO (Value Object)
 * DB 테이블의 한 행의 데이터가 기록되는 저장용 객체 
 * 
 * * 유사용어
 * 	 DTO (Data Transfer Object)
 * 	 DO (Domain Object)
 * 	 Enity (--> Strut에서는 이 용어로 사용 
 * 	 bean (--> EJB에서 사용)
 * 
 * * VO 조건
 * 	1) 반드시 캡슐화 적용할 것 : 모든 필드는 private
 *  2) 기본생성자 및 매개변수생성자 작성할 것 
 *  3) 모든 필드에 대한 setter/getter 메소드 필요 
 *  
 * 
 */

public class Member {

	// 필드는 DB컬럼 정보와 동일하게 작업하자
	private int userNO;
	private String userId;
	private String userPwd;
	private String userName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String hobby; // --> java.sql.Date import해야됨
	private Date enrollDate;

	public Member() {

	}
	
// 단축키 alt + shift + s
	// 모든 필드값 전달받아서 초기화하는 생성자
	public Member(int userNO, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		super();
		this.userNO = userNO;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}
	

	public int getUserNO() {
		return userNO;
	}
	// 회원가입시 사용할 매개변수 생성자
	public Member(String userId, String userPwd, String userName, String gender, int age, String email, String phone,
			String address, String hobby) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}

	public void setUserNO(int userNO) {
		this.userNO = userNO;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return userNO + ", " + userId + ", " + userPwd + ", " + userName
				+ ", " + gender +", " + age + ", " + email + ", " + phone + ", "
				+ address + ", " + hobby + ", " + enrollDate ;
	}
	
	
}
