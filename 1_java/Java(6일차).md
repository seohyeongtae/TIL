## Web Server를 이용한 통신 (TCP/IP , Tomcat) + Linux 연동 Web Browser -> 메시지, FCM 보내기

![KakaoTalk_20201102_174931188](Java(6%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201102_174931188.jpg)

### Spring 구현 (기존 Server, Client 코드는 동일 새롭게 Spring 생성으로 Web Browser 와 연동)

1. Dynamic Web Project 생성
2. maven - pom.xml  설정/수정
3. Add Spring Nature 설정
4. web -> web-inf -> web.xml  수정
5. web -> web-inf -> config -> spring.xml 생성
6. src - myspring.xml 

이클립스 Spring(tcpip) / network(server) / network(client)  && Android Studio 운용 

Ajax 이용 -> main.jsp 화면이동이 없고 신호만 보내기 위해 (Jstl / jquery 추가)

Client -> SendTarget 함수 사용

> Spring -> txpip

> main.jsp  -> JQuery , Jstl , Ajax 사용

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


<script>
$(document).ready(function(){
	$('#iot').click(function(){
		$.ajax({
			url:'iot.mc',
			success:function(data){
				alert('Send Complete...')
			}
		});
	});
	$('#phone').click(function(){
		$.ajax({
			url:'phone.mc',
			success:function(data){
				alert('Send Complete...')
			}
		});
	});
});
</script>

</head>
<body>

<h1>Main Page</h1>
<h2><a id="iot" href="#">Send IoT(TCP/IP)</a></h2>
<h2><a id="phone" href="#">Send Phone(FCM)</a></h2>

</body>
</html>
```



> Client.java

```java
package com.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.msg.Msg;

public class Client {

	int port;
	String address;
	String id;
	Socket socket;
	
	Sender sender;
	
	public Client() {
		
	}
	public Client(String adress, int port, String id) {
		this.address = adress;
		this.port = port;
		this.id = id;
	}
	
	public void connect() throws IOException {
		try {
			socket = new Socket(address, port);
		} catch (Exception e) {
			
			while(true) {
				try {
					Thread.sleep(200);
					socket = new Socket(address, port);
					break;
				} catch(Exception e2) {
					System.out.println("Retry...");
				}
			} // while end
		} 
		
		System.out.println("Connected Server : "+address);
		sender = new Sender(socket);
		// Server에서 보낸 데이터 받기 위해 receiver 실행 
		// new Receiver(socket).start();
	} // connect end
	
	// web App에서 데이터를 받고 cmd 를 보낸다 ============================================= 
	public void sendTarget(String ip, String cmd) {
		ArrayList<String> ips = new ArrayList<String>();
		ips.add(ip);
		Msg msg = new Msg(id,cmd);
		sender.setMsg(msg);
		new Thread(sender).start();
	}
	

	// Scanner로 Input 값 받기
	public void sendMsg() {
		Scanner sc = new Scanner(System.in);
		if (socket != null) {
		while(true) {
			System.out.println("Input Msg");
			String ms = sc.nextLine();
			// 1을 보내면 서버에서는 Client 리스트를 보낸다. ------------------------------------특정 규칙을 정해야 한다.
			Msg msg = null;
			if(ms.equals("1")) {
				msg = new Msg(id,ms);
			}else {
				// 특정 인원들에게 귓속말을 보낼때 Msg.java 수정했음--------------------------------------------
				// ArrayList로 귓속말 보낼 IP 주소를 넣어 Server에 보내면 해당 IP 들에게 Server에서 메시지 전송
				ArrayList<String> ips = new ArrayList<>();
				ips.add("/192.168.0.24");
				ips.add("/192.168.0.15");
				ips.add("/192.168.0.92");
				ips.add("/192.168.0.69");
//				msg = new Msg(ips,id,ms);
				msg = new Msg(null,id,ms);
			}
			// 귓속말 하기 위한 Ip 정보 추가 전송 Server에서 Ip를 읽으면 앞에 / 가 자동으로 붙기 때문에 그 형식을 맞추기 위해 / 추가
	//		Msg msg = new Msg("/192.168.0.24",id,ms);
			
			sender.setMsg(msg);
			new Thread(sender).start();
			
			if(ms.equals("q")) {
				break;
			}
		  }
		}
		sc.close();
		// 반드시 종료 ----------------------
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bye...");
	} // sendMsg end
	
	// Runnable 이용해야한다.
	class Sender implements Runnable{
		Socket socket;
		ObjectOutputStream oo;
		Msg msg;
		
		public Sender(Socket socket) throws IOException {
			this.socket = socket;
			oo = new ObjectOutputStream(socket.getOutputStream());
		}
		
		public void setMsg(Msg msg) {
			this.msg = msg;
		}
		@Override
		public void run() {
			if(oo != null) {
				try {
					oo.writeObject(msg);
				} catch (IOException e) {
					// 이때 Exception 은 Server 가 죽어있을 확률이 크다.
					//	e.printStackTrace();
					try {						
						if(socket != null) {
							socket.close();
						}

					}catch(Exception e1) {
						e1.printStackTrace();
					}
					// Server와 재 연결 시도 ---------------------------
					try {
						Thread.sleep(2000);
						connect();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		} // run end
	} // class Sender end
	
	// Server에서 보낸 메시지 InputStream으로 받기
	class Receiver extends Thread{
		ObjectInputStream oi;
		public Receiver(Socket socket) throws IOException {
			oi = new ObjectInputStream(socket.getInputStream());
		}
		@Override
		public void run() {
			while(oi != null) {
				Msg msg = null;
				try {
					msg = (Msg) oi.readObject();
					// 1을 입력 했을 때 Client list 받기 ----------------------
					if(msg.getMaps() != null) {
						HashMap<String,Msg> hm = msg.getMaps();
						Set<String> keys =  hm.keySet();
						for(String k : keys) {
							System.out.println(k);
						}
						continue; // 계속 받아야 하기 때문에 아래를 건너 뛰고 다시 실행
					}
					System.out.println(msg.getId()+" : "+msg.getMsg());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			} // while end
			try {
				if(oi != null) {
					oi.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch(Exception e) {
				
			}
		} // run end
	} // class Receiver end
	
	public static void main(String[] args) {
		Client client = new Client("192.168.1.107",5555,"[Seo]");
		try {
			client.connect();
			client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
```



> MainController.java

```java
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chat.Client;


@Controller 
public class MainController {
	
	Client client;
		
	public MainController() {
		client = new Client("192.168.1.107",5555,"[WEB]");
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
	// TCP/IP socket 을 통한 Data 전송 ============================================
	@RequestMapping("/iot.mc")
	public void iot(HttpServletResponse res) throws IOException {
		System.out.println("IoT Send Start...");
		client.sendTarget("/192.168.1.107", "100");
		PrintWriter out = res.getWriter();
		out.print("ok");
		out.close();
		
	}
	
//	@RequestMapping("/phone.mc")
//	public void phone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("Phone Send Start...");
//		
//		
//	}
	
	//  Phone 으로 FCM 보내기 (Tomcat Server 사용)===============================================
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

		// set my firebase server key https://console.firebase.google.com/ 설정 클라우드 메시징에 키값이 있다.
		conn.setRequestProperty("Authorization", "key="
				+ "AAAA8lOJpRo:APA91bFIBy6c8XfFTge-5gA6MHRnFUSeTH54HpLE8yb3MySrXTdmeq7hvdM1iiwi1C1rmh8EbPsM6wFTaHxbz1a7RT2Xjs1c9uZL8FSUVKQfjGQylgTmBmv46sRlevImRCdAcm1O459l");

		// create notification message into JSON format /topics/  옆에는 Android 양식에 맞게 수정
		JSONObject message = new JSONObject();
		message.put("to", "/topics/movie");
		message.put("priority", "high");
		
		// 앱이 꺼져있는 상태에 알림가는 내용 (데이터안감)
		JSONObject notification = new JSONObject();
		notification.put("title", "되냐");
		notification.put("body", "됩니까");
		message.put("notification", notification);
		
		// 앱이 켜져있는 상태에 데이터가 전송됨 (알림 안감)
		JSONObject data = new JSONObject();
		data.put("control", "ㅇㅇㅇ");
		data.put("data", "ㅇㅇㅇㅇ");
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

```

![KakaoTalk_20201102_175304278](Java(6%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201102_175304278.jpg)



###  VMWare 리눅스 고유 IP 부여 vmnetcfg - network 변경

### Linux java eclipse 코드는 기존 Client 코드와 동일하다 (Msg도)

> 가상의 IP로는 TCP/IP 로 Server 에 들어가면 가상의 IP로 들어가지는게 아니라 컴퓨터 자체의
>
> IP 주소로 들어가지기 때문에

Linux Bridged 설정 (가상의 IP 가 아니라 고유의 IP를 가지게 하기 위해) - 카페 게시판 참고

Bridged 밑에 설정은 컴퓨터 Network 연결 장치에 따라 변경

![브릿지_1](Java(6%EC%9D%BC%EC%B0%A8)/%EB%B8%8C%EB%A6%BF%EC%A7%80_1.jpg)

![브릿지_1](Java(6%EC%9D%BC%EC%B0%A8)/%EB%B8%8C%EB%A6%BF%EC%A7%80_1-1604306758452.jpg)

![브릿지_1](Java(6%EC%9D%BC%EC%B0%A8)/%EB%B8%8C%EB%A6%BF%EC%A7%80_1-1604306766714.jpg)

![브릿지_1](Java(6%EC%9D%BC%EC%B0%A8)/%EB%B8%8C%EB%A6%BF%EC%A7%80_1-1604306772223.jpg)

![브릿지_5](Java(6%EC%9D%BC%EC%B0%A8)/%EB%B8%8C%EB%A6%BF%EC%A7%80_5.jpg)





BOOTPROTO="dhcp"

#IPADDR="192.168.111.101"

#NETMASK="255.255.255.0"

#GATEWAY="192.168.111.2"

DNS1="192.168.111.2"