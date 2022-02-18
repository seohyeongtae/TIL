package com.example.p500;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    LinearLayout container;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        list = new ArrayList<>();

        getData();

    }
    // Server 에서 데이터를 가지고 온다.
    private void getData() {
        String url ="http://192.168.1.107:8000/android/items.jsp";
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
    }

    // 데이터 요청 Thread 로 진행해야한다.
    class ItemAsync extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 데이터가 받는 동안 progressDialog 띄운다움 데이터가 다내려오면 꺼지게 만든다. onPostExecute 에서
            progressDialog = new ProgressDialog(SecondActivity.this);
            progressDialog.setTitle("Get Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            // JSon 데이터 받아오기.
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
            // 받아온 데이터가 들어오는 곳
            progressDialog.dismiss();
            // 받아온 Json 데이터를 JsonArray 로 만든다.
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for (int i = 0; i <ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("name");
                    String id = jo.getString("id");
                    int age = jo.getInt("age");
                    String img = jo.getString("img");
                    String level = jo.getString("level");
                    Item item = new Item(id,name,age,img,level);
                    list.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ItemAdapter itemAdapter = new ItemAdapter();
            listView.setAdapter(itemAdapter);

        }

    } // AsyncTask end

    // 만들어진 jsonArray List 를 listview에다가 세팅
    class ItemAdapter extends BaseAdapter{

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
            View itemview = null;
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemview = inflater.inflate(R.layout.item,container,true);
            TextView tx_id  = itemview.findViewById(R.id.textView);
            TextView tx_name  = itemview.findViewById(R.id.textView2);
            TextView tx_age  = itemview.findViewById(R.id.textView3);

            tx_id.setText(list.get(position).getId());
            tx_name.setText(list.get(position).getName());
            // age 는 int 이기 때문에 +""  을 꼭 해주어야 한다.
            tx_age.setText(list.get(position).getAge()+"");

            // level 에 따라 골드,실버,브론즈 이미지 넣기
//            ImageView imageView2 = itemview.findViewById(R.id.imageView2);
//            String level = list.get(position).getLevel();
//            if(level.equals("gold")){
//                imageView2.setImageResource(R.drawable.gold);
//            }else if(level.equals("silver")){
//                imageView2.setImageResource(R.drawable.silver);
//            }else if(level.equals("bronze")){
//                imageView2.setImageResource(R.drawable.bronze);
//            }

            // 이미지 다운로드 및 ImageView 세팅
            final ImageView imageView = itemview.findViewById(R.id.imageView);
            String img = list.get(position).getImg();
            final String url = "http://192.168.1.107:8000/android/img/";

            // nework 를 이용하여 데이터를 받으면 무조건 Thread 를 이용해야 한다.
            GetImg t1 = new GetImg(img,url,imageView);
            t1.start();

            // level 이미지 가지고오기
            final ImageView imageView2 = itemview.findViewById(R.id.imageView2);
            String level = list.get(position).getLevel();
            String rimg = null;
            if(level.equals("gold")){
                rimg = "gold.jpg";
            }else if(level.equals("silver")){
                rimg = "silver.jpg";
            }else if(level.equals("bronze")){
                rimg = "bronze.jpg";
            }
            GetImg t2 = new GetImg(rimg,url,imageView2);
            t2.start();

            return itemview;
        } // getView end

        class GetImg extends Thread{
            String img;
            String url;
            ImageView imageView;
            public GetImg(String img,String url, ImageView imageView){
                this.img = img;
                this.url = url;
                this.imageView = imageView;
            }

            @Override
            public void run() {
                URL httpurl = null;
                InputStream is = null;
                try {
                    httpurl = new URL(url+img);
                    is = httpurl.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    // 서브 thread 에서는 mainthread 의 UI를 그냥 바꿀 수없어서 Runnable 사용
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
           }



    } // Adapter end


} // class end