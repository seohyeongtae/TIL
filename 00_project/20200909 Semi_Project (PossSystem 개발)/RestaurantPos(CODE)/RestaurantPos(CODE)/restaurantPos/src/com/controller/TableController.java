package com.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Biz;
import com.vo.Tab;

@Controller
public class TableController {

	@Resource(name="tabbiz")
	Biz<String, Tab> tabbiz;
	
	@RequestMapping("/tablelogin.mc")
	public ModelAndView tablelogin() {
		ModelAndView mv = new ModelAndView();
		
		ArrayList<Tab> list = null;
		
		try {
			list = tabbiz.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mv.addObject("tablelist", list);
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/login");

		return mv;
	}
	@RequestMapping("/tableloginimpl.mc")
	public ModelAndView tableloginimpl(HttpServletRequest request, String id) {
		
		Tab table = null;
		
		try {
			table = tabbiz.get(id);
			HttpSession session = request.getSession();
			session.setAttribute("logintable", table);
			session.setAttribute("tableId", table.getId());  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/home");

		return mv;
	}
	@RequestMapping("/tablelogout.mc")
	public String tablelogout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		if(session!=null) {
			session.invalidate();
		}

		return "redirect:tablelogin.mc";
	}
	@RequestMapping("/tabledata.mc")
	public ModelAndView table() {
		
		ArrayList<Tab> list = null;
		
		try {
			list = tabbiz.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("tabledatas", list);
		mv.addObject("centerpage", "admin/table");

		return mv;
	}
	@RequestMapping("edittable.mc")
	public String edittable(Tab tab) {
		System.out.println(tab);
		if(tab.getAdmin_id()==null||tab.getAdmin_id().equals("")) {
			return "redirect:adminlogin.mc";
		}
		try {
			tabbiz.modify(tab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:tabledata.mc";
	}
	@RequestMapping("addtable.mc")
	public String addtable(Tab tab) {
		System.out.println(tab);
		if(tab.getAdmin_id()==null||tab.getAdmin_id().equals("")) {
			return "redirect:adminlogin.mc";
		}
		try {
			tabbiz.register(tab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:tabledata.mc";
	}
	@RequestMapping("deletetable.mc")
	public String deletetable(String id) {
		
		try {
			tabbiz.remove(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:tabledata.mc";
	}
}
