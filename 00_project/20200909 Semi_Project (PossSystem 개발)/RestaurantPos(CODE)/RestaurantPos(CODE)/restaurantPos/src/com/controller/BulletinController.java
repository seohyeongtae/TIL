package com.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.RequestHeaderMapMethodArgumentResolver;
import org.springframework.web.servlet.ModelAndView;

import com.common.Pagination;
import com.frame.Biz;
import com.frame.Search;
import com.vo.Bulletin;

@Controller
public class BulletinController {

	@Resource(name="bulletinbiz")
	Biz<Integer, Bulletin> biz;
	@Resource(name="bulletinbiz")
	Search<Integer, Bulletin> search;
	
	@RequestMapping("/tablebull.mc")
	public String tablebull(ModelAndView mv) {
		
		return "redirect:gettablebullList.mc?page=1&range=1";
	}
	@RequestMapping(value="/gettablebullList.mc", method=RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request,
							@RequestParam(required=false, defaultValue="1") int page,
							@RequestParam(required=false, defaultValue="1") int range){
		ModelAndView mv = new ModelAndView();
		ArrayList<Bulletin> list = null;
		int listCnt;
		Pagination pagination = new Pagination();
		HttpSession session = request.getSession();
		try {
			listCnt = search.getcnt();
			pagination.pageInfo(page, range, listCnt);
			session.setAttribute("pagination", pagination);
			list = search.selectpage(pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("bulllist", list);
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/bull");

		return mv;
	}
	@RequestMapping("/adminbull.mc")
	public String adminbull(ModelAndView mv) {
		
		return "redirect:getadminbullList.mc?page=1&range=1";
	}
	@RequestMapping(value="/getadminbullList.mc", method=RequestMethod.GET)
	public ModelAndView getadminbullList(HttpServletRequest request,
							@RequestParam(required=false, defaultValue="1") int page,
							@RequestParam(required=false, defaultValue="1") int range){
		ModelAndView mv = new ModelAndView();
		ArrayList<Bulletin> list = null;
		int listCnt;
		Pagination pagination = new Pagination();
		HttpSession session = request.getSession();
		try {
			listCnt = search.getcnt();
			pagination.pageInfo(page, range, listCnt);
			session.setAttribute("pagination", pagination);
			list = search.selectpage(pagination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("bulllist", list);
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "admin/bull");

		return mv;
	}
	@RequestMapping("/deletebull.mc")
	public String deletebull(int id, 
							@RequestParam(required=false, defaultValue="1") int page,
							@RequestParam(required=false, defaultValue="1") int range) {
		try {
			biz.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:getadminbullList.mc?page="+page+"&range="+range;
	}
	
	@RequestMapping("/viewbull.mc")
	public ModelAndView viewbull(ModelAndView mv, Integer id) {
		Bulletin b = null;
		try {
			b = biz.get(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("data", b);
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/viewbull");

		return mv;
	}
	@RequestMapping("/addbull.mc")
	public ModelAndView addbull() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");//자동으로 .jsp를 붙여서 실행
		mv.addObject("centerpage", "table/addbull");

		return mv;
	}
	@RequestMapping("/savebull.mc")
	public String savebull(Bulletin b) {
		b.setTab_id("NULL");
		b.setComment_id(0);
		try {
			biz.register(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:viewbull.mc?id="+b.getId();
	}
}
