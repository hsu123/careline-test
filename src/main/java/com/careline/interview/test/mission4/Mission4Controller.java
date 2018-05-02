package com.careline.interview.test.mission4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.careline.interview.test.repo.MemberRepo;

@Controller
public class Mission4Controller {

	@Autowired
	MemberRepo mMemberRepo;
	
	@RequestMapping("/mission4/getAllMembers")
	@ResponseBody
	Map<String, Object> mission4(HttpServletRequest request) throws IOException, TimeoutException {
			Map<String, Object> result = new HashMap<String, Object>();
		result.put("memberList", mMemberRepo.findAll());
		return result;
	}
}
