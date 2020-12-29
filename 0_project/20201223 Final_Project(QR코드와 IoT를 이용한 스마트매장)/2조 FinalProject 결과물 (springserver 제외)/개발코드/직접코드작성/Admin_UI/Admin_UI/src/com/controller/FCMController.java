package com.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.biz.InterestBiz;
import com.dao.InterestDao;
import com.frame.Biz;
import com.vo.FCM;
import com.vo.Interest;


@Controller
public class FCMController {
	
	@Resource(name = "interestbiz")
	Biz<String, Interest> biz;
	
	@Autowired
	InterestDao interestbiz;

	@RequestMapping("/fcmsend.mc")
	public String editmenu(HttpServletRequest request, FCM fcm)  {
		
		System.out.println();
		String title = fcm.getTitle();
		String title2 = fcm.getTitle2();
		String data = fcm.getData();
		String gender = fcm.getGender();
		String category = fcm.getCategory();
		
		String upgender = null;
		if(gender.equals("man")) {
			upgender = "남";
		}
		if(gender.equals("woman")) {
			upgender ="여";
		}
//		System.out.println(data+ "   ddd");
//		System.out.println(upgender+"gender");
		Interest interest = new Interest(upgender,data);
		try {
			interestbiz.update(interest);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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

		// set my firebase server key https://console.firebase.google.com/ 설정 클라우드 메시징에 키값이 있다.
		conn.setRequestProperty("Authorization", "key="
				+ "AAAA8lOJpRo:APA91bFIBy6c8XfFTge-5gA6MHRnFUSeTH54HpLE8yb3MySrXTdmeq7hvdM1iiwi1C1rmh8EbPsM6wFTaHxbz1a7RT2Xjs1c9uZL8FSUVKQfjGQylgTmBmv46sRlevImRCdAcm1O459l");

		// create notification message into JSON format /topics/  옆에는 Android 양식에 맞게 수정
		JSONObject message = new JSONObject();
		message.put("to", "/topics/"+gender);
		message.put("priority", "high");
		
		// 앱이 꺼져있는 상태에 알림가는 내용 (데이터안감)
		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", title2);
		message.put("notification", notification);
		
		// 앱이 켜져있는 상태에 데이터가 전송됨 (알림 안감)
		JSONObject fcmdata = new JSONObject();
		fcmdata.put("control", title);
		fcmdata.put("data", data);
		message.put("data", fcmdata);


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



		return "redirect:manage.mc";
	}; //edit menu end
		
	

} // Class end
