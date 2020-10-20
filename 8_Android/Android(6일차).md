## 안드로이드 6일차



### WorkShop P533

1. 로그인 후 다음 페이지로 이동
2. 다음 페이지에서는 영화 정보를 리스트로 출력 한다. 
3. 리스트의 정보를 클릭하면 상세 내용을 AlertDialog 로 출력 한다. 



> API JSON 데이터 가지고 오는법 ( 첫줄에 Object가 적혀있을 때)

```java
public void setJsonData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject boxOfficeResult = jsonObject.getJSONObject("boxOfficeResult");
            JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");

            for (int i = 0; i < dailyBoxOfficeList.length(); i++) {
                JSONObject jsonMovie = dailyBoxOfficeList.getJSONObject(i);

                int rank = jsonMovie.getInt("rank");
                String name = jsonMovie.getString("movieNm");
                String openDate = jsonMovie.getString("openDt");

                movieArrayList.add(new Movie(rank, name, openDate));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

```

> 데이터 예시 첫번째 줄에 데이터에 대한 정보가 적혀 있을 경우 위 코드처럼 Object 안에 Array를 끄집어 내야 한다.

```java
{"boxOfficeResult":{"boxofficeType":"일별 박스오피스","showRange":"20201001~20201001","dailyBoxOfficeList":
                    
                    [{"rnum":"1","rank":"1","rankInten":"0","rankOldAndNew":"OLD","movieCd":"20199816","movieNm":"담보","openDt":"2020-09-29","salesAmt":"1320185090","salesShare":"37.8","salesInten":"471473250","salesChange":"55.6","salesAcc":"2610895850","audiCnt":"146641","audiInten":"48929","audiChange":"50.1","audiAcc":"312490","scrnCnt":"1117","showCnt":"4792"},{"rnum":"2","rank":"2","rankInten":"0","rankOldAndNew":"OLD","movieCd":"20184482","movieNm":"국제수사","openDt":"2020-09-29","salesAmt":"818608850","salesShare":"23.4","salesInten":"48512960","salesChange":"6.3","salesAcc":"2161749770","audiCnt":"91869","audiInten":"2494","audiChange":"2.8","audiAcc":"267697","scrnCnt":"1074","showCnt":"4724"},{"rnum":"3","rank":"3","rankInten":"0","rankOldAndNew":"OLD","movieCd":"20204008","movieNm":"그린랜드","openDt":"2020-09-29","salesAmt":"392334920","salesShare":"11.2","salesInten":"64018050","salesChange":"19.5","salesAcc":"889780810","audiCnt":"44240","audiInten":"5947","audiChange":"15.5","audiAcc":"108524","scrnCnt":"650","showCnt":"1999"},{"rnum":"4","rank":"4","rankInten":"1","rankOldAndNew":"OLD","movieCd":"20201122","movieNm":"테넷","openDt":"2020-08-26","salesAmt":"229318840","salesShare":"6.6","salesInten":"35080350","salesChange":"18.1","salesAcc":"16165746350","audiCnt":"24132","audiInten":"2795","audiChange":"13.1","audiAcc":"1759516","scrnCnt":"464","showCnt":"801"},{"rnum":"5","rank":"5","rankInten":"1","rankOldAndNew":"OLD","movieCd":"20190621","movieNm":"죽지않는 인간들의 밤","openDt":"2020-09-29","salesAmt":"142387260","salesShare":"4.1","salesInten":"-14972570","salesChange":"-9.5","salesAcc":"427461770","audiCnt":"16540","audiInten":"-2180","audiChange":"-11.6","audiAcc":"53232","scrnCnt":"551","showCnt":"1633"},{"rnum":"6","rank":"6","rankInten":"-2","rankOldAndNew":"OLD","movieCd":"20206062","movieNm":"극장판 포켓몬스터 뮤츠의 역습 EVOLUTION","openDt":"2020-09-30","salesAmt":"79228490","salesShare":"2.3","salesInten":"-117756970","salesChange":"-59.8","salesAcc":"276213950","audiCnt":"9738","audiInten":"-14846","audiChange":"-60.4","audiAcc":"34322","scrnCnt":"540","showCnt":"875"},{"rnum":"7","rank":"7","rankInten":"2","rankOldAndNew":"OLD","movieCd":"20178401","movieNm":"검객","openDt":"2020-09-23","salesAmt":"85677260","salesShare":"2.5","salesInten":"23666650","salesChange":"38.2","salesAcc":"1247799390","audiCnt":"9556","audiInten":"2330","audiChange":"32.2","audiAcc":"146380","scrnCnt":"370","showCnt":"559"},{"rnum":"8","rank":"8","rankInten":"0","rankOldAndNew":"OLD","movieCd":"20206826","movieNm":"그대, 고맙소 : 김호중 생애 첫 팬미팅 무비","openDt":"2020-09-29","salesAmt":"254515000","salesShare":"7.3","salesInten":"3250000","salesChange":"1.3","salesAcc":"956505500","audiCnt":"8300","audiInten":"197","audiChange":"2.4","audiAcc":"31237","scrnCnt":"102","showCnt":"225"},{"rnum":"9","rank":"9","rankInten":"-2","rankOldAndNew":"OLD","movieCd":"20206588","movieNm":"극장판 미니특공대: 햄버거괴물의 습격","openDt":"2020-09-30","salesAmt":"39831720","salesShare":"1.1","salesInten":"-27711990","salesChange":"-41","salesAcc":"110462430","audiCnt":"4892","audiInten":"-3520","audiChange":"-41.8","audiAcc":"13647","scrnCnt":"389","showCnt":"486"},{"rnum":"10","rank":"10","rankInten":"0","rankOldAndNew":"OLD","movieCd":"20204684","movieNm":"브레이크 더 사일런스: 더 무비","openDt":"2020-09-24","salesAmt":"28958500","salesShare":"0.8","salesInten":"-17518000","salesChange":"-37.7","salesAcc":"836760500","audiCnt":"3094","audiInten":"-2025","audiChange":"-39.6","audiAcc":"95172","scrnCnt":"139","showCnt":"420"}]}}
```



> HttpConnect.java

```jav
package com.example.p533;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnect {
    public static String getString(String urlstr){
        String result = null;
        URL url = null;
        HttpURLConnection hcon = null;
        InputStream is = null;
        try{
            url = new URL(urlstr);
            hcon = (HttpURLConnection)url.openConnection();
            hcon.setConnectTimeout(2000);
            hcon.setRequestMethod("GET");
            is = new BufferedInputStream(hcon.getInputStream());
            result = convertStr(is);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 받아온 것을 String 으로 바꿔준다.
    public static String convertStr(InputStream is){
        String result = null;
        BufferedReader bi = null;
        StringBuilder sb = new StringBuilder();
        try{
            bi = new BufferedReader(
                    new InputStreamReader(is)
            );
            String temp = "";
            while((temp =bi.readLine()) != null){
                sb.append(temp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

}
```



> MainActivity

```java
package com.example.p533;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tx_id, tx_pwd;
    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);
    } // oncreate end
    public void ckbt(View view){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        String url = "http://192.168.1.107:8000/androidP533/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
    } // ckbt end

    class HttpAsync extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            String result = s.trim();
            if(result.equals("1")){
                progressDialog.dismiss();
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }else if(result.equals("2")){
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Login Fail");
                dialog.setMessage("Try Again");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
          }
    } // httpasync end

}  // class end
```



> SecondActivity

```java
package com.example.p533;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView listView;

    // container = movie.xml listview에 들어가는 container 양식
    LinearLayout container;
    ArrayList<Movie> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        list = new ArrayList<>();

        getData();
    } // onCreate end

    private void getData() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20201001";
        MovieAsync movieAsync = new MovieAsync();
        movieAsync.execute(url);
    } //getData end

    class MovieAsync extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject boxOfficeResult = jsonObject.getJSONObject("boxOfficeResult");
                JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
                for (int i = 0; i < dailyBoxOfficeList.length(); i++) {
                    JSONObject jo = dailyBoxOfficeList.getJSONObject(i);
//                    String rnum = jo.getString("rnum");
                    String rank = jo.getString("rank");
//                    String rankInten = jo.getString("rankInten");
//                    String rankOldAndNew = jo.getString("rankOldAndNew");
//                    String movieCd = jo.getString("movieCd");
                    String movieNm = jo.getString("movieNm");
//                    String openDt = jo.getString("openDt");
//                    String salesAmt = jo.getString("salesAmt");
//                    String salesShare = jo.getString("salesShare");
//                    String salesInten = jo.getString("salesInten");
//                    String salesChange = jo.getString("salesChange");
//                    String salesAcc = jo.getString("salesAcc");
                    String audiCnt = jo.getString("audiCnt");
//                    String audiInten = jo.getString("audiInten");
//                    String audiChange = jo.getString("audiChange");
                    String audiAcc = jo.getString("audiAcc");
//                    String scrnCnt = jo.getString("scrnCnt");
//                    String showCnt = jo.getString("showCnt");


                    Movie movie = new Movie(rank, movieNm, audiCnt, audiAcc);
                    list.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MovieAdapter movieAdapter = new MovieAdapter();
            listView.setAdapter(movieAdapter);

            // ListView 클릭했을때 AlertDialog
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                    builder.setTitle("Rank");
                    builder.setMessage("일별 랭킹 : " + list.get(position).getRank()
                            + "\n 총관객수 : " + list.get(position).getAudiAcc());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });

        } // onPostExecute end

    } // MovieAsync end

    // Adapter를 이용하여 Listview Setting
    class MovieAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie, container, true);
            TextView textView1 = view.findViewById(R.id.textView);
            TextView textView2 = view.findViewById(R.id.textView2);


            textView1.setText(list.get(position).getMovieNm());
            textView2.setText("일별 관객수 : " + list.get(position).getAudiCnt() + "명");


            // Java 로부터 이미지 받아 총 관객수별 등급 이미지로 나타내기
            final ImageView imageView = view.findViewById(R.id.imageView);
            String img = null;
            if (Integer.parseInt(list.get(position).getAudiAcc()) >= 1000000) {
                img = "up.jpg";
            } else if (Integer.parseInt(list.get(position).getAudiAcc()) >= 100000) {
                img = "middle.jpg";
            } else {
                img = "down.jpg";
            }

            final String iurl = "http://192.168.0.24/androidP533/img/" + img;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL httpurl = null;
                    InputStream is = null;
                    try {
                        httpurl = new URL(iurl);
                        is = httpurl.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bm);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            return view;
        } //getView end
    } // MovieAdapter end


 } // class end

```



> Movie.java

```java
package com.example.p533;

public class Movie {
    String rnum;
    String rank;
    String rankInten;
    String rankOldAndNew;
    String movieCd;
    String movieNm;
    String openDt;
    String salesAmt;
    String salesShare;
    String salesInten;
    String salesChange;
    String salesAcc;
    String audiCnt;
    String audiInten;
    String audiChange;
    String audiAcc;
    String scrnCnt;
    String showCnt;

    public Movie() {
    }
    public Movie(String movieNm) {
        this.movieNm = movieNm;

    }

    public Movie(String rank, String movieNm, String audiCnt, String audiAcc) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.audiCnt = audiCnt;
        this.audiAcc = audiAcc;
    }

    public Movie(String rnum, String rank, String rankInten, String rankOldAndNew, String movieCd, String movieNm, String openDt, String salesAmt, String salesShare, String salesInten, String salesChange, String salesAcc, String audiCnt, String audiInten, String audiChange, String audiAcc, String scrnCnt, String showCnt) {
        this.rnum = rnum;
        this.rank = rank;
        this.rankInten = rankInten;
        this.rankOldAndNew = rankOldAndNew;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.salesAmt = salesAmt;
        this.salesShare = salesShare;
        this.salesInten = salesInten;
        this.salesChange = salesChange;
        this.salesAcc = salesAcc;
        this.audiCnt = audiCnt;
        this.audiInten = audiInten;
        this.audiChange = audiChange;
        this.audiAcc = audiAcc;
        this.scrnCnt = scrnCnt;
        this.showCnt = showCnt;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getRank() {
        return rank;
    }

			//    <중략>
```



> Java Web Dynamic Project   login.jsp

```java
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	System.out.println(id +" "+ pwd);
	Thread.sleep(3000);
	if (id.equals("id01") && pwd.equals("pwd01")) {
		out.print("1");
	} else {
		out.print("2");
	}
	
%>
```





### Android Date 처리 (Calender 만들고 date처리)

> MainActivty 내부에 들어가는 함수

```java
long d = calendarView.getDate();

SimpleDateFormat timeStampFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss",

Locale.KOREA);

String date = timeStampFormat.format(new Timestamp(d));

textView.setText(date);
```



> cal.xml

```java
<?xml version="1.0" encoding="utf-8"?>

<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"

xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
    
android:id="@+id/dlayout"
android:layout_width="wrap_content"
android:layout_height="wrap_content">

<CalendarView
android:id="@+id/calendarView"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>

```



> MainAcitvity

```java
package com.example.p533;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    } // onCreate end

    public void ckbt(View v){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);

        LayoutInflater layoutInflater = getLayoutInflater();
        View dview = layoutInflater.inflate(R.layout.cal,
                (ViewGroup) findViewById(R.id.dlayout) );

        final CalendarView calendarView = dview.findViewById(R.id.calendarView);

        builder.setView(dview);
        builder.setTitle("Hi");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                long d = calendarView.getDate();
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss",
                        Locale.KOREA);
                String date = timeStampFormat.format(new Timestamp(d));
                textView.setText(date);
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    } //ckbt end
}

```

