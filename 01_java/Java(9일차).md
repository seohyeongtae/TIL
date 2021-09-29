## QR 코드 생성(Html), QR 코드 스캔(Android)



> mani.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>


<style>

#msg {
	text-align : left;
	border: 2px solid gray;
}
</style>

<script>

function display(datas){
	$('msg').append(datas)
};


/* function msg(){
	$.ajax({
		url:'/getmsg.mc',
		async : false,
		dataType : "json",
		success:function(data){
			display(data)
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
			alert("안돼냐");
		}
	});
	
}; */


$(document).ready(function(){
	
	 var gqrapi = "http://chart.apis.google.com/chart?cht=qr&chs=144x144&choe=UTF-8&chld=H|0";
	
	
	
	
	$('#iot').click(function(){
		$.ajax({
			url:'iot.mc',
			success:function(data){
				alert('Send Complete...')
			/* 	msg(); */
			}
		});
	});
	$('#phone').click(function(){
		$.ajax({
			url:'phone.mc',
			success:function(data){
				alert('Send Complete...')
			}
		});
	});
	$('#controllcan').click(function(){
		$.ajax({
			url:'controllcan.mc',
			success:function(data){
				alert('Send Complete...')
			}
		});
	});
	$('#msgb').click(function(){
	$.ajax({
		url:'getmsg.mc',
		async : false,
		dataType : "json",
		success:function(data){
			display(data)
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
			alert("안돼냐");
		}
	});
	});
	
	
	
	$("#gobtn").click(function () {
		  $text = $("#text").val();

		//입력한 내용 있는지 체크
		  if ($text !== "") {
		   var imgsrc = gqrapi+"&chl="+encodeURIComponent($text); //입력 데이터 인코딩해서 구글 API에 파라메터로 붙이고...

		//이미지 객체를 생성해서 구글API URL을 "src="로 지정

		   var img = new Image();
		   $(img).load(function (){
		    var $this = $(this);
		    $this.hide();
		    $("#qrout").empty().append(this); //<div></div>에 이미지 객체를 덧붙이고...

		    $this.fadeIn(); //블로그에 붙이고 수정하다 잘라먹었습니다. 실행안되시는분은 이줄이 빠져서 만들어진 QR코드가 화면에 안보이는겁니다.

		   }).attr({"src" : imgsrc, "width" : 144, "height" : 144, "alt" : "QR CODE: "+$("#text").val()}); //이미지 객체의 속성 지정 "src"에 구글API URL 지정
		  } else {
		   $("#qrout").empty().text("인코딩할 데이터를 입력하세요.");
		  }
		 });


	
	
	
	
	
});

</script>


</head>
<body>

<h1>Main Page</h1>
<h2><a id="iot" href="#">Send IoT(TCP/IP)</a></h2>
<h2><a id="phone" href="#">Send Phone(FCM)</a></h2>
<h2><a id="msgb" href="#">msgbt</a></h2>
<h2><a id="controllcan" href="#">Can on</a></h2>

<div id ='msg'></div>

<div id="cont">
 <form method="get" action="none">
  <fieldset>
  <legend>QR Code 생성</legend>
  <label for="text">인코딩할 데이터: </label>
  <input type="text" name="text" id="text" size="100" />
  <input type="button" name="go" id="gobtn" value="QR Code 생성" />
  </fieldset>
 </form>
 <div id="qrout"></div>
</div>



</body>
</html>
```



> Android MainActivity (layout에는 버튼만 추가했음)

```java
package com.example.tcpip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.msg.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView tx_list, tx_msg;
    EditText et_ip, et_msg;

    int port;
    String address;
    String id;
    Socket socket;

    Sender sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_list = findViewById(R.id.tx_list);
        tx_msg = findViewById(R.id.tx_msg);
        et_ip = findViewById(R.id.et_ip);
        et_msg = findViewById(R.id.et_msg);
        port = 5555;
        address = "192.168.1.107";
        id = "[SeoPhone]";
        // Thread 를 통해서 connect 시작
        new Thread(con).start();

    } // onCreate end

    public void ckqr(View v){
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                // todo
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    // App 종료(뒤로가기로 껐을 때) App을 완전히 죽이고 Server 접속해제
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            Msg msg = new Msg(null,id,"q");
            sender.setMsg(msg);
            new Thread(sender).start();
            if(socket != null){
                socket.close();
            }
            // App을 완전히 죽이지 않으면 뒤로가기 눌러도 접속해제 됐다가 자동으로 다시 Server에 접속함
            onDestroy();
            finish();
        }catch(Exception e){

        }
    }

    // connect 는 꼭 Thread 처리를 해야 한다. --------------------
    Runnable con = new Runnable() {
        @Override
        public void run() {
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void connect()  throws IOException {
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
        getList();
    } // connect end

    public void getList(){
        Msg msg = new Msg(null,"[SeoPhone]","1");
        sender.setMsg(msg);
        new Thread(sender).start();
    } //getList end

    public  void clickBt(View v){
        String txip = et_ip.getText().toString();
        String txmsg = et_msg.getText().toString();
        Msg msg = null;
        if (txip == null || txip.equals("")){
            msg= new Msg(id,txmsg);
        }else{
            ArrayList<String> ips = new ArrayList<>();
            ips.add(txip);
            ips.add("/192.168.1.46");
            msg = new Msg(ips,id+"[귓속말]"+"["+txip+" 에게]",txmsg);
        }
        sender.setMsg(msg);
        et_msg.setText("");
        new Thread(sender).start();
    } // clickBt end

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
                        for(final String k : keys) {
                            // 서브 쓰레드에서는 메인 쓰레드의 UI 를 변경할 수 없으므로 runOnUiThread를 사용해야 한다.
                            // 현재 접속중인 Client ip 정보 뿌리기
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String tx = tx_list.getText().toString();
                                    tx_list.setText(tx+k+"\n");
                                }
                            });
                            System.out.println(k);
                        } // for end
                        continue; // 계속 받아야 하기 때문에 아래를 건너 뛰고 다시 실행
                    }
                    final Msg finalMsg = msg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String tx = tx_msg.getText().toString();
                            tx_msg.setText(finalMsg.getId()+" : "+ finalMsg.getMsg()+"\n"+tx);
                        }
                    });
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
} // class end
```



![1](Java(9%EC%9D%BC%EC%B0%A8)/1.jpg)

![2](Java(9%EC%9D%BC%EC%B0%A8)/2.jpg)

![3](Java(9%EC%9D%BC%EC%B0%A8)/3.jpg)

![4](Java(9%EC%9D%BC%EC%B0%A8)/4.jpg)