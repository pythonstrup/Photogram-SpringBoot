package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {

	@GetMapping("/user/{id}") 
	public String profile(@PathVariable int id) {
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 1. 추천 - 쉬움: 어노테이션을 통해 세션을 찾는 방식
		System.out.println("세션 정보: " + principalDetails.getUser());
		
		// 2. 비추천 - 복잡함: 직접 찾는 방식
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//		System.out.println("직접 찾은 세션 정보: " + mPrincipalDetails.getUser());
		
		return "user/update";
	} 
}
