## Networking P676

> java.net 패키지를 사용하여 간단하게 작성 가능하다.

> Http 프로토콜에서 기 만들어져 있는 웹서버와 브라우저를 통해 연동. -> 실시간으로 데이터 전송이 안된다. 브라우저 측에서 데이터를 보내는 리퀘스트 리스폰 형태

> TCP IP 실시간으로 통신을 이루는 형태 공부 예정



서버는 서비스를 제공하는 컴퓨터 (service provider)

클라이언트는 서비스를 사용하는 컴퓨터 (service user)

![KakaoTalk_20201027_151617400_05](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_05.jpg)

![KakaoTalk_20201027_151617400_04](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_04.jpg)

![KakaoTalk_20201027_151617400_03](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_03.jpg)



### InetAddress 클래스 P680

![KakaoTalk_20201027_151617400_06](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_06.jpg)



### URLConnection 클래스 P685 day02/test.java  test2.java

### Json 데이터, 파일 받기 (xxx.jsp 에서, Http 에서)

![KakaoTalk_20201027_151617400_02](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_02.jpg)

![KakaoTalk_20201027_151617400_01](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400_01.jpg)

> spring 에 있는 users.jsp 에서 Json 데이터 불러오기
>
> Reader 사용

```java
package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test1 {

	public static void main(String[] args) {
		// spring 에 있는 users.jsp 에서 Json 데이터 불러오기
		String urlstr = "http://192.168.1.107:8000/network/users.jsp";
		URL url = null;
		URLConnection con = null;
		
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			url = new URL(urlstr);
			con = url.openConnection();
			
			is = con.getInputStream();
			isr = new InputStreamReader(is); // 한글이 깨질경우에는 (is,"UTF-8") 로 적기
			br = new BufferedReader(isr);
			
			String str = "";
			while((str = br.readLine()) != null) {
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

} // class end
```



> spring 에 있는 users.jsp 에서 파일 가지고 오기
>
> InputStream 사용

```java
package com.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Test2 {

	public static void main(String[] args) {
		// spring 에 있는 users.jsp 에서 파일 가지고 오기
		String urlstr = "http://192.168.1.107:8000/network/mp.mp3";
		URL url = null;
		URLConnection con = null;
		
		// 파일가지고오기 InputStream 은 byte 로 받아온다.
		InputStream is = null;
		BufferedInputStream bis = null;
		
		// 내 컴퓨터에 저장
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			url = new URL(urlstr);
			con = url.openConnection();
			is = con.getInputStream();
			bis = new BufferedInputStream(is,10240000); // 사이즈는 안써도 된다. 
			
			// 파일 저장
			fos = new FileOutputStream("newmp.mp3");
			bos = new BufferedOutputStream(fos);
			
			int data =0;
			while ((data = bis.read()) != -1){
				bos.write(data);
				//System.out.println(data);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

} // class end
```



### 자신의 위치정보 Server에 계속 보내고 받기 HttpURLConnection  /Test3.java (일정시간별 정보 보내기 Thread.sleep 활용)

> Server (spring / network / car.jsp) -> 위치정보 받기

> car.jsp

```java
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<% 
	String lat = request.getParameter("lat");
	String lng = request.getParameter("lng");
	
	System.out.println(lat + " " + lng);
%>
```



> network / day02 / Test3  -> 위치정보 보내기

```java
package com.http;

import java.net.HttpURLConnection;
import java.net.URL;


public class Test3 {

	public static void main(String[] args) {
		String urlstr = "http://192.168.1.107:8000/network/car.jsp";
		URL url = null;
		HttpURLConnection con = null;
		while(true) {
		try {
			double lat = Math.random()*90+1;
			double lng = Math.random()*90+1;
			url = new URL(urlstr+"?lat="+lat+"&lng="+lng);
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		// 5초에 한번씩 데이터를 전송하도록
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		} // while end
	}
}
```



### WorkShop

> Server에 login.jsp 를 만들고 id 값이 "qqq" 이고 pwd 값이 '111' 일경우에는 1을, 그외는  0을 내보낸다.
>
> Client는 서버에 login 정보를 보내고 반환 값을 출력한다.



> Server  login.jsp

```java
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

    <% 
    String id = request.getParameter("id");
    String pwd = request.getParameter("pwd");
    
    if(id.equals("qqq") && pwd.equals("111")){
    	out.print("1");
    }else{
    	out.print("0");
    }
    
    %>
```



> Client  Testws.java

```java
package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Testws {

	public static void main(String[] args) {
		String urlstr = "http://192.168.1.107:8000/network/login.jsp";
		URL url = null;
		HttpURLConnection con = null;
		
	//	InputStream is = null;
	//	InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String id = "qqq";
			String pwd = "111";
			url = new URL(urlstr+"?id="+id+"&pwd="+pwd);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.getInputStream();
			
		//	is = con.getInputStream();
		//	isr = new InputStreamReader(is);
			// br = new BufferedReader(isr);
			// 위 아래 둘다 가능
			br = new BufferedReader(
					new InputStreamReader(con.getInputStream()) 
					);
			
			String str = "";
			while((str = br.readLine()) != null) {
                if(str.equals("")){
                    continue;
                }
				System.out.println(str.trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			con.disconnect();
		}
	}

}

```



### 소캣 프로그래밍 , TCP/IP 프로토콜(TCP와 UDP) P690  com.tcpip 패키지

> 소캣(socket)이란 프로세스 간의 통신에 사용되는 양쪽 끝단(endpoint)을 의미한다. 서로 멀리 떨어진 두 사람이 통신하기 위해서 전화기가 필요한 것처럼, 프로세스간의 통신을 위해서는 그 무언가가 필요하고 그것이 바로 소켓이다.

> server 는 도중에 중지가 되면 안되고 client 는 연결이 안되었을 경우 지속적으로 server 에 연결을 해야 한다. 또한 server와 client 는 Thread로 구성하여 파일을 보내고 받는 중에도 다른 일을 할 수 있게 해야 한다.
>
> ( Thread 는 미작성)

![KakaoTalk_20201027_151617400](Java(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201027_151617400.jpg)

> Server.java

```java
package com.tcpip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	int port = 9999;
	ServerSocket serverSocket;
	Socket socket;
	
	InputStreamReader ir;
	BufferedReader br;
	
	public Server() {
		
	}
	public void startServer() throws IOException {
		serverSocket = new ServerSocket(port);
		// Server 는 꺼지면 안되기 때문에 while 무한 루프를 돌려야 한다.
		try {
			while(true) {
				// socket 은 서버, 클라이언트와의 연결 해주는 역할
				System.out.println("Ready Server..");
				socket = serverSocket.accept();
				System.out.println("Connected..");
				
				// client 에 데이터를 받는다 .
				ir = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(ir);
				String msg = "";
				msg = br.readLine();
				System.out.println(msg);
				} // while end
			// Exception 처리 따로 꼭 해주어야 한다. Reader 에서 발생할 수 있기 때문에 --------------------------
		} catch(Exception e) {
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
			if(socket != null) {
				socket.close();
			}
		}
	} // startServer end
	
	public static void main(String[] args) {
		Server server = null;
		server = new Server();
		try {
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End Server");
	}

}
```



> Client.java

```java
package com.tcpip;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	int port;
	String ip;

	Socket socket;

	OutputStreamWriter ow;
	BufferedWriter bw;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void connectServer() {
		try {
			System.out.println("Start Client");
			socket = new Socket(ip, port);
			System.out.println("Connected...");
		} catch (Exception e) {
			// 연결이 되지 않았을 경우 계속 재시도
			while (true) {
				try {
					Thread.sleep(2000);
					socket = new Socket(ip, port);
					System.out.println("Connected...");
					break;
				} catch (Exception e1) {
					System.out.println("Re Try...");
				}
			} // while end
		}
	} // connectServer end

	// server 에 데이터 보내기
	public void request(String msg) throws IOException {
		// try catch 로 close를 하지 않으면 Server 에서 에러가 뜬다.
		try {
			ow = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(ow);
			bw.write(msg);
		} catch (Exception e) {
			throw e;
		}
	} // request end

	public void close() throws IOException {
		if (bw != null) {
			bw.close();
		}
		if (socket != null) {
			socket.close();
		}
	} // close end

	public static void main(String[] args) {
		Client client = null;
		client = new Client("192.168.1.107", 9999);
		client.connectServer();
				
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input msg");
			String msg = sc.nextLine();
			if(msg.equals("q")) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			try {
				client.request(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // while end
		
		System.out.println("End Client");
	}

}
```

