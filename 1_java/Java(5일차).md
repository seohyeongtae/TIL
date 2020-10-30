## Server , Client 데이터 주고 받기 HashMap 사용 (귓속말 기능 추가), (서버에 접속한 Client List 요청하기.)

> **특정 사람들에게 귓속말을 보내기 위해 Msg.java 에 HashMap 객체 추가 (Getter, Setter)**
>
> ip 역시 ArrayList 로 변경  ,객체의 직렬화 필수 public class Msg implements Serializable

1. 전체에게 메시지 보내기
2.  특정인에게 메시지 보내기
3.  특정인들에게 메시지 보내기
4. 메시지 받기
5. 서버 접속자 정보 조회
6. 나가기

> Msg.java

```java
package com.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Msg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String,Msg> maps;
	private ArrayList<String> ips;
	private String id;
	private String msg;
	
	public Msg() {
	}
		
	public Msg(String id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	
	public Msg(ArrayList<String> ips, String id, String msg) {
		this.ips = ips;
		this.id = id;
		this.msg = msg;
	}

	public Msg(HashMap<String, Msg> maps, ArrayList<String> ips, String id, String msg) {
		this.maps = maps;
		this.ips = ips;
		this.id = id;
		this.msg = msg;
	}

	public HashMap<String, Msg> getMaps() {
		return maps;
	}
	public void setMaps(HashMap<String, Msg> maps) {
		this.maps = maps;
	}
	public ArrayList<String> getIps() {
		return ips;
	}
	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
```



> Server

```java
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
		maps = new HashMap<>(); // Key값 = Client Ip주소  , Value = Client OutputStream 정보
	}

	public void startServer() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Start Server ...");
		
		// Thread 안에서 진행을 하는 것이 좋다. 굳이 안해도 프로그램이 돌아가긴한다.
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					// try catch 로 Exception을 잡아야 Server 즉 while 문이 종료되지 않는다. (Server가 죽지 않는다)-------------------
					try {
						Socket socket = null;
						System.out.println("Ready Server...");
						socket = serverSocket.accept();
						// socket.getInetAddress() = client의 ip 정보 출력
						System.out.println(socket.getInetAddress());
						makeOut(socket);

						new Receiver(socket).start();
					}catch(Exception e){
						e.printStackTrace();
					}
				} // while end
			}
		}; // Runnable and
		new Thread(r).start();
	} // startServer end
	
		// 각각의 client가 접속을 했을 때 각각 socket이 만들어지면 socket을 가지고 OutputStream을 만들어 hashmap에 담는 역할
	public void makeOut(Socket socket) throws IOException {
		ObjectOutputStream oo;
		oo = new ObjectOutputStream(socket.getOutputStream());
		maps.put(socket.getInetAddress().toString(), oo);
		System.out.println("현재 접속자수 : " + maps.size());
	} // makeOut end

	class Receiver extends Thread {
		Socket socket;
		ObjectInputStream oi;

		public Receiver(Socket socket) throws IOException {
			this.socket = socket;
			oi = new ObjectInputStream(socket.getInputStream());
		}

		@Override
		public void run() {
			while (oi != null) {
				Msg msg = null;
				try {
					msg = (Msg) oi.readObject();
					if(msg.getMsg().equals("q")) {
						// q를 누른 정상 종료 강제로 Exception 을 만들어 catch로 던진다. (코드 간략화)------------
						throw new Exception();
						// 1을 보낸 Client 에게 현재 접속중인 Client List 보내기
					} else if(msg.getMsg().equals("1")) {
						// 1 을 보낸 (Client List를 요청한) Client 의 IP 정보
						String ip = socket.getInetAddress().toString();
						ArrayList<String> ips = new ArrayList<>();
						ips.add(ip);
						msg.setIps(ips);
						// 현재 Hashmap 에 들어가 있는 Key (사용자 ip) 를 꺼낸다.
						Set<String> keys = maps.keySet();
						HashMap<String, Msg> hm = new HashMap<>();
						for(String k: keys) {
							hm.put(k, null);
						}
						// hm 에는 1을 보낸 client 정보 / Server 접속자 IP 정보들 이 들어가 있다.
						msg.setMaps(hm);
						
					} // else if end
					System.out.println(msg.getId()+" : "+msg.getMsg());
					sendMsg(msg);
				} catch (Exception e) {
					// Exception 이 나면 client 와 연결이 끊어 졌다는 의미이므로 maps 에서 삭제 (비정상 종료)
					maps.remove(socket.getInetAddress().toString());
					System.out.println(socket.getInetAddress()+ ".. Exited");
					System.out.println("현재 접속자수 : "+maps.size());
					break;
				}
				//System.out.println(msg.getId()+" : "+msg.getMsg()); 위에 넣어도 된다.
			} // end while
			// 반드시 close 를 시켜주어야 한다. --------------------------------------------
			try {
				if(oi != null) {
					oi.close();
				}
				if(socket != null) {
					socket.close();
				}
			} catch(Exception e) {
				
			}
		} // run end

	} // Receiver end
		// Receiver 메시지를 받으면 그 메세지를 가지고 Sender를 호출하는 역할

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
			// Hashmap 에 있는 값 꺼내기
			Collection<ObjectOutputStream> cols = maps.values();
			Iterator<ObjectOutputStream> it = cols.iterator();
			while(it.hasNext()) {
				try {
				// 귓속말 보낼때 null값과 공백일 경우를 같이 체크를 해야 한다. ---------------------- 귓속말
				//if (msg.getIp() != null || !msg.getIp().equals("")) {
				//	maps.get(msg.getIp()).writeObject(msg);
				//	break;
					// 특정 인원들에게 귓속말을 보낼 떄 -------------------------------------
					if (msg.getIps() != null) {
						for(String ip :msg.getIps()) {
							maps.get(ip).writeObject(msg);	
						}					
						break;
					}
					
					//client 전체에 메시지 보내기 broadcast  
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
```



> Client

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
				msg = new Msg(ips,id,ms);
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



### Android  와 연동 (Android - Client)

1. 전체에게 메시지 보내기
2.  특정인에게 메시지 보내기
3. 메시지 받기
4. 서버 접속자 정보 조회
5. 나가기