package com.careline.interview.test.mission3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.careline.interview.test.model.Member;
import com.careline.interview.test.repo.MemberRepo;

@Controller
public class Mission3Controller {
	@Autowired
	MemberRepo mMemberRepo;

	@RequestMapping("/mission3/register")
	@ResponseBody
	Map<String, Object> mission3(HttpServletRequest request) throws IOException, TimeoutException {
		// , @RequestBody HashMap<String,Object> pMap
		Map<String, Object> result = new HashMap<String, Object>();
		String pEmail = request.getParameter("email").trim();
		String pName = request.getParameter("name").trim();
		String pPassword = request.getParameter("password").trim();

		// check null
		if (StringUtils.isNullOrEmpty(pEmail) || StringUtils.isNullOrEmpty(pName) || StringUtils.isNullOrEmpty(pPassword)) {
			result.put("msg", "Invalid parameters");
			return result;
		}
		// check email
		if (mMemberRepo.findByEmail(pEmail) != null) {
			result.put("msg", "email already exists!");
			return result;
		}

		Member newMember = new Member();
		newMember.setEmail(pEmail);
		newMember.setName(pName);
		newMember.setPassword(Base64.encodeBase64String(pPassword.getBytes()));

		try {
			int retrunId = mMemberRepo.insert(newMember);

			if (retrunId != 0) {
				result.put("memberId", retrunId);
				return result;
			}

		}catch(Exception ex ) {
			
			result.put("msg", ex.getMessage());
			ex.printStackTrace();
			return result;
		}
		return result;
	}
}
