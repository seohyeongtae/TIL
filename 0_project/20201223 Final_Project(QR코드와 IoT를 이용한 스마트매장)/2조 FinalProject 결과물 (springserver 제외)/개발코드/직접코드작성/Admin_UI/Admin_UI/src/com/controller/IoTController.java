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


import com.dao.IotDao;

import com.frame.Biz;
import com.vo.Iot;


import chat.Client;

@Controller
public class IoTController {
	Client client;
	String ss;
	String iotIP;
	String iotStatus;
	
	@Resource(name = "iotbiz")
	Biz<String, Iot> biz;
	
	@Autowired
	IotDao iotbiz;
	
	
	@RequestMapping("/getiot.mc")
	public void getiot(HttpServletRequest request, HttpServletResponse res) throws IOException {
		ArrayList<Iot> iot = new ArrayList(); 
		
		try {
			iot = iotbiz.selectall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("door",iot.get(0).getDoor());
		jo.put("light",iot.get(0).getLight());
		jo.put("temperature",iot.get(0).getTemperature());
		ja.add(jo);
		
//		System.out.println(iot.get(0).getDoor()+" door");
//		System.out.println(iot.get(0).getLight()+" getLight");
		res.setContentType("text/json;charset=utf-8");
		PrintWriter out;
		try {
			out = res.getWriter();
			out.print(ja.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	
	@RequestMapping("/iotsys.mc")
	public void iotsys(HttpServletRequest request, HttpServletResponse res) throws IOException {
		String id = request.getParameter("id").toString();
				
		if(id.equals("dooron")) {
			System.out.println("IoT Send door...");
			SocketListener.sendTarget("123","1:1900");
			PrintWriter out = res.getWriter();
			ss = "s";
			out.print("ok");
			out.close();
			try {
				iotbiz.updatedoor(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		};
		if(id.equals("dooroff")) {
			System.out.println("IoT Send door...");
			SocketListener.sendTarget("123","1:1150");
			PrintWriter out = res.getWriter();
			ss = "s";
			out.print("ok");
			out.close();
			try {
				iotbiz.updatedoor(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		};
		if(id.equals("lighton")) {
			SocketListener.sendTarget("123","3:1");
			System.out.println("IoT Send light...");
			PrintWriter out = res.getWriter();
			ss = "l";
			out.print("ok");
			out.close();
			try {
				iotbiz.updatelight(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		};
		if(id.equals("lightoff")) {
			SocketListener.sendTarget("123","3:0");
			System.out.println("IoT Send light...");
			PrintWriter out = res.getWriter();
			ss = "l";
			out.print("ok");
			out.close();
			try {
				iotbiz.updatelight(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		};
		
		int temp = Integer.parseInt(id);
		if(temp > 0 && temp <40) {
			String tempiot = "2:"+temp;
			SocketListener.sendTarget("123",tempiot);
			PrintWriter out = res.getWriter();
			ss = "l";
			out.print("ok");
			out.close();
			try {
				iotbiz.updatetemp(temp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		};
	
	}; //iotsys end
	
	@RequestMapping("/iotnavi.mc")
	public void iotnavi(HttpServletRequest request, HttpServletResponse res) throws IOException {
		String id = request.getParameter("category").toString();
		
		if(id.equals("cate1")) {
			System.out.println("IoT Send door...");
			SocketListener.sendTarget("123","4:0");
			PrintWriter out = res.getWriter();
			ss = "s";
			out.print("ok");
			out.close();
	
			return;
		};
		if(id.equals("cate2")) {
			System.out.println("IoT Send door...");
			SocketListener.sendTarget("123","4:1");
			PrintWriter out = res.getWriter();
			ss = "s";
			out.print("ok");
			out.close();
		
			return;
		};
		if(id.equals("cate3")) {
			SocketListener.sendTarget("123","4:0");
			System.out.println("IoT Send light...");
			PrintWriter out = res.getWriter();
			ss = "l";
			out.print("ok");
			out.close();
		
			return;
		};
		if(id.equals("cate4")) {
			SocketListener.sendTarget("123","4:1");
			System.out.println("IoT Send light...");
			PrintWriter out = res.getWriter();
			ss = "l";
			out.print("ok");
			out.close();
		
			return;
		};
	
	}; //iotnavi end
	
	@RequestMapping("/iotproduct.mc")
	public void iotproduct(HttpServletRequest request, HttpServletResponse res) throws IOException {
		String id = request.getParameter("id").toString();
			
			System.out.println("iotproduct...");
			SocketListener.sendTarget("123",id);
			PrintWriter out = res.getWriter();
			ss = "s";
			out.print("ok");
			out.close();
	
	}; //iotproduct end

	
} // Class end
