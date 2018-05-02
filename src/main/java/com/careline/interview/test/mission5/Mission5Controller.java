package com.careline.interview.test.mission5;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.careline.interview.test.model.Member;
import com.careline.interview.test.repo.MemberRepo;

@Controller
public class Mission5Controller {
	@Autowired
	MemberRepo mMemberRepo;
	
	@RequestMapping("/mission5/login")
	@ResponseBody
	Map<String, Object> login(HttpServletRequest request,HttpServletResponse response) throws IOException, TimeoutException {
		// , @RequestBody HashMap<String,Object> pMap
		Map<String, Object> result = new HashMap<String, Object>();
		String pEmail = request.getParameter("email").trim();
		String pPassword = request.getParameter("password").trim();

		// check null
		if (StringUtils.isNullOrEmpty(pEmail) || StringUtils.isNullOrEmpty(pPassword)) {
			result.put("msg", "Invalid parameters");
			result.put("success",false);
			return result;
		}
		
		//check exists
		Member member = mMemberRepo.findByEmail(pEmail);
		if(member== null) {
			result.put("msg", "Invalid Email");
			result.put("success",false);
			return result;
		}
		
		// check password
		if(! StringUtils.equals(Base64.encodeBase64String(pPassword.getBytes()),member.getPassword())) {
			result.put("msg", "Invalid password");
			result.put("success",false);
			return result;
		}
		
		// login 
		String loginToken = mMemberRepo.login(pEmail);
		result.put("loginToken",loginToken);
		result.put("success",true);
//		response.addHeader("TOKEN", loginToken);
		return result;
	}
	
	
	@RequestMapping("/mission5/logout")
	@ResponseBody
	Map<String, Object> logout(HttpServletRequest request) throws IOException, TimeoutException {
		Map<String, Object> result = new HashMap<String, Object>();
		String pEmail = request.getParameter("email");
		String pToken = request.getParameter("token");

		// check null
		if (StringUtils.isNullOrEmpty(pEmail) || StringUtils.isNullOrEmpty(pToken)) {
			result.put("msg", "Invalid parameters");
			result.put("success",false);
			return result;
		}
		
		//check token
		Member member = mMemberRepo.findByToken(pToken);
		if(member== null) {
			result.put("msg", "Invalid token");
			result.put("success",false);
			return result;
		}
		
		// logout
		int loginToken = mMemberRepo.logout(pEmail);
		if(loginToken != 0) {
			result.put("success",true);			
		}else {
			result.put("success",false);		
			result.put("msg","Cannot delete token in DBs");		
		}
//		response.addHeader("TOKEN", loginToken);
		return result;
	}
}
