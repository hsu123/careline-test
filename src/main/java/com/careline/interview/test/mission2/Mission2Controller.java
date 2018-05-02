package com.careline.interview.test.mission2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Mission2Controller {

	@RequestMapping("/mission2/compute")
	@ResponseBody
	Map<String, Object> mission2(HttpServletRequest request, @RequestBody HashMap<String,Object> pMap) throws IOException, TimeoutException {
		Map<String,Object> result = new HashMap<String,Object>();
	
		List<Integer> pList = (List<Integer>) pMap.get("numList");
		
		result.put("sum",pList.stream().mapToInt(Integer::intValue).sum());

		BigDecimal avg = new BigDecimal(pList.stream().mapToInt(Integer::intValue).average().getAsDouble());
		BigDecimal roundOffAvg = avg.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		result.put("avg",roundOffAvg);
		result.put("max",pList.stream().mapToInt(Integer::intValue).max());
		result.put("min",pList.stream().mapToInt(Integer::intValue).min());
		result.put("sorted_list",pList.stream().mapToInt(Integer::intValue).sorted());
		return result;
	}
}
// {
// "sum" : 0.00, // 數字總和
// "max" : 0.00, // 最大數字
// "min" : 0.00, // 最小數字
// "avg" : 0.00, // 數字平均
// "sorted_list" : [0, 0, ...] // 由小到大排序的數字陣列
// }
