package com.example.p533;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

    private void getData(){
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20201001";
        MovieAsync movieAsync = new MovieAsync();
        movieAsync.execute(url);
    } //getData end

    class MovieAsync extends AsyncTask<String ,Void,String>{

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
                for(int i =0; i < dailyBoxOfficeList.length(); i++){
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
//                    String audiCnt = jo.getString("audiCnt");
//                    String audiInten = jo.getString("audiInten");
//                    String audiChange = jo.getString("audiChange");
                    String audiAcc = jo.getString("audiAcc");
//                    String scrnCnt = jo.getString("scrnCnt");
//                    String showCnt = jo.getString("showCnt");


                    Movie movie = new Movie(rank,movieNm,audiAcc);
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
                    builder.setMessage(list.get(position).getRank());
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
    class MovieAdapter extends BaseAdapter{

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
                view = inflater.inflate(R.layout.movie,container,true);
                TextView textView1 = view.findViewById(R.id.textView);
                TextView textView2 = view.findViewById(R.id.textView2);
                ImageView imageView = view.findViewById(R.id.imageView);

                textView1.setText(list.get(position).getMovieNm());
                textView2.setText(list.get(position).getAudiAcc()+"명");
                imageView.setImageResource(R.drawable.d2);

                return view;
        } //getView end
    } // MovieAdapter end

} // class end

