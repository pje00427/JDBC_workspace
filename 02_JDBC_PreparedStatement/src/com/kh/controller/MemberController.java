package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	public void insertMember(Member m) {

		int result = new MemberDao().insertMember(m);

		if (result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원가입되었습니다.");
		} else {
			new MemberMenu().displayFail("회원가입에 실패했습니다.");
		}
	}

	public void selectList() {

		ArrayList<Member> list = new MemberDao().selectList();

		if (list.isEmpty()) {
			new MemberMenu().displayNoData("전체 회원에 대한 조회결과 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}

	}

	public void selectByUserId(String userId) {

		Member m = new MemberDao().selectByUserId(userId);

		if (m != null) { // 조회가되었을 경우
			new MemberMenu().displayMember(m);
		} else { // 조회가 안됐다
			new MemberMenu().displayNoData("조회된 데이터가 없습니다.");
		}
	}
	
	/**
	 * 사용자의 회원명(키워드)으로 검색요청시 처리하는 메소드
	 * @param keyword
	 */
	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		
		if(list.isEmpty()) { // 리스트가 비어있을 경우 (조회결과 없을 경우)
			new MemberMenu().displayNoData(keyword + "에 해당하는 검색결과가 없습니다.");
			
		}else {// 그게 아닐 경우 (조회결과 있을 경우)
			new MemberMenu().displayMemberList(list);
			
		}
		
	}
	
	/**
	 * 사용자의 정보 변경 요청을 처리해주는 메소드
	 * @param m --> 변경하고자하는 회원아이디, 변경할암호, 변경할 이메일, 변경할 전화번호, 변경할 주소
	 */
	public void updateMember(Member m) {
		
		int result = new MemberDao().updateMember(m);
		
		if(result > 0) { // 성공 --> displaySuccess
			new MemberMenu().displaySuccess("회원 정보 변경 성공!");
		}else { // 실패 --> displayFail
			new MemberMenu().displayFail("회원 정보 변경 실패!");
		}
		
	}
	
	
	/**
	 * 사용자의 회원 탈퇴 요청을 처리해주는 메소드
	 * @param userId	--> 탈퇴요청한 해당 회원의 아이디 담김
	 */
	public void deleteMember(String userId) {
		
		int result = new MemberDao().deleteMember(userId);
		
		if(result > 0) { // 성공 
			new MemberMenu().displaySuccess("회원 탈퇴 성공!");
		}else { // 실패
			new MemberMenu().displayFail("회원 탈퇴 실패!");
		}
		
		
	}
	
	
}
