package com.example.user_ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;


public class Fragment1 extends Fragment {

    String usergender;
    SecondActivity s;

    Button infobt;
    Button orderbt;
    Button cartbt;
    Button productbt;
    TextView fcmtx;

    TextView itemtx;
    ImageView itemimage;

    FragmentManager fragmentManager;
    Menu menu;
    item item;

    public Fragment1(SecondActivity s, String usergender) {
        this.s =s;
        this.usergender = usergender;
    }

    // HOME 기능 fragment 각 버튼 클릭 시 해당 fragment 로 이동
    // FCM 메시지 부분은 TextView 로만 구현 완료
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1,container,false);

        infobt = v.findViewById(R.id.infobt);
        orderbt = v.findViewById(R.id.orderbt);
        cartbt = v.findViewById(R.id.cartbt);
        productbt =v.findViewById(R.id.productbt);

        fcmtx = v.findViewById(R.id.fcmtx);

        menu = s.bottomNavigationView.getMenu();
        fragmentManager =s.getSupportFragmentManager();

        fcmtx.setText(usergender+"성 분들을 위한 추천 상품!");

        itemtx = v.findViewById(R.id.itemtx);
        itemimage = v.findViewById(R.id.itemimage);

        infobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_check_24);
                menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);
                fragmentManager.beginTransaction().replace(R.id.framelayout,s.fragment2).commit();
            }
        });

        orderbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_check_24);
                menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);
                fragmentManager.beginTransaction().replace(R.id.framelayout,s.fragment3).commit();
            }
        });

        cartbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_check_24);
                menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);
                fragmentManager.beginTransaction().replace(R.id.framelayout,s.fragment4).commit();
            }
        });

        productbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_check_24);
                fragmentManager.beginTransaction().replace(R.id.framelayout,s.fragment5).commit();
            }
        });

        getData();

        return v;
    } // onCreateView end


    // AWS 에서 getData
    public void getData(){
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/interest/"+usergender;
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
        Log.d("[Log]:","ddde"+"  "+url);
    }

    class ItemAsync extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {

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
        protected void onPostExecute(final String s) {

            // 현재는 items Table 에서 데이터를 가져오기 때문에 그 형식에 맞게 JSon 생성
            JSONObject jo = null;
            try {
                jo = new JSONObject(s);
                String itemname = jo.getString("itemname");
                String price = jo.getString("price");
                String stock = jo.getString("stock");
                String category = jo.getString("category");
                String image = jo.getString("image");

                item = new item(itemname,price,stock,category,image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            itemtx.setText(item.getName());


            String img = item.getImg();
            String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/img/";

            GetImg t1 = new GetImg(img, url);
            t1.start();
        } //onPostExecute end
    } // ItemAsync end
    class GetImg extends Thread {
        String img;
        String url;


        public GetImg(String img, String url) {
            this.img = img;
            this.url = url;
        }

        @Override
        public void run() {
            URL httpurl = null;
            InputStream is = null;
            try {
                httpurl = new URL(url + img);
                is = httpurl.openStream();
                final Bitmap bm = BitmapFactory.decodeStream(is);
                // 서브 thread 에서는 mainthread 의 UI를 그냥 바꿀 수없어서 Runnable 사용
                s.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemimage.setImageBitmap(bm);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } // run end
    } // getImg end


} // class end