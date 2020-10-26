## Process (프로세스)와 Thread (쓰레드) 506P~

멀티쓰레드의 장점

- CPU의 사용률을 향상시킨다.
- 자원을 보다 효율적으로 사용할 수 있다.
- 사용자에 대한 응답성이 향상된다.
- 작업이 분리되어 코드가 간결해진다.

> 멀티쓰레드를 가장 잘 활용하는 곳 중 하나가 웹서버이다.
>
> Thread 관리 방법 중 Asynctask 및 handler  기능은  Android에만 있다.



> Thread  클래스를 상속 Test.java

```java
package com.thread;


// class 에서 상속 
class T extends Thread{

	// Asynctask 및 handler 는 Android에만 있다. 
	
	String name;
	
	public T () {}
	public T(String name) {
		this.name = name;
	}
	
	// Override Method
	@Override
	public void run() {
		for(int i =0; i<=100; i++) {
			System.out.println(name+ ":"+i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} // run end
	
} // class T end


public class Test {

	public static void main(String[] args) {
		// Thread 실행
		T t1 = new T("T1");
		t1.start();
		T t2 = new T("T2");
		t2.start();
		
	}

}
```



> Runnable 인터페이스를 구현 Test2.java
>
> t1.setPriority(10); 최대 우선순위 숫자가 높을 수록 우선순위가 높다.

```java
package com.thread;


// interface 에서 상속
class Th implements Runnable{

	// Asynctask 및 handler 는 Android에만 있다. 
	
	String name;
	
	public Th () {}
	public Th(String name) {
		this.name = name;
	}
	
	// Override Method
	@Override
	public void run() {
		for(int i =0; i<=100; i++) {
			System.out.println(name+ ":"+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} // run end
	
} // class T end


public class Test2 {

	public static void main(String[] args) {
		// Thread 실행
		Thread t1 = new Thread(new Th("T1"));
		t1.start();
		// 우선순위 정하기 숫자가 높을 수록 우선순위가 높아진다. 
		t1.setPriority(10);
		Thread t2 = new Thread(new Th("T2"));
		t2.start();
		t2.setPriority(1);
		
	}

}

```



> Thread 제어(Thread 클래스 상속) Test3.Java
>
> **stop(), suspend() 등 종료 함수는 교착상태를 일으키기 쉽기 때문에 사용하면 안된다.**
>
> flag 등을 사용하여 제어한다.

```java
package com.thread;

import java.util.Scanner;

// class 에서 상속 
class Thread1 extends Thread{
	
	boolean flag = true;
	
	public Thread1 () {}
	
	
	//Thread 제어를 위한 flag 
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	// Override Method
	@Override
	public void run() {
		System.out.println("Start");
		// flag 의 값에 따라 무한 루프 혹은 정지
		while(true) {
			if (flag == false) {
				System.out.println("Stop...");
				break;
			}
			// System.out.println("Connecting ...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("End");
	} // run end
	
} // class T end


public class Test3 {

	public static void main(String[] args) throws InterruptedException {
//		// Thread 실행
//		Thread1 t1 = new Thread1();
//		t1.start();
//		Thread.sleep(10000);
//		t1.setFlag(false);
		Thread1 t1 = null;
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input Cmd");
			String cmd = sc.nextLine();
			if(cmd.equals("start")) {
				// 객체를 생성해주지 않으면 다시 start를 했을 때 에러가 생긴다.
				t1 = new Thread1();
				t1.start();
			}else if(cmd.equals("stop")) {
				t1.setFlag(false);
			}else {
				break;
			}
		}
		sc.close();
		System.out.println("Exit Application...");
		
	}

} // class end
```



> Thread 제어(Runnable 인터페이스 구현) Test33.Java

```java
package com.thread;

import java.util.Scanner;

// class 에서 상속 
class Thread11 implements Runnable{
	
	boolean flag = true;
	
	public Thread11 () {}
	
	
	//Thread 제어를 위한 flag 
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	// Override Method
	@Override
	public void run() {
		System.out.println("Start");
		// flag 의 값에 따라 무한 루프 혹은 정지
		while(true) {
			if (flag == false) {
				System.out.println("Stop...");
				break;
			}
			// System.out.println("Connecting ...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("End");
	} // run end
	
} // class T end


public class Test33 {

	public static void main(String[] args) throws InterruptedException {

		// Runnable 은 쓰레드 객체를 한번만 생성하면 된다.
		Thread11 r = new Thread11();
		Thread t1 = null;
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input Cmd");
			String cmd = sc.nextLine();
			if(cmd.equals("start")) {
				t1 = new Thread(r);
				t1.start();
			}else if(cmd.equals("stop")) {
				r.setFlag(false);
			}else {
				break;
			}
		}
		sc.close();
		System.out.println("Exit Application...");
		
	}

} // class end
```



### 데몬 쓰레드 (Daemon thread) P525 Test4.java

> Daemon Thread 를 쓰지 않으면 종료 알람이 떠도 Thread 는 계속 동작을 한다.
>
> 메인 프로세스가 종료되면 동시에 종료되는 것 

```java
package com.thread;


	
	class TT extends Thread{

		@Override
		public void run() {
			while(true) {
				System.out.println("Thread ...");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} // run end
		
	} // class TT end

	
public class Test4 {
	
	public static void main(String[] args) throws InterruptedException {
		TT tt = new TT();
		
		// Daemon Thread 를 쓰지 않으면 종료 알람이 떠도 Thread 는 계속 동작을 한다.
		tt.setDaemon(true);
		tt.start();
		Thread.sleep(10000);
		System.out.println("End Application...");

	}

} // class end
```



### 쓰레드의 상태 P527

![2](Java(1일차)/2.jpg)

### join() 과 yield P535  Test5.java

```java
package com.thread;


class Th1 extends Thread{
	int sum;
	@Override
	public void run() {
		for(int i = 0; i<=20;i++) {
			sum += i;
			System.out.println("Th1....................");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getSum() {
		return sum;
	}
	
}

class Th2 extends Thread{
	int sum;
	@Override
	public void run() {
		for(int i = 0; i<=40;i++) {
			sum += i;
			System.out.println("Th2....................");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getSum() {
		return sum;
	}
	
}

public class Test5 {

	public static void main(String[] args) throws InterruptedException {
		Th1 th1 = new Th1();
		Th2 th2 = new Th2();
		System.out.println("START");
		th1.start();
		System.out.println("TH1 STARTED");
		th2.start();
		System.out.println("TH2 STARTED");

		// th1 이 끝나기 전까지 아래로 내려가지 않는다.
		// Thread 를 모두 실행 시킨 이후 join 을 해야 Thread 작업에 의미가 있다.
		// th1 이 끝나기 전까지 실행이 되지 않으면 Thread 작업의 의미가 없기 때문에
		th1.join();
		th2.join();
		
		// 두 Thread 작업이 모두 끝난 뒤 결과값이 나온다. 
		System.out.println(th1.getSum()+" "+th2.getSum());
		System.out.println(th1.getSum()+th2.getSum());

	}
}
```



### 쓰레드의 동기화(synchronization) P537  com.sync 패키지 / com.sus

> 한 쓰레드가 진행중인 작업을 다른 쓰레드가 간섭하지 못하게 막는 것



> Accout.java

```java
package com.sync;

public class Account {
	private int balance;
	public Account() {
		
	}
	public Account(int balance) {
		this.balance = balance;
	}
	public int getBalance() {
		return balance;
	}
	
	public synchronized void deposit(int money) {
		if(money > 0) {			
			try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			balance += money;
		}
	}
	public void withdraw(int money) {
		synchronized (this) {
			if(balance >= money && money > 0) {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				balance -= money;
			}
		}
	}
	@Override
	public String toString() {
		return "Account [balance=" + balance + "]";
	}

}
```



> Bank.java

```java
package com.sync;

public class Bank {

	public static void main(String[] args) {
		Account acc = new Account(3000);
		Dth dt = new Dth(acc);
		Wth wt = new Wth(acc);
		wt.start();
		dt.start();

	}

}
```



> Dth.java

```java
package com.sync;

public class Dth extends Thread{
	Account acc;
	public Dth (Account acc) {
		this.acc = acc;
	}
	@Override
	public void run() {
		while(acc.getBalance() >= 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int money = (int)(Math.random()*9+1)*100;
			acc.deposit(money);
			System.out.println(acc);
		}
	}	
}
```



> Wth.java

```java
package com.sync;

public class Wth extends Thread{
	Account acc;
	public Wth (Account acc) {
		this.acc = acc;
	}
	@Override
	public void run() {
		while(acc.getBalance() >= 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int money = (int)(Math.random()*9+1)*100;
			acc.withdraw(money);
			System.out.println(acc);
		}
	}

}
```



> com.sus -> test.java

```java
package com.sus;

import java.util.Scanner;

class Th extends Thread {

	boolean stop = false;
	boolean sus = false;
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public void setSus(boolean sus) {
		this.sus = sus;
	}
	
	@Override
	public void run() {
		while(true) {
			if(stop==true) {
				System.out.println("Strop Thread...");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(sus != true) {
				System.out.println("Downloading ...");
			}
		}
		System.out.println("End Thread...");
	}
	
}


public class Test {

	public static void main(String[] args) {
		Th th = new Th();
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input cmd");
			String cmd = sc.nextLine();
			if(cmd.equals("start")) {
				th = new Th();
				th.start();
			}else if(cmd.equals("stop")) {
				th.setStop(true);
			}else if(cmd.equals("sus")) {
				th.setSus(true);
			}else if(cmd.equals("res")) {
				th.setSus(false);
			}else if(cmd.equals("q")) {
				th.setStop(true);
				break;
			}
		}
	}
}
```









### 입출력 (I/O)과 스트림 P624  com.io

> **반드시 close 를 해줘야한다. (인,아웃스트림)**

> I/O => Input, Output
>
> 스트림이란 데이터를 운반하는데 사용되는 연결통로이다.
>
> 스트림은 단방향통신만 가능하기 때문에 하나의 스트림으로 입력과 출력을 동시에 처리할 수 없다.
>
> 그래서 입력과 출력을 동시에 수행하려면 입력을 위한 입력스트림과 출력을 위한 출력스트림 모두 2개의 스트림이 필요하다.

![1](Java(1일차)/1.jpg)

> InputStream / OutputStream (한글이 깨진다 byte 단위로 읽어들이기 때문에)

```java
package com.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test1 {

	public static void main(String[] args) {
		String file ="c:\\network\\day01\\src\\test.txt";
		
		// Buffered 는 성능향상을 위한 보조 스트림
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream("test2.txt");
			bos = new BufferedOutputStream(fos);
			int data = 0;
			// file 의 끝이 아닐때까지 데이터를 읽어서 넣어라. 기본적으로는 byte 단위로 읽는다.
			while((data=bis.read()) != -1) {
				bos.write(data);
				System.out.println((char)data);
			}
			System.out.println(fis.available());
		} catch (Exception e) {
			e.printStackTrace();
			// 반드시 close를 해주어야 한다. Buffered 를 close를 해주면 File스트림도 close 가 된다. (buffered 안에 넣었기 때문에) 
		} finally {
			if(bis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} //finally end
	}

}
```



> Reader / Writher (readLine을 통해 한줄씩 읽어들일 수 있어 한글이 깨지지 않는다.)

```java
package com.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test2 {

	public static void main(String[] args) {
		String file ="c:\\network\\day01\\src\\test.txt";
		
		// Buffered 는 성능향상을 위한 보조 스트림
		FileReader fis = null;
		BufferedReader bis = null;
		FileWriter fos = null;
		BufferedWriter bos = null;
		try {
			fis = new FileReader(file);
			bis = new BufferedReader(fis);
			fos = new FileWriter("test2.txt");
			bos = new BufferedWriter(fos);
			String data = "";
			// file 의 끝이 아닐때까지 데이터를 읽어서 넣어라. 기본적으로는 byte 단위로 읽는다.
			// readLine 은 한 줄씩 읽어라 Writer 에만 있다. 
			while((data=bis.readLine()) != null) {
				bos.write(data);
				System.out.println(data);
			}
			//System.out.println(fis.available());
		} catch (Exception e) {
			e.printStackTrace();
			// 반드시 close를 해주어야 한다. Buffered 를 close를 해주면 File스트림도 close 가 된다. (buffered 안에 넣었기 때문에) 
		} finally {
			if(bis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} //finally end
	}

}
```



### 객체의 직렬화 ObjectOutputStream P666  com.io-> Test3

> User.java

```java
package com.io;

import java.io.Serializable;

public class User implements Serializable{
	String id;
	String name;
	public User() {

	}
	public User(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}
```



> Test3.java

```java
package com.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test3 {

	public static void main(String[] args) {
		User user = new User("id01","이말숙");
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream("user.serial");
			bos = new BufferedOutputStream(fos);
			// 기능의 확장
			oos = new ObjectOutputStream(bos);
			
			// public class User implements Serializable{ } 반드시 해주어야 stream에 통과가 된다.
			oos.writeObject(user);
			System.out.println("Write Complete...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} //  finally end
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream("user.serial");
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			User readUser = null;
			readUser = (User)ois.readObject();
			System.out.println(readUser);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	} // end main

}
```



