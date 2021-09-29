package com.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.msg.Msg;

import chat.Client;


@WebListener
public class SocketListener  implements ServletContextListener {
	int port;
	String address;
	static String id;
	static Socket socket;
	String iotCmd;
	Client client;
	HttpConnectionPut httpconnectionput;
	static Sender sender;
 
    public void contextInitialized(ServletContextEvent sce) throws RuntimeException{
        ServletContext context = sce.getServletContext();
        System.out.println("!!!!!웹 어플리케이션 시작!!!!!");
        System.out.println("서버 정보 : " + context.getServerInfo());
        
        client = new Client("3.35.11.144",5555,"[Admin]");
        
		try {
			connect();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
    
    public void contextDestroyed(ServletContextEvent sce)  {
        System.out.println("!!!!!웹 어플리케이션 종료!!!!!");
     // 반드시 종료 ----------------------
     		if(socket != null) {
     			try {
     				socket.close();
     			} catch (IOException e) {
     				e.printStackTrace();
     			}
     		}
     		System.out.println("Bye...");
        
        
    }
    
	public void connect() throws IOException {
		address = "3.35.11.144";
		port = 5555;
		id = "[ADMIN]";
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
		new Receiver(socket).start();
		// Server에서 보낸 데이터 받기 위해 receiver 실행 
//		 new Receiver(socket).start();
	} // connect end
	
	// 지정한 IP로 메세지를 보내는 기능
	
	public static void sendTarget(String ip, String cmd) {
		
		if (socket != null) {
			String ms = cmd;
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
		}
		
	} // sendMsg end
	
	
	// Runnable 이용해야한다.
	class Sender implements Runnable{
		
		Socket socket;
		ObjectOutputStream oo;
		Msg msg;
		
		
		
		public Sender(Socket socket) throws IOException {
			this.socket = socket;
			oo = new ObjectOutputStream(socket.getOutputStream());
			System.out.println(String.valueOf(oo)+"output");
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
		JSONArray ja = new JSONArray();
		public Receiver(Socket socket) throws IOException {
			oi = new ObjectInputStream(socket.getInputStream());
		}
		@Override
		public void run() {
			while(true) {
				Msg msg = null;
				try {
					Thread.sleep(1000);
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
		
						
						SimpleDateFormat format1 = new SimpleDateFormat ( "MM-dd HH:mm:ss");
						String temp = msg.getMsg().toString().trim();
						// *C 출력
						String result = temp.substring(temp.length()-2, temp.length());
						
						// *C 빼고 온도 값 출력
						String resulttemp = temp.substring(0,temp.length()-2);
							if(result.equals("q")) {
								break;
							}
						
						 if(result.equals("*C")) {
							 
							 JSONArray ja2 = new JSONArray();
							 JSONObject jo = new JSONObject();
							 
							 Date date = new Date();
							 String time = format1.format(date);
							 
//							 ja2.add(resulttemp);
//							 ja2.add(date);
							 jo.put("Temp",resulttemp);
							 jo.put("Date",time);
							 ja.add(jo);
//							 ja.add(ja2);
							 String url = "http://192.168.0.31/Admin_UI/temp.jsp";
							 String url2 = "http://192.168.0.31/Admin_UI/storetemp.jsp";	
							 String finalresult = null;
							 String finalresult2 = null;
							 if(ja.size() ==10) {
								 ja.remove(0);
							 }
								 for(int i = 0; i <ja.size(); i++){
									 	
										JSONObject jo1 = (JSONObject) ja.get(i);
										  String temp1 = (String) jo1.get("Temp");
										  String date1 = (String) jo1.get("Date");
										  finalresult += date1 + "," +temp1 +"\n";										
										  
									}
								 String finalresult1 = finalresult.substring(4, finalresult.length());
								 System.out.println(finalresult1);
								 HttpConnectionUtil httpconnectionutil = new HttpConnectionUtil();
								 httpconnectionutil.postRequest(url, finalresult1);
								
						 } 	

				} catch (Exception e) {
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
    
}

