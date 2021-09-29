package com.example.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import java.util.ArrayList;


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