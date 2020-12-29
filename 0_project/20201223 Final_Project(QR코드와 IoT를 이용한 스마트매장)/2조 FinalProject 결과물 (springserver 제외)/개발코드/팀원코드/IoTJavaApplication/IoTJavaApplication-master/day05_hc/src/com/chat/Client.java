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
	Sender sender; // ������ �޼��� ������ ��
	
	public Client() {}
	public Client(String address, int port, String id) {
		this.address = address;
		this.port = port;
		this.id = id;
	}
	
	public void connect() throws IOException  {
		//��Ĺ�� ����
		try {
			socket = new Socket(address,port);
		} catch (Exception e) {
			while(true) {
				try {
					Thread.sleep(2000);
					socket = new Socket(address,port);
					break;
				}catch(Exception e1) {
					System.out.println("Retry...");
				}
				
			}
		}  // exception�� ���� ó�� �� ����. ���ѷ����� ���� ��� ���� �õ�

		System.out.println("Connected Server:"+address);
		sender = new Sender(socket); //������ ��ü�� Ʋ�� ���� ��.
		new Receiver(socket).start(); //���ù��� �����.
	}
	
	public void sendMsg() {
		//��ĳ�ʿ��� �Է¹޾� ����.
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input msg");
			String ms = sc.nextLine();
			// 1�� ������ ���������� ����� ����Ʈ�� ������.
			Msg msg = null;
			if(ms.equals("1")) {
				msg = new Msg(id,ms);				
			}else {
				ArrayList<String> ips = new ArrayList();// ����Ʈ�� �޼����� ���� ip����Ʈ ����
				ips.add("/192.168.0.92");
				ips.add("/192.168.0.24");
				ips.add("/192.168.0.15");
				ips.add("/192.168.0.70");
				ips.add("/192.168.0.2");
				msg = new Msg(null,id,ms);
			}
			sender.setMsg(msg);
			new Thread(sender).start(); // ������ ������.
			if(ms.equals("q")) {
				break;
			}
		}
		sc.close();
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Bye...");
	}
	
	class Sender implements Runnable{
		//������ ����, Ʋ�̴�
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
					oo.writeObject(msg); // exception�� ������ �������� Ȯ���� ŭ
				} catch (IOException e) {
					//e.printStackTrace();
					try {
						if(socket!=null) {
							socket.close();
						}
					}catch(Exception e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(2000);
						connect();
					} catch (Exception e1) {
						e1.printStackTrace();
					}//�ٽ� Ŀ��Ʈ
				}
			}
		}	
	}
	
	class Receiver extends Thread{
		//�������� ����
		//������ �ʿ��ϴ�. �����͸� �ޱ� ����
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
					if(msg.getMaps() != null) { //Key���� ������.
						HashMap<String,Msg> hm = msg.getMaps();
						Set<String> keys = hm.keySet();
						for(String k: keys) {
							System.out.println(k);
						}
						continue;
					}
					System.out.println(msg.getId()+msg.getMsg());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				} 
			} //end while
			try {
				if(oi!=null) {
					oi.close();
				}
				if(socket!=null) {
					socket.close();
				}
			}catch(Exception e) {
				
			}
		}
		
	}
	
	public static void main(String[] args) {
		Client client = new Client("192.168.0.24",5555,"[Chaeguevara_IoT]");
		try {
			client.connect();
			client.sendMsg();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
