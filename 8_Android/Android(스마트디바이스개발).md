## Android 수행평가 10월 23일 오전 까지 진행 / 23일 오후에 제출

### PWS 파일

> Import 생략 / xml Code는 WS  파일 참고할 것 / HttpConnect, MyFService, eclipse 파일 등은 이전 정리 or WS 파일 참고할 것

1.  Project  명 WS
2.  본 과정의 모든 내용을 하나의 앱에 통합 하여 구현
   - UI
   - Fragment
   - Geo Location
   - BroadCast Reciver ,Notification
   - Thread
   - Network (JSon 데이터 뿌리기)
   - FCM
3.  수행평가 문서 작성
   - 기획 내용
   - 시스템 구성도
   - 실행 결과 화면

![Android](Android(%EC%8A%A4%EB%A7%88%ED%8A%B8%EB%94%94%EB%B0%94%EC%9D%B4%EC%8A%A4%EA%B0%9C%EB%B0%9C)/Android.PNG)

![Eclipse](Android(%EC%8A%A4%EB%A7%88%ED%8A%B8%EB%94%94%EB%B0%94%EC%9D%B4%EC%8A%A4%EA%B0%9C%EB%B0%9C)/Eclipse.PNG)

![시스템구성도2](Android(%EC%8A%A4%EB%A7%88%ED%8A%B8%EB%94%94%EB%B0%94%EC%9D%B4%EC%8A%A4%EA%B0%9C%EB%B0%9C)/%EC%8B%9C%EC%8A%A4%ED%85%9C%EA%B5%AC%EC%84%B1%EB%8F%842.PNG)



> MainActivity.java

```java

public class MainActivity extends AppCompatActivity {
    WebView webView;
    TextView status_wifi,login_id, login_pwd;
    Button login_button;
    IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;
    ActionBar actionBar;

    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.login_webView);
        status_wifi = findViewById(R.id.status_wifi);
        login_id = findViewById(R.id.login_id);
        login_pwd = findViewById(R.id.login_pwd);
        login_button = findViewById(R.id.login_button);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Android 수행평가");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        String permission [] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        ActivityCompat.requestPermissions(this,permission,101);

       // wifi 연결유무 확인
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo wifi = null;
                NetworkInfo mobile = null;
                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    // cm = 현재 네트워크 상태
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if(mobile != null && mobile.isConnected()){

                    }else if(wifi != null && wifi.isConnected()){
                        status_wifi.setText("인터넷에 연결중입니다.");
                    }else{
                        status_wifi.setText("인터넷에 연결되어있지 않습니다.");
                    }
                }
            }
        };  // BrodcastReciver end
        registerReceiver(broadcastReceiver,intentFilter);

        // WebView
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://m.cgv.co.kr/");

        //Firebase 메시지
        FirebaseMessaging.getInstance().subscribeToTopic("movie").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "FCM Complete...";
                if(!task.isSuccessful()){
                    msg = "FCM Fail...";
                }
                Log.d("[LOG]:",msg);
            }
        });

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));

    } // onCreate end

    // Firebase 메시지
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                Toast.makeText(MainActivity.this, title+ "  "+ control+" " ,Toast.LENGTH_SHORT).show();
                notification(title,control,data);
            }
        }
    };

    // data 가 들어오면 어플 내부에서 진동 , 상단 알람창 띄움 , 알람 클릭하면 어플 재실행
    public void notification(String title, String control, String data){
        NotificationManager manager;
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(data != null) {
            // 진동
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, 10));
            } else {
                vibrator.vibrate(1000);
            }

            // 알람
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if (Build.VERSION.SDK_INT >= 26) {
                if(manager.getNotificationChannel("ch1") == null) {
                    manager.createNotificationChannel(new NotificationChannel("ch1", "chname1", NotificationManager.IMPORTANCE_DEFAULT));
                }

                builder = new NotificationCompat.Builder(this, "ch1");
            } else {
                builder = new NotificationCompat.Builder(this);
            }
            // 알람 클릭시 어플 재실행 Pending 사용
            Intent intent = new Intent(this, Fragment3.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            builder.setContentTitle(title);
            builder.setContentText(control);
            builder.setSmallIcon(R.drawable.cgv);
            Notification noti = builder.build();
            manager.notify(1, noti);
        }
    } // notification end


    // Login 버튼
    public  void login_bt(View v){
        String id = login_id.getText().toString();
        String pwd = login_pwd.getText().toString();
        String url = "http://192.168.1.107:8000/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
      //  Log.d("[LOG]:",url);
    }

    // 회원정보 데이터 받기
    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 데이터가 받는 동안 progressDialog 띄운다움 데이터가 다내려오면 꺼지게 만든다. onPostExecute 에서
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login...");
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

        // return 되는 값이 오는 곳 이클립스에서 out.print 한 값이 들어온다.
        @Override
        protected void onPostExecute(String s) {
            // 데이터가 다 내려오면 progressDialog 종료 후 Login 여부 확인 및 각 코드 실행
            // trim 으로 받은 이유는 혹시모르는 빈 스페이스 공간도 없애기 위해
            String result = s.trim();

            if (result.equals("1")){
                progressDialog.dismiss();
                // Login COMPLETE SecondActivity 로 전환
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }else if (result.equals("2")){
                // Login Fail
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Login Fail");
                dialog.setMessage("아이디,비밀번호를 확인해 주세요");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            };
        }
    } // HttpAsync end

} // class end
```



> SecondActivity.java

```java

public class SecondActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 상단 actionbar 없애기
        actionBar = getSupportActionBar();
        actionBar.hide();

        fragment1 = new Fragment1(this);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();


        bottomNavigationView = findViewById(R.id.bottomNav);
        menu = bottomNavigationView.getMenu();

        // bottom nav 클릭시 이미지 변경, fragment 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.navbt1){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.location);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.event);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();
                    Toast.makeText(SecondActivity.this, "상영순위", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt2){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.list);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.event);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment2).commit();
                    Toast.makeText(SecondActivity.this, "상영관 위치", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt3){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.list);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.location);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment3).commit();
                    Toast.makeText(SecondActivity.this, "이벤트 정보", Toast.LENGTH_SHORT).show();
                }
                switch (item.getItemId()){
                    case R.id.navbt1:
                }

             return false;
            }
        });


    } // onCreate end

} // class end
```



> Fragment1

```java

public class Fragment1 extends Fragment {
    ListView listView;
    ArrayList<Movie> movies;
    LinearLayout container;
    SecondActivity s;
    TextView list_tx;
    Button list_button;
    CalendarView calendarView;
    String listdate;

    public Fragment1(SecondActivity s) {
        this.s = s;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1,container,false);
        listView = v.findViewById(R.id.listView);
        list_tx = v.findViewById(R.id.list_tx);
        list_button = v.findViewById(R.id.list_button);

        movies = new ArrayList<>();


        list_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // CalenderView 를 통한 날짜 선택, 조회
                AlertDialog.Builder builder = new AlertDialog.Builder(s);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dview = layoutInflater.inflate(R.layout.cal,(ViewGroup) v.findViewById(R.id.dlayout));
                calendarView = dview.findViewById(R.id.calendarView);

                builder.setView(dview);
                builder.setTitle("날짜를 선택해주세요");

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        int dmonth = month+1;
                        String smonth = null;
                        String sday = null;
                        String syear = year+"";
                        if(month < 9){
                            smonth = "0"+dmonth+"";
                        }else{
                            smonth = dmonth+"";
                        }
                        if(dayOfMonth < 10){
                            sday = "0"+dayOfMonth+"";
                        }else {
                            sday = dayOfMonth+"";}
                        String date = syear + smonth + sday;
                        list_tx.setText(date);
                    }
                });
                builder.show();
            }
        });

        // 조회 버튼 클릭시 날짜 getText하여 데이터 조회 cal.xml 사용
        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listdate = list_tx.getText().toString().trim();
               movies.clear();
                getData(listdate);
            }
        });

        return v;

    } // onCreateView end

    private void getData(String listdate){
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt="+listdate;
        MovieAsync movieAsync = new MovieAsync();
        movieAsync.execute(url);
        Log.d("[Log]:","ddde"+"  "+url);
    }

    class MovieAsync extends  AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(s);
            progressDialog.setTitle("Loading...");
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
        protected void onPostExecute(final String s) {

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
                    movies.add(movie);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Rank");
                    builder.setMessage("일별 랭킹 : " + movies.get(position).getRank()
                            + "\n 총관객수 : " + movies.get(position).getAudiAcc()+ " 명");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });
            progressDialog.dismiss();
        } // onPostExecute end


    } // MovieAsync end

    // Json Date를 listView 에 뿌리는 Adapter
    class MovieAdapter extends  BaseAdapter{

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public Object getItem(int position) {
            return movies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = (LayoutInflater) s.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie,container,false);
            TextView textView1 = view.findViewById(R.id.ctx1);
            TextView textView2 = view.findViewById(R.id.ctx2);


            textView1.setText(movies.get(position).getMovieNm());
            textView2.setText("일별 관객수 : " + movies.get(position).getAudiCnt() + "명");
        // Java 로부터 이미지 받아 총 관객수별 이미지로 나타내기
            final ImageView imageView = view.findViewById(R.id.imageView);
            String img = null;
            if (Integer.parseInt(movies.get(position).getAudiAcc()) >= 1000000) {
                img = "100m.jpg";
            } else if (Integer.parseInt(movies.get(position).getAudiAcc()) >= 500000) {
                img = "50m.jpg";
            } else if (Integer.parseInt(movies.get(position).getAudiAcc()) >= 100000) {
                img = "10m.jpg";
            } else { img = "1m.jpg";}
            final String iurl = "http://192.168.1.107:8000/android/img/" + img;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL httpurl = null;
                    InputStream is = null;
                    try {
                        httpurl = new URL(iurl);
                        is = httpurl.openStream();
                        final Bitmap bm = BitmapFactory.decodeStream(is);

                        s.runOnUiThread(new Runnable() {
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
        } // getView end
    } // MovieAdapter end

} // class end
```



> Fragment2

```java
public class Fragment2 extends Fragment {

    MapView gmap;

    public Fragment2() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        gmap = (MapView) v.findViewById(R.id.map);
        gmap.onCreate(savedInstanceState);
        gmap.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        gmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latlng = new LatLng(37.501580, 127.026298);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                googleMap.addMarker(
                        new MarkerOptions().position(latlng).
                                title("CGV").snippet("강남점")
                );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
            }
        });
        return v;
    }
} // class end
```



> Fragment3

```java

public class Fragment3 extends Fragment {

    TextView eventtext1, eventtext2;
    SecondActivity s;
    WebView webView;

    public Fragment3(SecondActivity s) {
        this.s = s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

        // Firebase 로 데이터를 받아 TextView, webView 뿌리기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container,false);
        eventtext1 = v.findViewById(R.id.eventtextView);
        eventtext2 = v.findViewById(R.id.eventtextView2);
        webView = v.findViewById(R.id.eventWeb);

        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(s);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));
        return v;

    }
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                eventtext2.setText(control);
                webView.loadUrl(data);
            }
        }
    };

} // class end
```

