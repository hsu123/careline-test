package com.careline.interview.test.mission6;

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
public class Mission6Controller {
	@Autowired
	MemberRepo mMemberRepo;
	
	@RequestMapping("/mission6/updateProfile")//updatePassword 
	@ResponseBody
	Map<String, Object> login(HttpServletRequest request,HttpServletResponse response) throws IOException, TimeoutException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		String pEmail = request.getParameter("email").trim();
		String pName = request.getParameter("name").trim();

		// check null
		if (StringUtils.isNullOrEmpty(pEmail) || StringUtils.isNullOrEmpty(pName)) {
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
		
		// check name
		if(StringUtils.equals(member.getName(),pName)) {
			result.put("msg", "Same name, you don't have to update");
			result.put("success",false);
			return result;
		}
		
		// login 
		int updateResult = mMemberRepo.updateMemberName(pEmail, pName);
		if(updateResult == 1) {
			result.put("success",true);			
		}else {
			result.put("success",false);
			result.put("msg","cannot update");

		}
		return result;
	}
	
	

	@RequestMapping("/mission6/updatePassword")
	@ResponseBody
	Map<String, Object> updatePassword(HttpServletRequest request,HttpServletResponse response) throws IOException, TimeoutException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		String pEmail = request.getParameter("email").trim();
		String pPassword = request.getParameter("oldPassword").trim();
		String pNewPassword = request.getParameter("newPassword").trim();
		String pNewPasswordConfirm = request.getParameter("newPasswordConfirm").trim();
		

		// check null
		if (StringUtils.isNullOrEmpty(pEmail) || StringUtils.isNullOrEmpty(pPassword) || StringUtils.isNullOrEmpty(pNewPassword) || StringUtils.isNullOrEmpty(pNewPasswordConfirm)) {
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
		
		// check password
				if(! StringUtils.equals(Base64.encodeBase64String(pPassword.getBytes()),member.getPassword())) {
					result.put("msg", "Invalid password");
					result.put("success",false);
					return result;
				}
				
		
		// check confirm
		if(StringUtils.equals(pNewPassword,pNewPasswordConfirm)) {
			result.put("msg", "new password and confirm are not equal");
			result.put("success",false);
			return result;
		}
		
		// login 
		int updateResult = mMemberRepo.updateMemberPassword(pEmail, Base64.encodeBase64String(pNewPassword.getBytes()));
		if(updateResult == 1) {
			result.put("success",true);			
		}else {
			result.put("success",false);
			result.put("msg","cannot update");

		}
		return result;
	}
	
	
}
