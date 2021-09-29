package com.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
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
	String iotCmd;
	Sender sender;
	
	public Client() {
		
	}
	public Client(String adress, int port, String id) {
		this.address = adress;
		this.port = port;
		this.id = id;
	}
	
	public String getIotCmd() {
		return iotCmd;
	}
	public void setIotCmd(String iotCmd) {
		this.iotCmd = iotCmd;
	}
	
	
	@Override
	public String toString() {
		return "Client [port=" + port + ", address=" + address + ", id=" + id + ", socket=" + socket + ", iotCmd="
				+ iotCmd + "]";
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
		// Server�뿉�꽌 蹂대궦 �뜲�씠�꽣 諛쏄린 �쐞�빐 receiver �떎�뻾 
		 new Receiver(socket).start();
	} // connect end
	
	// 지정한 IP로 메세지를 보내는 기능
	public void sendTarget(String ip, String cmd) {
		ArrayList<String> ips = new ArrayList<String>();
		ips.add(ip);
		Msg msg = new Msg(id,cmd);
		sender.setMsg(msg);
		new Thread(sender).start();
	}
	

	// Scanner濡� Input 媛� 諛쏄린
	public void sendMsg() {
		Scanner sc = new Scanner(System.in);
		if (socket != null) {
		while(true) {
			System.out.println("Input Msg");
			String ms = sc.nextLine();
			// 1�쓣 蹂대궡硫� �꽌踰꾩뿉�꽌�뒗 Client 由ъ뒪�듃瑜� 蹂대궦�떎. ------------------------------------�듅�젙 洹쒖튃�쓣 �젙�빐�빞 �븳�떎.
			Msg msg = null;
			if(ms.equals("1")) {
				msg = new Msg(id,ms);
			}else {
				// �듅�젙 �씤�썝�뱾�뿉寃� 洹볦냽留먯쓣 蹂대궪�븣 Msg.java �닔�젙�뻽�쓬--------------------------------------------
				// ArrayList濡� 洹볦냽留� 蹂대궪 IP 二쇱냼瑜� �꽔�뼱 Server�뿉 蹂대궡硫� �빐�떦 IP �뱾�뿉寃� Server�뿉�꽌 硫붿떆吏� �쟾�넚
				ArrayList<String> ips = new ArrayList<>();
				ips.add("/192.168.0.24");
				ips.add("/192.168.0.15");
				ips.add("/192.168.0.92");
				ips.add("/192.168.0.69");
//				msg = new Msg(ips,id,ms);
				msg = new Msg(null,id,ms);
			}
			// 洹볦냽留� �븯湲� �쐞�븳 Ip �젙蹂� 異붽� �쟾�넚 Server�뿉�꽌 Ip瑜� �씫�쑝硫� �븵�뿉 / 媛� �옄�룞�쑝濡� 遺숆린 �븣臾몄뿉 洹� �삎�떇�쓣 留욎텛湲� �쐞�빐 / 異붽�
	//		Msg msg = new Msg("/192.168.0.24",id,ms);
			
			sender.setMsg(msg);
			new Thread(sender).start();
			
			if(ms.equals("q")) {
				break;
			}
		  }
		}
		sc.close();
		// 諛섎뱶�떆 醫낅즺 ----------------------
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bye...");
	} // sendMsg end
	
	// Runnable �씠�슜�빐�빞�븳�떎.
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
					// �씠�븣 Exception �� Server 媛� 二쎌뼱�엳�쓣 �솗瑜좎씠 �겕�떎.
					//	e.printStackTrace();
					try {						
						if(socket != null) {
							socket.close();
						}

					}catch(Exception e1) {
						e1.printStackTrace();
					}
					// Server�� �옱 �뿰寃� �떆�룄 ---------------------------
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
	
	// Server�뿉�꽌 蹂대궦 硫붿떆吏� InputStream�쑝濡� 諛쏄린
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
					// 1�쓣 �엯�젰 �뻽�쓣 �븣 Client list 諛쏄린 ----------------------
					if(msg.getMaps() != null) {
						HashMap<String,Msg> hm = msg.getMaps();
						Set<String> keys =  hm.keySet();
						for(String k : keys) {
							System.out.println(k);
						}
						continue; // 怨꾩냽 諛쏆븘�빞 �븯湲� �븣臾몄뿉 �븘�옒瑜� 嫄대꼫 �쎇怨� �떎�떆 �떎�뻾
					}
					System.out.println(msg.getId()+" : "+msg.getMsg());
					// IoT에서 서버로 데이터를 전송한다. 즉 http방식이지 Client통신이라고 보기는 어렵다. 다른방식을 사용해야할듯.
					if(msg.getMsg().contains("turned")) {
						iotCmd = msg.getMsg();
					}
					if(msg.getId().equals("[Chaeguevara_IoT]")) {

						String urlstr = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/tcpipWebCon/iotToServer.mc";
						//String urlstr = "http://127.0.0.1/tcpip/iotToServer.mc";
						URL url = null;
						HttpURLConnection con = null;
						
						try {
							url = new URL(urlstr + "?ss=" + msg.getMsg());
							con = (HttpURLConnection) url.openConnection();
							// wait 5 sec
							con.setReadTimeout(5000);
							con.setRequestMethod("POST");
							con.getInputStream();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							con.disconnect();
						}
						
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
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
		Client client = new Client("192.168.0.13",5555,"[ChaeWeb]");
		try {
			client.connect();
			client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}