## Networking P676

> java.net 패키지를 사용하여 간단하게 작성 가능하다.

> Http 프로토콜에서 기 만들어져 있는 웹서버와 브라우저를 통해 연동. -> 실시간으로 데이터 전송이 안된다. 브라우저 측에서 데이터를 보내는 리퀘스트 리스폰 형태

> TCP IP 실시간으로 통신을 이루는 형태 공부 예정



서버는 서비스를 제공하는 컴퓨터 (service provider)

클라이언트는 서비스를 사용하는 컴퓨터 (service user)



### InetAddress 클래스 P680





### URLConnection 클래스 P685 day02/test.java  test2.java

### Json 데이터, 파일 받기 (xxx.jsp 에서, Http 에서)

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
> Client는 login 정보를 보내고 그 값을 출력한다.



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
		
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String id = "qqq";
			String pwd = "111";
			url = new URL(urlstr+"?id="+id+"&pwd="+pwd);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.getInputStream();
			
			is = con.getInputStream();
			isr = new InputStreamReader(is);
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
			con.disconnect();
		}
	}
}
```

