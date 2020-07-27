package com.kh.controller;
import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	public void insertMember(Member m) {
	
		int result = new MemberService().insertMember(m); // dao로 안보내고, service로 보냄
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원가입 되었습니다.");
		}else {
			new MemberMenu().displayFail("회원가입에 실패했습니다.");
		}
	}	
	
	public void selectList() {
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) { // 조회결과가 없을 경우
			new MemberMenu().displayNoData("조회된 데이터가 없습니다");
		}else {
			new MemberMenu().displayMemberList(list);
		}
	}
	
	public void selectByUserId(String userId) {
		
		Member m = new MemberService().selectByUserId(userId);
		
		if(m == null) { //조회가 안되었을 경우 (일치하는 회원을 찾지 못했을경우)
			new MemberMenu().displayNoData(userId + "에 해당되는 조회결과 없습니다.");
		}else {
			new MemberMenu().displayMember(m);
		}
	}
	
	public void selectByUserName(String keyword) {
		
		ArrayList<Member>list = new MemberService().selectByUserName(keyword);

		if(list.isEmpty()) { // 조회결과가 없을 경우
			new MemberMenu().displayNoData("조회된 데이터가 없습니다");
		}else {
			new MemberMenu().displayMemberList(list);
		}
		
	}
	
	public void updateMember(Member m) {
		
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 수정 되었습니다.");
		}else {
			new MemberMenu().displayFail("회원정보 수정에 실패했습니다.");
		}
	}
}
