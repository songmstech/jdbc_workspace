package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	
	public void insertMember(Member m) {
		
		int result = new MemberService().insertMember(m);
		
		if(result > 0) {
			
			new MemberMenu().displaySuccess("성공적으로 회원가입이 되었습니다.");
			
		}else {
			
			new MemberMenu().displayFail("회원가입을 할 수 없습니다.");
			
		}
		
	}
	
	public void selectList() {
		
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) {
			
			new MemberMenu().displayNoData("조회된 데이터가 없습니다.");
			
		}else {
			
			new MemberMenu().displayMemberList(list);
			
		}
	}

	public void selectByUserId(String userId) {
		
		Member m = new MemberService().selectByUser(userId);
		
		if(m == null) {
			
			new MemberMenu().displayNoData(userId + "에 해당되는 조회 결과가 없습니다.");
			
		}else {
			
			new MemberMenu().displayMember(m);
			
		}
		
	}

	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberService().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			
			new MemberMenu().displayNoData(keyword + "에 해당되는 조회 결과가 없습니다.");
			
		}else {
			
			new MemberMenu().displayMemberList(list);
			
		}
		
	}

	public void updateMember(Member m) {
		
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			
			new MemberMenu().displaySuccess("성공적으로 변경되었습니다.");
			
		}else {
			
			new MemberMenu().displayFail("회원정보 변경에 실패했습니다.");
			
		}
	}

	public void deleteMember(String userId) {
		
		int result = new MemberService().deleteMember(userId);
		
		if(result > 0) {
			
			new MemberMenu().displaySuccess("성공적으로 회원 탈퇴되었습니다.");
			
		}else {
			
			new MemberMenu().displayFail("회원 탈퇴에 실패하였습니다.");
			
		}
	}

}



















