package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dao.OrderlistDao;
import com.dao.OrdersDao;
import com.frame.Biz;
import com.vo.Orderlist;
import com.vo.Orders;

@Controller
public class DashBoradController {
	
	@Resource(name = "orderlistbiz")
	Biz<String, Orderlist> biz;
	
	@Resource(name = "ordersbiz")
	Biz<String, Orders> biz2;
	
	@Autowired
	OrderlistDao orderlistbiz;
	
	@Autowired
	OrdersDao ordersbiz;


	// 제품별 판매현황
	@RequestMapping("/getorderlist.mc")
	public void getorderlist(HttpServletRequest request, HttpServletResponse response) {
		String id ="20"+request.getParameter("id")+"%";
		ArrayList<Orderlist> orderlist = new ArrayList();
		try {
			orderlist = orderlistbiz.getorderlist(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray();
		
		for(int i =0; i < orderlist.size(); i++) {
			JSONArray ja2 = new JSONArray();
			ja2.add(orderlist.get(i).getItemname());
			ja2.add(orderlist.get(i).getQuantity());
			ja.add(ja2);	
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
	};
	
	// 일별 판매현황
	@RequestMapping("/getorderlist2.mc")
	public void getorderlist2(HttpServletRequest request, HttpServletResponse response) {
		String id ="20"+request.getParameter("id")+"%";		
		ArrayList<Orders> orders = new ArrayList();
		try {
			orders = ordersbiz.gettotallist(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray();
		
		for(int i =0; i < orders.size(); i++) {
		
			JSONArray ja2 = new JSONArray();
			ja2.add(orders.get(i).getOrderdate().toString());
			ja2.add(orders.get(i).getTotalprice());	
			ja.add(ja2);	
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
	};
	
	
	

}
