package com.careline.interview.test.mission1;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Mission1Controller {

	@RequestMapping("/mission1/hello")
	@ResponseBody
	String mission1() throws IOException, TimeoutException {
		Date date = new Date();
		return date.toString();
	}
}
