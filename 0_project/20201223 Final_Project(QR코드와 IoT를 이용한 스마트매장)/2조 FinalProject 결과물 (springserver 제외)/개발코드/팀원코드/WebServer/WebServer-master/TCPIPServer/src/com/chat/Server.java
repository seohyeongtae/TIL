package com.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.msg.Msg;

public class Server {

	int port;
	HashMap<String, ObjectOutputStream> maps;

	ServerSocket serverSocket;

	public Server() {

	}

	public Server(int port) {
		this.port = port;
		maps = new HashMap<>(); // 해쉬맵을 이용해 받아온 채팅 값을 저장할 것임.
	}

	public void startServer() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Start Server ...");

		// Thread 안에서 진행을 하는 것이 좋다. 굳이 안해도 프로그램이 돌아가긴한다.
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					// try catch 로 Exception을 잡아야 Server 즉 while 문이 종료되지 않는다. (Server가 죽지
					// 않는다)-------------------
					try {
						Socket socket = null;
						System.out.println("Ready Server...");
						socket = serverSocket.accept();
						// socket.getInetAddress() 어드레스
						System.out.println(socket.getInetAddress());

						new Receiver(socket, makeOut(socket)).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while end
			}
		}; // Runnable and
		new Thread(r).start();
	} // startServer end

	// 각각의 client가 접속을 했을 때 각각 socket이 만들어지면 socket을 가지고 OutputStream을 만들어 hashmap에
	// 담는 역할
	public ObjectOutputStream makeOut(Socket socket) throws IOException {
		ObjectOutputStream oo;
		oo = new ObjectOutputStream(socket.getOutputStream());
		// 원래는 Key 값이 들어가야 하지만, 각자 접속한 InputStream 값을 넣는다.
		maps.put(oo.toString(), oo);
		System.out.println(oo.toString());
		System.out.println("접속자 수: " + maps.size());
		return oo;
	} // makeOut end

	class Receiver extends Thread {
		Socket socket;
		ObjectInputStream oi;
		ObjectOutputStream oo;

		public Receiver(Socket socket) throws IOException {
			this.socket = socket;
			oi = new ObjectInputStream(socket.getInputStream());
		}

		public Receiver(Socket socket, ObjectOutputStream oo) throws IOException {
			this.socket = socket;
			oi = new ObjectInputStream(socket.getInputStream());
			this.oo = oo;
		}

		@Override
		public void run() {
			while (oi != null) {
				Msg msg = null;
				try {
					msg = (Msg) oi.readObject();
					//Test
					System.out.println("Run Receiver: "+oo.toString());
					if (msg.getMsg().equals("q")) {
						throw new Exception();
					} else if (msg.getMsg().equals("1")) {
						// 1을 보낸 Client 에게 현재 접속중인 Client List 보내기
						String ip = socket.getInetAddress().toString();
						ArrayList<String> ips = new ArrayList<>();
						ips.add(ip);
						msg.setIps(ips);
						// �쁽�옱 Hashmap �뿉 �뱾�뼱媛� �엳�뒗 Key (�궗�슜�옄 ip) 瑜� 爰쇰궦�떎.
						Set<String> keys = maps.keySet();
						HashMap<String, Msg> hm = new HashMap<>();
						for (String k : keys) {
							hm.put(k, null);
						}
						// hm �뿉�뒗 1�쓣 蹂대궦 client �젙蹂� / Server �젒�냽�옄 IP �젙蹂대뱾 �씠 �뱾�뼱媛� �엳�떎.
						msg.setMaps(hm);

					}
					// 실제 프로젝트에서 사용되는 부분.
					System.out.println(msg.getId() + " : " + msg.getMsg());
					sendMsg(msg);
				} catch (Exception e) {
					// Exception 이 나면 client 와 연결이 끊어 졌다는 의미이므로 maps 에서 삭제 (비정상 종료)
					maps.remove(oo.toString());
					System.out.println(oo.toString() + ".. Exited");
					System.out.println("접속자수 : " + maps.size());
					break;
				}
				// System.out.println(msg.getId()+" : "+msg.getMsg()); �쐞�뿉 �꽔�뼱�룄 �맂�떎.
			} // end while
				// 반드시 close 를 시켜주어야 한다. --------------------------------------------
			try {
				if (oi != null) {
					oi.close();
					System.out.println("oi.close()");
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {

			}
		} // run end

	} // Receiver end
		// Receiver 硫붿떆吏�瑜� 諛쏆쑝硫� 洹� 硫붿꽭吏�瑜� 媛�吏�怨� Sender瑜� �샇異쒗븯�뒗 �뿭�븷

	public void sendMsg(Msg msg) {
		Sender sender = new Sender();
		sender.setMsg(msg);
		sender.start();
	}

	// 한 Client가 보낸 msg 즉, hashmap에 있는 데이터를 가지고 Client 들에게 전송을 함.
	class Sender extends Thread {
		Msg msg;

		public void setMsg(Msg msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			// Hashmap �뿉 �엳�뒗 媛� 爰쇰궡湲�
			Collection<ObjectOutputStream> cols = maps.values();
			Iterator<ObjectOutputStream> it = cols.iterator();
			while (it.hasNext()) {
				try {
					// 洹볦냽留� 蹂대궪�븣 null媛믨낵 怨듬갚�씪 寃쎌슦瑜� 媛숈씠 泥댄겕瑜� �빐�빞 �븳�떎. ----------------------
					// 洹볦냽留�
					// if (msg.getIp() != null || !msg.getIp().equals("")) {
					// maps.get(msg.getIp()).writeObject(msg);
					// break;
					// �듅�젙 �씤�썝�뱾�뿉寃� 洹볦냽留먯쓣 蹂대궪 �뻹 -------------------------------------
					// 원하는 IP에만 보내는 기능. 우리 시나리오에는 딱히 필요 없음.
					if (msg.getIps() != null) {
						for (String ip : msg.getIps()) {
							maps.get(ip).writeObject(msg);
						}
						break;
					}
					// client �쟾泥댁뿉 硫붿떆吏� 蹂대궡湲� broadcast
					it.next().writeObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} // run end

	} // class Sender end

	public static void main(String[] args) {
		Server server = new Server(5555);
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}