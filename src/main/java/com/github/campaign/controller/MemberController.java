package com.github.campaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.campaign.model.MemberRequest;
import com.github.campaign.model.MemberResponse;
import com.github.campaign.service.MemberService;

@Controller
@Transactional
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	//cadastro
	@RequestMapping( method = RequestMethod.POST, produces={"application/json; charset=UTF-8"})
	public @ResponseBody MemberResponse include(@RequestBody MemberRequest memberRequest) {
		MemberResponse response = new MemberResponse();
		response = this.memberService.includeMember(memberRequest);
				
		return response;
	}
	
}
