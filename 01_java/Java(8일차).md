### Can, Http 통신

> SendAndReceiveSerial.java

```java
package com.can;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.chat.Client;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SendAndReceiveSerial implements SerialPortEventListener {
	private BufferedInputStream bin;
	private InputStream in;
	private OutputStream out;
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	private String result;
	private String rawCanID, rawTotal;
	// private boolean start = false;
	
	
	public SendAndReceiveSerial() {
		
	}

	public SendAndReceiveSerial(String portName, boolean mode) {
		
		try {
			if (mode == true) {
				portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				System.out.printf("Port Connect : %s\n", portName);
				connectSerial();
				// Serial Initialization ....
				(new Thread(new SerialWriter())).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void connectSerial() throws Exception {

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			commPort = portIdentifier.open(this.getClass().getName(), 5000);
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
				serialPort.setSerialPortParams(921600, // ��żӵ�
						SerialPort.DATABITS_8, // ������ ��Ʈ
						SerialPort.STOPBITS_1, // stop ��Ʈ
						SerialPort.PARITY_NONE); // �и�Ƽ
				in = serialPort.getInputStream();
				bin = new BufferedInputStream(in);
				out = serialPort.getOutputStream();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public void sendSerial(String rawTotal, String rawCanID) {
		this.rawTotal = rawTotal;
		this.rawCanID = rawCanID;
		// System.out.println("send: " + rawTotal);
		try {
			// Thread.sleep(50);
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread sendTread = 
				new Thread(new SerialWriter(rawTotal));
		sendTread.start();
	}

	private class SerialWriter implements Runnable {
		String data;

		public SerialWriter() {
			// 최초에 한번 쏴주어야 한다. Can 프로토콜에 참여하겠다는 뜻. Can 메시지 시작은 :  끝나는 부분은 \r  로  정해져있다.
			this.data = ":G11A9\r";
		}

		public SerialWriter(String serialData) {
			// CheckSum Data 생성
			this.data = sendDataFormat(serialData);
			// check sum
			// : serialData checksum \r
		}

		public String sendDataFormat(String serialData) {
			serialData = serialData.toUpperCase();
			char c[] = serialData.toCharArray();
			int cdata = 0;
			for (char cc : c) {
				cdata += cc;
			}
			// check sum = 간단한 암호화
			cdata = (cdata & 0xFF);

			String returnData = ":";
			returnData += serialData + Integer.toHexString(cdata).toUpperCase();
			returnData += "\r";
			return returnData;
		}

		public void run() {
			try {

				byte[] inputData = data.getBytes();

				out.write(inputData);
				send(data+"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	

	
	// Asynchronized Receive Data
	// --------------------------------------------------------

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[128];

			try {

				while (bin.available() > 0) {
					int numBytes = bin.read(readBuffer);
				}

				String ss = new String(readBuffer);
				System.out.println("Receive Low Data:" + ss + "||");
				send(ss);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	

	

	public void close() throws IOException {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (commPort != null) {
			commPort.close();
		}

	}
	
	public static void send(String candata) {

		HttpSender sender = null;

			String urlstr = "http://192.168.0.24/tcpip/can.mc";
			URL url = null;
			try {
				String data = candata;
				url = new URL(urlstr+"?candata="+data);
				sender = new HttpSender(candata, url);
				new Thread(sender).start();
			} catch (Exception e) {
//				break;
			}
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		
	};
	
	static class HttpSender implements Runnable{
		URL url = null;
		String data;
		
		public HttpSender() {
			
		}
		public HttpSender(String data, URL url) {
			this.data = data;
			this.url = url;
		}

		@Override
		public void run() {
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("POST");
				con.getInputStream();
				System.out.println("data:" + data);
			} catch (Exception e) {

			} finally {
				con.disconnect();
			}
		
		}
		
	}

	public static void main(String args[]) throws IOException {
		Client client = new Client("192.168.0.24",5555,"[SeoCan]");
		try {
			client.connect();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM6", true);
//		ss.sendSerial("W2810003B010000000000005011", "10003B01");
		//ss.close();
		

	}

}
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

import com.can.SendAndReceiveSerial;
import com.msg.Msg;

import gnu.io.CommPort;

public class Client {

	int port;
	String address;
	String id;
	Socket socket;
	
	Sender sender;
	
	// Can 연결 설정 ===================================================================================================
	SendAndReceiveSerial ss =new SendAndReceiveSerial("COM6", true);
	
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
		new Receiver(socket).start();
	} // connect end

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
		private CommPort commPort;
		
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
				
				// Can 시작 ----------------------------------------------------------------===============================
					String canss = msg.getMsg();
					ss.sendSerial(canss, "10003B01");	
					try {
						ss.close();
						ss =new SendAndReceiveSerial("COM6", true);
					} catch (IOException e) {
						e.printStackTrace();
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
		Client client = new Client("192.168.0.24",5555,"[Seo]");
		try {
			client.connect();
			client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
```



> Msg,jav 는 동일



Spring  구조

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
		client = new Client("192.168.0.24",5555,"[WEB]");
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
		client.sendTarget("/192.168.0.24", "100");
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
	
	
	@RequestMapping("/can.mc")
	public void can(HttpServletRequest res) {
		String data;
		data = res.getParameter("candata");
		System.out.println(data);
	}
	
	@RequestMapping("/controllcan.mc")
	public void controllcan(HttpServletResponse res) throws IOException {
		System.out.println("Can Send Start...");
		client.sendTarget("/192.168.0.24", "W2810011B010000000000005011");
		PrintWriter out = res.getWriter();
		out.print("ok");
		out.close();
		
	}

} // class end

```



> Client.java

```java
package com.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

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
	

	
//	public void sendTarget(String ip, String cmd) {
//		ArrayList<String> ips = new ArrayList<String>();
//		ips.add(ip);
//		Msg msg = new Msg(id,cmd);
//		sender.setMsg(msg);
//		new Thread(sender).start();
//	}
	public static void main(String[] args) {
		Client client = new Client("192.168.0.24",5555,"[Seo]");
		try {
			client.connect();
			client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
```



> main.jsp

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

<style>

#msg {
	text-align : left;
	border: 2px solid gray;
}
</style>

<script>

function display(datas){
	$('msg').append(datas)
};


/* function msg(){
	$.ajax({
		url:'/getmsg.mc',
		async : false,
		dataType : "json",
		success:function(data){
			display(data)
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
			alert("안돼냐");
		}
	});
	
}; */


$(document).ready(function(){
	$('#iot').click(function(){
		$.ajax({
			url:'iot.mc',
			success:function(data){
				alert('Send Complete...')
			/* 	msg(); */
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
	$('#controllcan').click(function(){
		$.ajax({
			url:'controllcan.mc',
			success:function(data){
				alert('Send Complete...')
			}
		});
	});
	$('#msgb').click(function(){
	$.ajax({
		url:'getmsg.mc',
		async : false,
		dataType : "json",
		success:function(data){
			display(data)
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
			alert("안돼냐");
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
<h2><a id="msgb" href="#">msgbt</a></h2>
<h2><a id="controllcan" href="#">Can on</a></h2>

<div id ='msg'></div>



</body>
</html>
```

