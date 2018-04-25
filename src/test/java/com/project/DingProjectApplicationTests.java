package com.project;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DingProjectApplicationTests {

	@Test
	public void contextLoads() throws Exception{
		Map<String,Object> parameter1 = new HashMap<>();
		parameter1.put("choice","选择题");
		parameter1.put("judge",20);

		Map<String,Object> parameter2 = new HashMap<>();
		parameter2.put("choice","判断题");
		parameter2.put("judge",20);
		//获取试卷列表
		List<Map<String,Object>> paperList = new ArrayList<>();
		paperList.add(parameter1);
		paperList.add(parameter2);

		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("true");

		JSONArray a = new JSONArray(list.toString());
		System.out.println(a.toString());
	}

}
