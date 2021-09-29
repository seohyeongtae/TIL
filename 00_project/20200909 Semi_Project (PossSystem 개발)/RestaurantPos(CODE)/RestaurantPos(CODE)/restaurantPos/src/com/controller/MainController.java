package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		return mv;
	}

	@RequestMapping("/adminlogin.mc")
	public ModelAndView adminlogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main"); //자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "admin/login");

		return mv;
	}
	@RequestMapping("/adminhome.mc")
	public ModelAndView adminhome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "admin/home");

		return mv;
	}
	@RequestMapping("/tablehome.mc")
	public ModelAndView tablehome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/home");
		
		return mv;
	}
	@RequestMapping("/tableorder.mc")
	public ModelAndView tableorder() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/order");

		return mv;
	}
	@RequestMapping("/tablereceipt.mc")
	public String tablereceipt() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
//		mv.addObject("centerpage", "table/receipt");

		return "redirect:receiptlists.mc";
	}

}
