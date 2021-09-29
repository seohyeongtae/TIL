package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biz.ReceiptBiz;
import com.dao.ReceiptDao;
import com.frame.Biz;
import com.vo.Receipt;

@Controller
public class SalesController {
	
	@Autowired
	ReceiptDao rbiz;	

	@RequestMapping("/salesgraph.mc")
	public void salesgraph(HttpServletRequest request, HttpServletResponse response) {

		String month = request.getParameter("month");
		
		Integer[] data = {};
		
		try {
			data = rbiz.getdaytotal(month);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray();
		for(int m:data) {
			JSONObject obj = new JSONObject();
			obj.put("data",m);
			ja.add(obj);
		}
		response.setContentType("text/json;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(ja.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println();
	}
}
