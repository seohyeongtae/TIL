package com.example.tcpip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        address = "192.168.0.24";
        id = "[SeoPhone]";
        // Thread 를 통해서 connect 시작
        new Thread(con).start();
    } // onCreate end

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
            ips.add("/192.168.0.69");
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