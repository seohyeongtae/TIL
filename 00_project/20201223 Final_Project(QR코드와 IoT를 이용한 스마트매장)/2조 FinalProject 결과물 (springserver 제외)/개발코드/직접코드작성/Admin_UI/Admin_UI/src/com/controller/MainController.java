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

import chat.Client;

@Controller
public class MainController {
	
	
	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		return mv;
	}

	@RequestMapping("/dashboard.mc")
	public ModelAndView adminhome() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dashboard/dashboard"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	
	@RequestMapping("/iot.mc")
	public ModelAndView adminiot() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/iot/iot"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	@RequestMapping("/manage.mc")
	public ModelAndView adminmanage() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/manage/manage"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	@RequestMapping("/product.mc")
	public ModelAndView adminproduct() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/product/product"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	
	@RequestMapping("/refund.mc")
	public ModelAndView adminrefund() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/refund/refund"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	
	
	@RequestMapping("/log.mc")
	public ModelAndView adminlog() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/log/log"); //자동으로 .jsp를 붙여서 실행

		return mv;
	}
	
	
	
	// 제품별 판매현황
	@RequestMapping("/dashboardchart1.mc")
	public ModelAndView dashboardchart1() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dashboard/dashboard"); //자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "/view/dashboard/orderlistchart");

		return mv;
	}
	// 일별 판매현황
	@RequestMapping("/dashboardchart2.mc")
	public ModelAndView dashboardchart2() {
				
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dashboard/dashboard"); //자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "/view/dashboard/orderlistchart2");

		return mv;
	}
	

}
