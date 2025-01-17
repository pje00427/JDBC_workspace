package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// view : 사용자가 보게 될 화면
public class MemberMenu {

	
	private Scanner sc = new Scanner(System.in);
	// MemberController 객체 생성 (전역에서 바로 요청할 수 있게)
	private MemberController mc = new MemberController();
	
	/**
	 * 사용자가 보게될 첫 화면
	 */
	public void mainMenu() {
		
		while(true) {
			System.out.println("\n=== 회원 관리 프로그램 ===");
			System.out.println("1. 회원가입");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디 검색");
			System.out.println("4. 회원 이름으로 검색");
			System.out.println("5. 회원정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.println("번호 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1:	insertMember();		break;
			case 2: mc.selectList();	break;
			case 3:	String userId = inputMemberId();	
					mc.selectByUserId(userId);
					break;
			case 4:	//String userName = inputMemberName();
					//mc.selectByUserName(userName);
					mc.selectByUserName(inputMemberName());
					break;
			case 5:	updateMember();		break;
			case 6:	mc.deleteMember(inputMemberId());		break;
			case 0:	System.out.println("프로그램을 종료합니다."); return;
			default: System.out.println("번호를 잘못입력했습니다. 다시입력해주세요."); 
			}
		}
	}
	/**
	 * 회원가입 창(화면)
	 * 
	 * 즉, 회원의 정보를 입력 받는 메소드
	 */
	public void insertMember() {
		
		System.out.println("\n==회원가입===");
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		System.out.print("비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별(M/F) : ");
		String gender = sc.nextLine().toUpperCase();
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(-빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 공백없이 나열) : ");
		String hobby = sc.nextLine();
		
		// 회원가입 요청 ! (Controller 메소드 호출)
		// 매개변수가 너무 많아서 다 기술하기 어려우니 통째로 전달하자 
		
		// Member 객체 생성 후 주섬주섬 담자
		// 매개변수 생성자 추가적으로 만들어줘야한다. 기존 생성자랑 담는 개수가 틀리기 때문에
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		mc.insertMember(m);
		
	}
	
		
	/**
	 * 사용자에게 조회할 회원 아이디 입력받은 후 그 아이디 반환하는 메소드 
	 * @return	--> 사용자가 입력한 아이디 
	 */
	public String inputMemberId() {
		System.out.print("회원 아이디 입력 : ");
		String userId = sc.nextLine();
		return userId;
	}
	/**
	 * 사용자에게 조회할 회원명 (키워드)을 입력받은 후 반환하는 메소드 
	 * @return
	 */
	public String inputMemberName() {
		System.out.print("회원 이름(키워드) 입력 :");
		return sc.nextLine();
	}
	/**
	 * 사용자에게 변경할 정보들과 해당 회원의 아이디 입력받는 화면
	 */
	public void updateMember() {
		
		Member m = new Member();
		
//		System.out.println("아이디 : ");
//		m.setUserId(sc.nextLine());
		m.setUserId(inputMemberId());
		
		System.out.print("변경할 암호 :");
		m.setUserPwd(sc.nextLine());
		
		System.out.print("변경할 이메일 :");
		m.setEmail(sc.nextLine());
		
		System.out.print("변경할 전화번호(-빼고)입력 : ");
		m.setPhone(sc.nextLine());
		
		System.out.print("변경할 주소 : ");
		m.setAddress(sc.nextLine());
		
		// m : 변경하고자 하는 회원아이디, 변경할 암호, 변경할 이메일, 변경할 전화번호, 변경할 주소
		
		mc.updateMember(m);
		
	}
	
	
	
	// 서비스 요청 처리 후 사용자가 보게 될 응답화면 
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " + message);
	}
	public void displayFail(String message) {
		System.out.println("서비스 요청 실패 :" + message);
	}
	public void displayNoData(String message) {
		System.out.println(message);
	}
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("\n조회된 데이터는 다음과 같습니다.\n");
		
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	public void displayMember(Member m) {
		System.out.println("\n조회된 데이터는 다음과 같습니다.\n");
		System.out.println(m);
	}
	
	
}
