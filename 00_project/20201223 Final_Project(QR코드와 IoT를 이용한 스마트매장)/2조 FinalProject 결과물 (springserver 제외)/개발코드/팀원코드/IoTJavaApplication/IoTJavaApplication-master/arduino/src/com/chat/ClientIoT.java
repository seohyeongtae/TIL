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

public class ClientIoT {

	int port;
	String address;
	String id;
	Socket socket;
	Sender sender; // ������ �޼��� ������ ��
	String gMessage;

	public ClientIoT() {
	}

	public ClientIoT(String address, int port, String id) {
		this.address = address;
		this.port = port;
		this.id = id;
	}

	
	public String getgMessage() {
		return gMessage;
	}

	public void setgMessage(String gMessage) {
		this.gMessage = gMessage;
	}

	public void connect() throws IOException {
		// ��Ĺ�� ����
		try {
			socket = new Socket(address, port);
		} catch (Exception e) {
			while (true) {
				try {
					Thread.sleep(2000);
					socket = new Socket(address, port);
					break;
				} catch (Exception e1) {
					System.out.println("Retry...");
				}

			}
		} // exception�� ���� ó�� �� ����. ���ѷ����� ���� ��� ���� �õ�

		System.out.println("Connected Server:" + address);
		sender = new Sender(socket); // ������ ��ü�� Ʋ�� ���� ��.
		new Receiver(socket).start(); // ���ù��� �����.
	}

	public void sendMsg(String signal) {
		// IoT장비의 신호를 받아서 TCP/IP로 보낸다.

		String ms = signal;
		// 1이면 메세지 객체에, 아이디와 메세지 내용을 설정한다.
		Msg msg = null;
		if (ms.equals("1")) {
			msg = new Msg(id, ms);
		} else {
			ArrayList<String> ips = new ArrayList();// ����Ʈ�� �޼����� ���� ip����Ʈ ����
			ips.add("/192.168.0.92");
			ips.add("/192.168.0.24");
			ips.add("/192.168.0.15");
			ips.add("/192.168.0.70");
			ips.add("/192.168.0.2");
			msg = new Msg(null, id, ms);
			//원래 특정 단체에게 메세지 보내기 기능인듯 하지만 null값으로 막아놓음
		}
		sender.setMsg(msg);
		new Thread(sender).start(); //메세지를 보낸다.

		
		
	}
	
	public void closeSocket() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bye...");
	}

	class Sender implements Runnable {
		// ������ ����, Ʋ�̴�
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
			if (oo != null) {
				try {
					oo.writeObject(msg); // 그냥 메세지를 보낸다. ID와 함께.
				} catch (IOException e) {
					// e.printStackTrace();
					try {
						if (socket != null) {
							socket.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(2000);
						connect();
					} catch (Exception e1) {
						e1.printStackTrace();
					} // �ٽ� Ŀ��Ʈ
				}
			}
		}
	}

	class Receiver extends Thread {
		// �������� ����
		// ������ �ʿ��ϴ�. �����͸� �ޱ� ����
		ObjectInputStream oi;

		public Receiver(Socket socket) throws IOException {
			oi = new ObjectInputStream(socket.getInputStream());
		}

		@Override
		public void run() {
			while (oi != null) {
				Msg msg = null;
				try {
					msg = (Msg) oi.readObject();
					if (msg.getMaps() != null) { // Key���� ������.
						HashMap<String, Msg> hm = msg.getMaps();
						Set<String> keys = hm.keySet();
						for (String k : keys) {
							System.out.println(k);
						}
						continue;
					}
					//받은 메세지를 gMessage에 보낸다.
					System.out.println(msg.getId() + msg.getMsg());
					
					//특정 아이디 받은 메세지는 명령어로 사용해서 IoT장비에 보낸다.
					if(msg.getId().equals("[Chaeguevara_PC]")) {
						gMessage = msg.getMsg();
					}
					if(msg.getId().equals("[ADMIN]")) {
						gMessage = msg.getMsg();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			} // end while
			try {
				if (oi != null) {
					oi.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {

			}
		}

	}



}
