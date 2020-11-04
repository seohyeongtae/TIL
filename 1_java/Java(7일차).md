## Can 통신

![can](Java(7일차)/can.PNG)

### Can 장비 Test

제어판 -> 하드웨어 및 소리 -> 장치관리자 -> 포트(COM & LPT) 에서 연결여부 확인

> 현업에서는 USB로 연결하는 것이 아니라 Serial Port로 연결하기 때문에 USB Serial Port가 나온다.

![캡처](Java(7일차)/캡처-1604389830635.PNG)

Port가 아니라 기타에 떳을때

![can](Java(7일차)/can-1604389898274.PNG)

WINDOW10_DRV.pdf 로 드라이버 재설정 하기 (연결안될 때) - 상위폴더에 있다.



Can Pro Application 

> Can 이 잘 작동되는지 확인 
>
> 동작 -> 환경설정 쓰기 -> Serial 통신 -> 통신 포트 설정

![캡처](Java(7일차)/캡처.PNG)

동작 -> 수신시작

리스트 -> 송신데이터 추가 

로 송수신 Test



### Can & Java 연동 library

lib 다운받아서 복사 하기

Copy RXTXcomm.jar ---> <JAVA_HOME>/java/jre/lib
Copy rxtxSerial.dll ---> <JAVA_HOME>\jre\bin
Copy rxtxParallel.dll ---> <JAVA_HOME>\jre\bin



이클립스트에서도 Add External JAR 를 해야 한다. (RXTXcomm.jar)



>Test.java

```java
package com.can;

import java.io.IOException;

import gnu.io.CommPortIdentifier;

public class Test  {

	private CommPortIdentifier portIdentifier;

	// private boolean start = false;

	public Test(String portName, boolean mode) {

		try {
			if (mode == true) {
				portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				System.out.printf("Port Connect : %s\n", portName);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public static void main(String args[]) throws IOException {
		// 컴퓨터 포트 Num
		Test ss = new Test("COM5", true);
	
	}

}

```



> SendAndRecevieSerial.java

```java
package com.can;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SendAndReceiveSerial implements SerialPortEventListener {
	private BufferedInputStream bin;
	private InputStream in;
	private OutputStream out;
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	private String result;
	private String rawCanID, rawTotal;
	// private boolean start = false;

	public SendAndReceiveSerial(String portName, boolean mode) {

		try {
			if (mode == true) {
				portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				System.out.printf("Port Connect : %s\n", portName);
				connectSerial();
				// Serial Initialization ....
				(new Thread(new SerialWriter())).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void connectSerial() throws Exception {

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			commPort = portIdentifier.open(this.getClass().getName(), 5000);
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
				serialPort.setSerialPortParams(921600, // ��żӵ�
						SerialPort.DATABITS_8, // ������ ��Ʈ
						SerialPort.STOPBITS_1, // stop ��Ʈ
						SerialPort.PARITY_NONE); // �и�Ƽ
				in = serialPort.getInputStream();
				bin = new BufferedInputStream(in);
				out = serialPort.getOutputStream();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public void sendSerial(String rawTotal, String rawCanID) {
		this.rawTotal = rawTotal;
		this.rawCanID = rawCanID;
		// System.out.println("send: " + rawTotal);
		try {
			// Thread.sleep(50);
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread sendTread = 
				new Thread(new SerialWriter(rawTotal));
		sendTread.start();
	}

	private class SerialWriter implements Runnable {
		String data;

		public SerialWriter() {
			// 최초에 한번 쏴주어야 한다. Can 프로토콜에 참여하겠다는 뜻. Can 메시지 시작은 :  끝나는 부분은 \r  로  정해져있다.
			this.data = ":G11A9\r";
		}

		public SerialWriter(String serialData) {
			// CheckSum Data ����
			this.data = sendDataFormat(serialData);
		}

		public String sendDataFormat(String serialData) {
			serialData = serialData.toUpperCase();
			char c[] = serialData.toCharArray();
			int cdata = 0;
			for (char cc : c) {
				cdata += cc;
			}
			cdata = (cdata & 0xFF);

			String returnData = ":";
			returnData += serialData + Integer.toHexString(cdata).toUpperCase();
			returnData += "\r";
			return returnData;
		}

		public void run() {
			try {

				byte[] inputData = data.getBytes();

				out.write(inputData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	

	
	// Asynchronized Receive Data
	// --------------------------------------------------------

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[128];

			try {

				while (bin.available() > 0) {
					int numBytes = bin.read(readBuffer);
				}

				String ss = new String(readBuffer);
				System.out.println("Receive Low Data:" + ss + "||");

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	

	

	public void close() throws IOException {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (commPort != null) {
			commPort.close();
		}

	}

	

	public static void main(String args[]) throws IOException {

		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM6", true);
		ss.sendSerial("W2810003B010000000000005011", "10003B01");
		//ss.close();

	}

}
```

