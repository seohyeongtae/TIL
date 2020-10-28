## TCP/IP -> Thread 구현 Object Seriallization 이용



### Thread 구현 후 Socket 구현 / Input,Ouput

>  Server

```java
package com.tcpip;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	int port;
	ServerSocket serverSocket;
	Socket socket;
	
	public Server(){
		
	}
	
	public Server(int port) {
		this.port = port;
	}
	// Thread 처리
	class Receiver extends Thread{
		DataInputStream dis;
		Socket socket = null;
		public Receiver(Socket socket) {
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while(dis != null) {
				String msg = "";
				try {
					msg = dis.readUTF();
					if(msg.equals("q")) {
						System.out.println("아무개가 나갔습니다.");
						break;
					}
					System.out.println(msg);
				} catch (IOException e) {
					// client q를 누르지 않고 가 강제로 종료했을 때  error 가 난다 이것은 해결할 수 없다.
					System.out.println("아무개가 나갔습니다.");
					// e.printStackTrace();
					break;
				}
				// client 와 연결이 끊어져도 dis 는 남아있을 수 있기 때문에 그리고 socket도 종료 시킴으로써 완전히 접속 해제	
			} // while end
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} // Receiver end
	
	public void startServer() throws Exception {
		System.out.println("TCP/IP Server Start...");
		
		// serverSocket 에서 Exception 이 나면 아래로 내려가지 않기 때문에 try catch로 잡아줘야 한다.
		try {
			serverSocket = new ServerSocket(port);
			// Server 는 항상 동작되어져야 하기 떄문에 while
			while(true) {
				System.out.println("Ready Server...");
				//  Connect 하는 client 마다 socket이 생성된다. ----------------------------
				socket = serverSocket.accept();
				System.out.println("Connected...");
				new Receiver(socket).start();
			}
		}catch(Exception e) {
			throw e;
		}
	} // startServer end

	public static void main(String[] args) {
		Server server = new Server(7777);
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
package com.tcpip;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	int port;
	String address;
	
	Socket socket;
	
	public Client() {
		
	}
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void connect() throws Exception {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected...");
		} catch (Exception e) {
			while(true) {
				Thread.sleep(2000);
				try {
					socket = new Socket(address, port);
					System.out.println("Connected...");
					break;
				} catch (IOException e1) {
					System.out.println("Re-Try...");
				} 
				
			}
		}
	} // connect end
	
	class Sender extends Thread{
		DataOutputStream dos;
		String msg;
		public Sender(String msg) {
			this.msg = msg;
			try {
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			if(dos != null) {
				try {
					dos.writeUTF(msg);
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		} // run end

	} // Sender end
	
	
	public void request() throws IOException {
		Scanner sc = new Scanner(System.in);
		try {
			while(true) {
				System.out.println("[Input Msg:]");
				String msg = sc.nextLine();
				if(msg.equals("q")) {
					// Server readUTF 에서 Exception 이 나기 때문에 이를 방지 하기 위해
					new Sender("q").start();
					Thread.sleep(1000);
					System.out.println("Exit Client ..");
					break;
				}
				// 메시지 보내기
				new Sender(msg).start();
			}
		} catch(Exception e){
			
		} finally {
			sc.close();
			if(socket != null) {
				socket.close();
			}
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("192.168.0.24",7777);
		try {
			client.connect();
			client.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

```



### Object Seriallization / Object input output

**msg.java (Server,Client 구조를 완전히 똑같이 구현해야한다.) public class Msg implements Serializable 로 선언해야 한다.** 

Sender ->implement Runnable 사용 (한 Client에서 Sender가 계속 생기는 것을 막기 위해) - Server는 Receiver가 Client별로 계속 생겨야 하지만 Client는 한개만 있어야 한다.

> msg.java 

```java
package com.msg;

import java.io.Serializable;

public class Msg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String id;
	private String msg;
	public Msg() {
	}
	public Msg(String ip) {
		this.ip = ip;
	}
	public Msg(String id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	public Msg(String ip, String id, String msg) {
		this.ip = ip;
		this.id = id;
		this.msg = msg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
package com.tcpip2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.msg.Msg;

// Object Seriallization...
public class Server {
	
	int port;
	ServerSocket serverSocket;
	Socket socket;
	
	public Server(){
		
	}
	
	public Server(int port) {
		this.port = port;
	}
	// Thread 처리
	class Receiver extends Thread{
		ObjectInputStream dis;
		Socket socket = null;
		public Receiver(Socket socket) {
			this.socket = socket;
			try {
				dis = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while(dis != null) {
				Msg mo = null;
				try {
					mo = (Msg) dis.readObject();
					if(mo.getMsg().equals("q")) {
						System.out.println(mo.getId()+" 님이 나갔습니다.");
						break;
					}
					System.out.println(mo.getId()+mo.getMsg());
				} catch (Exception e) {
					// client q를 누르지 않고 가 강제로 종료했을 때  error 가 난다 이것은 해결할 수 없다.
					if(mo.getId() != null) {
					System.out.println(mo.getId()+" 님이 나갔습니다.");
					}
					// e.printStackTrace();
					break;
				}
				// client 와 연결이 끊어져도 dis 는 남아있을 수 있기 때문에 그리고 socket도 종료 시킴으로써 완전히 접속 해제	
			} // while end
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} // Receiver end
	
	public void startServer() throws Exception {
		System.out.println("TCP/IP Server Start...");
		
		// serverSocket 에서 Exception 이 나면 아래로 내려가지 않기 때문에 try catch로 잡아줘야 한다.
		try {
			serverSocket = new ServerSocket(port);
			// Server 는 항상 동작되어져야 하기 떄문에 while
			while(true) {
				System.out.println("Ready Server...");
				//  Connect 하는 client 마다 socket이 생성된다. ----------------------------
				socket = serverSocket.accept();
				System.out.println("Connected...");
				new Receiver(socket).start();
			}
		}catch(Exception e) {
			throw e;
		}
	} // startServer end

	public static void main(String[] args) {
		Server server = new Server(7777);
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
package com.tcpip2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.msg.Msg;


public class Client {
	
	int port;
	String address;
	
	Socket socket;
	Sender sender;
	
	public Client() {
		
	}
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void connect() throws Exception {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected...");
		} catch (Exception e) {
			while(true) {
				Thread.sleep(2000);
				try {
					socket = new Socket(address, port);
					System.out.println("Connected...");
					break;
				} catch (IOException e1) {
					System.out.println("Re-Try...");
				} 
				
			}
		}
		// Sender 를 미리 1개 만들어 놓는다. 인터페이서로 받으면 재사용 이 용이하다 ------------------------------
		sender = new Sender();
	} // connect end
	
	class Sender implements Runnable{
		ObjectOutputStream dos;
		Msg mo;
		public void setMo(Msg mo) {
			this.mo = mo;
		}
		public Sender() {
			try {
				dos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			if(dos != null) {
				try {
					dos.writeObject(mo);
				} catch (IOException e) {
					System.out.println("Not Avaliable..");
					// Sever와 연결이 끊어지면 프로그램 종료
					// System.exit(0);
					
					main(new String[0]); // 이거 어떻게 하지????????????? 재실행
					
					// e.printStackTrace();
				} 
			}
		} // run end

	} // Sender end
	
	
	public void request() throws IOException {
		Scanner sc = new Scanner(System.in);
		try {
			Msg mo = null;
			while(true) {
				System.out.println("[Input Msg:]");
				String msg = sc.nextLine();
				mo = new Msg("192.168.0.24","[seo]",msg.trim());
				// 	메시지 보내기
				sender.setMo(mo);
				new Thread(sender).start();
				Thread.sleep(500);
				if(msg.equals("q")) {
					System.out.println("Exit Client ..");
					break;
				}
	
			}
		} catch(Exception e){
			
		} finally {
			sc.close();
			if(socket != null) {
				socket.close();
			}
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("192.168.0.24",7777);
		try {
			client.connect();
			client.request();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
```





