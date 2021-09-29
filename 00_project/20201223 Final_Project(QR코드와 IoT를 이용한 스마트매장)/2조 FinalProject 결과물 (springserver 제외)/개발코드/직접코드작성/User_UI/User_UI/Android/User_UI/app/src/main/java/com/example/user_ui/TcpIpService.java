package com.example.user_ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class TcpIpService extends Service {

    int port;
    String address;
    String id;
    Socket socket;

    Sender sender;

    MainActivity mainActivity;

    public TcpIpService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        port = 5555;
        address = "3.35.11.144";
        id = "[USER]";

        new Thread(con).start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }





    // TCP/IP 통신 ===========================


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
                    Thread.sleep(1000);
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
                            mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            System.out.println(k);
                        } // for end
                        continue; // 계속 받아야 하기 때문에 아래를 건너 뛰고 다시 실행
                    }
                    Msg Msg = msg;
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

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
            Log.d("msgsmsgmsg",msg.getMsg());
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


}
