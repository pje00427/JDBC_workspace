package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController();
	
	public void mainMenu() {
		
	// 특정영역안에서 계속 쓰이는 변수(박스) 계속 생성됐다 사라지는거 보다 미리선언 해두면 더좋음
		int menu;		
		while(true) {
			System.out.println("\n=== 회원 관리프로그램 ===");
			System.out.println("1. 회원가입");
			System.out.println("2. 회원 전체조회");
			System.out.println("3. 회원 아이디 검색");
			System.out.println("4. 회원 이름(키워드)으로 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("7. 로그인");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 선택 : ");
			menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: insertMember(); break;
			case 2: mc.selectList(); break;
			case 3: String userId = inputMemberId();	
					mc.selectByUserId(userId);break;
			case 4: //String userName = inputMemberName();  
					//mc.selectByUserName(userName);
					mc.selectByUserName(inputMemberName());
					break;
			case 5: updateMember(); break;
			case 6: mc.deleteMember(inputMemberId()); break;
			case 7: loginMember(); break;
			case 0: System.out.println("정말로 끝내겠습니까? (y/n) : ");
					if(sc.nextLine().toUpperCase().charAt(0) == 'Y') {
						System.out.println("프로그램을 종료합니다.");
						return; // 전체 메소드 빠져나가도록
					}else {
						break;
					}
			default:System.out.println("번호를 잘못입력했습니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	// 사용자에게 값을 입력받는 화면들
	// 1. 회원 가입 화면 (회원의 정보를 입력받는 화면)
	public void insertMember() {
		System.out.println("\n==회원가입==");
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
		
		// 회원가입 요청하기 (사용자가 입력한 값들 담은 Member객체 전달하면서)
		mc.insertMember(new Member(userId,userPwd,userName,gender,age,email,phone,address, hobby));
		
	// Member m = new Member(userId,userPwd,userName,gender,age,email,phone,address, hobby);
	// mc.insertMember(m)
		
		
	}
	// 2. 회원 아이디 입력 받는 화면 (아이디로검색, 정보수정, 회원탈퇴)
	public String inputMemberId() { // 메소드는 재사용 많이 할 수록 좋다
		System.out.println("\n회원아이디 입력: ");
		return sc.nextLine(); // 별도의 변수에 기록하지 않고 바로 리턴
		
	}
	// 3. 회원 이름 입력받는 화면 
	
	public String inputMemberName() {
		System.out.print("\n검색할 회원명(키워드) 입력 : ");
		return sc.nextLine();
	}
	
	
	// 4. 회원 정보 수정하는화면 (변경하고자 하는 회원 아이디, 변경할 비번, 변경할 이메일, 변경할 전화번호)
	
	public void updateMember() {
		System.out.println("\n==회원 정보 변경==");
		Member m = new Member(); // 기본생성자
		m.setUserId(inputMemberId()); // 변경하고자 하는 회원 아이디 담기
		
		System.out.print("변경할 비밀번호 : ");
		m.setUserPwd(sc.nextLine());
		System.out.print("변경할 이메일 : ");
		m.setEmail(sc.nextLine());
		System.out.print("변경할 전화번호(-빼고 입력) : ");
		m.setPhone(sc.nextLine());
		System.out.println("변경할 주소 : ");
		m.setAddress(sc.nextLine());
	}
	
	
	
	// 사용자가 서비스 요청 후 보게되는 응답화면들
	
	// 1. 서비스 요청 성공 했을 때의 응답화면 
	public void displaySuccess(String message) {
		System.out.println("\n서비스요청 성공 : " + message);
	}
	// 2. 서비스 요청 실패했을 때의 응답화면 
	public void displayFail(String message) {
		System.out.println("\n서비스요청 실패 : " + message);
	}
	// 3. 조회 요청시 조회결과가 없을 경우의 응답화면 
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	// 4. 조회 요청시 한행만 조회되었을 때 응답화면
	public void displayMember(Member m) {
		System.out.println("\n조회된 회원 정보는 다음과 같습니다.");
		System.out.println(m);// 알아서toString메소드 호출한다.
	}
	
	// 5. 조회 요청시 여러행 조회 되었을 때 응답화면 
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("\n조회된 회원 정보는 다음과 같습니다.");
		for(Member m : list) {
			System.out.println(m);
		}
	}
	
	
	// 5. 로그인 하는 화면
		public void loginMember() {
			System.out.println("\n==== 로그인 ====");
			System.out.print("아이디 : ");
			String userId = sc.nextLine();
			
			System.out.print("비밀번호 : ");
			String userPwd = sc.nextLine();
			
			// 전달할 값이 얼마 안되니깐 그냥 따로 보냄!
			mc.loginMember(userId, userPwd);
		}
	
	
	
}
