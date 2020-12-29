package com.tcpip;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chat.Client;
import com.vo.Items;


@Controller 
public class MainController {
	
	Client client;
	String ss;
	String iotIP;
	String iotStatus;
	Items scanItem;
		
	public MainController() {
		// TCP-IP와 연결한다. TCP-IP서버 IP로 항상 변경해 주도록 하자.
		client = new Client("3.35.11.144",5555,"[ADMIN]");
		try {
			client.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	// TCP/IP socket �쓣 �넻�븳 Data �쟾�넚 ============================================
	@RequestMapping("/iotStart.mc")
	public void iotStart(HttpServletResponse res) throws IOException {
		System.out.println("IoT Send Start...");
		System.out.println(client.toString());
		client.sendTarget(iotIP, "s");
		PrintWriter out = res.getWriter();
		ss = "s";
		out.print("ok");
		out.close();
		
	}
	@RequestMapping("/iotStop.mc")
	public void iotStop(HttpServletResponse res) throws IOException {
		System.out.println("IoT Send Start...");
		client.sendTarget(iotIP, "t");
		PrintWriter out = res.getWriter();
		ss = "t";
		out.print("ok");
		out.close();
	}
	
	@RequestMapping("/toServer.mc")
	public void toServer(HttpServletRequest res){
		double lat;
		double lng;
		lat = Double.parseDouble(res.getParameter("lat"));
		lng = Double.parseDouble(res.getParameter("lng"));
		System.out.println(lat +" "+ lng);
		
	}
	
	@RequestMapping("/IoTsubmit.mc")
	public void IoTsubmit(HttpServletRequest res) throws InterruptedException{
		iotIP = res.getParameter("id");
		System.out.println(iotIP);
		
		//10초간 장비를 켠 후 끈다.
		client.sendTarget(iotIP, "s");
		Thread.sleep(10000);
		client.sendTarget(iotIP, "t");
		
	}
	
	//IoT로 부터 상태메세지를 출력한다. Client로 받은 것을 출력함.
	@RequestMapping("/iotToServer.mc")
	public void iotToServer(HttpServletRequest res){
		//메세지를 받아서 내용을 저장한다
		iotStatus = res.getParameter("ss");
		System.out.println(iotStatus);
		
	}

	//QR로 부터 Json을 받아 IoT장비를 작동 시킵니다.
	@RequestMapping("/getJson.mc")
	@ResponseBody
	public void getJson(@RequestBody Items item) throws InterruptedException{
		//데이터가 들어왔는지 확인
		System.out.println(item);
		//들어온 아이템 할당
		scanItem = item;
		//10초간 장비를 켠 후 끈다.
		client.sendTarget(iotIP, "s");
		Thread.sleep(10000);
		client.sendTarget(iotIP, "t");
		
	}
	//현재 아두이노상태(Ready)를 웹에 띄운다
	@RequestMapping("/statusToBrower.mc")
	public void statusToBrower(HttpServletResponse res) throws IOException{
		//web에서 현재 메세지를 요청하면 ss를 내보내준다.
		PrintWriter out = res.getWriter();
		out.print(iotStatus);
		out.close();
		
	}
	
	// 현재 아두이노에 입력된 커맨드를 웹에 띄운다
	@RequestMapping("/itemToBrower.mc")
	public void itemToBrower(HttpServletResponse res) throws IOException{
		//web에서 현재 메세지를 요청하면 ss를 내보내준다.
		if (scanItem!=null) {
			PrintWriter out = res.getWriter();
			out.print(scanItem.toString());
			out.close();
		}

		
	}
	
//	@RequestMapping("/phone.mc")
//	public void phone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("Phone Send Start...");
//		
//		
//	}
	
	//  Phone �쑝濡� FCM 蹂대궡湲� (Tomcat Server �궗�슜)===============================================
	@RequestMapping("/phone.mc")
	public void phone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Phone Send Start...");
		URL url = null;
		try {
			url = new URL("https://fcm.googleapis.com/fcm/send");
		} catch (MalformedURLException e) {
			System.out.println("Error while creating Firebase URL | MalformedURLException");
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			System.out.println("Error while createing connection with Firebase URL | IOException");
			e.printStackTrace();
		}
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");

		// set my firebase server key
		conn.setRequestProperty("Authorization", "key="
				+ "AAAAHBSezUM:APA91bFYQil3eNOyKtk780M880TtG2y5c1FgaP346fc49OAnDNuj1NnRRl9r_02Z64t8dMEASes40JxAOKaeBiQvOC7fDA1DxZg5qZj-jkoEoEFgodjMVUETsimil6jKpl2GU2S_dS8B");

		// create notification message into JSON format
		JSONObject message = new JSONObject();
		message.put("to", "/topics/car");
		message.put("priority", "high");
		
		//send to specific device
		
		JSONObject notification = new JSONObject();
		notification.put("title", "Hi,there");
		notification.put("body", "This is body text");
		message.put("notification", notification);
		
		JSONObject data = new JSONObject();
		data.put("control", "control1");
		data.put("data", 100);
		data.put("hi", "Hi, there");
		message.put("data", data);


		try {
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(message.toString());
			out.flush();
			conn.getInputStream();
			System.out.println("OK...............");

		} catch (IOException e) {
			System.out.println("Error while writing outputstream to firebase sending to ManageApp | IOException");
			e.printStackTrace();
		}	
		
	}
	

} // class end
