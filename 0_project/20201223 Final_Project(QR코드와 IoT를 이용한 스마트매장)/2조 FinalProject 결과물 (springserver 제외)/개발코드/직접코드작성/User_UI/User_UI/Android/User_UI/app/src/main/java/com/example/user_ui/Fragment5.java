package com.example.user_ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

// 제품안내, 자판기 안내
public class Fragment5 extends Fragment {
    ListView menulist;
    ArrayList<item> items;
    ArrayList<item> orderitems;
    LinearLayout containinfo;

    ArrayList<item> searchlist;
    TextView searchtx;

    ArrayList<item> spinnerlist;

    SecondActivity s1;
    String useridinfo;
    FragmentManager fragmentManager;
    Menu menu;
    Spinner spinner;

    // Admin 통신 -> 자판기 위치안내
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;

    public Fragment5(SecondActivity s1,String useridinfo) {
        this.s1 =s1;
        this.useridinfo = useridinfo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_5, container, false);
        menulist = v.findViewById(R.id.menulist);
        containinfo = v.findViewById((R.id.containinfo));

        items =new ArrayList<>();
        orderitems = new ArrayList<>();

        menu = s1.bottomNavigationView.getMenu();

        getData();

        // 메뉴 검색 기능 추가
        searchtx = v.findViewById(R.id.searchtx);
        searchlist = new ArrayList<>();
        // 검색기능 옆 스피너
        spinner = v.findViewById(R.id.spinner);
        spinnerlist = new ArrayList<>();

        // 스피너 클릭 시
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString().trim();
                spinnermenu(category);
                spinner.setPrompt(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 검색기능
        searchtx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchtx.getText().toString();
                search(text);
            }
        });


        // 자판기 위치안내 구현 ======================================
        bt1 = v.findViewById(R.id.bt1);
        bt2 = v.findViewById(R.id.bt2);
        bt3 = v.findViewById(R.id.bt3);
        bt4 = v.findViewById(R.id.bt4);

        // 과자자판기
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.0.31/Admin_UI/navigation.jsp?id=cate1";
                sendiot(url);
            }
        });
        // 음료수 자판기
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.0.31/Admin_UI/navigation.jsp?id=cate2";
                sendiot(url);
            }
        });
        // 생필품 자판기
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.0.31/Admin_UI/navigation.jsp?id=cate3";
                sendiot(url);
            }
        });
        // 신선식품 자판기
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.0.31/Admin_UI/navigation.jsp?id=cate4";
                sendiot(url);
            }
        });
        return v;
    } // onCreateView end

    // 스피너로 메뉴 선택 시 해당 카테고리 리스트만 출력
    public void spinnermenu (String category){
        items.clear();
        if(category.equals("ALL")){
            items.addAll(spinnerlist);
        } else {
            for(int i =0; i< spinnerlist.size(); i++){
                if(spinnerlist.get(i).getCategory().equals(category)){
                    items.add(spinnerlist.get(i));
                }
            }
        }
        ItemAdapter itemAdapter = new ItemAdapter();
        menulist.setAdapter(itemAdapter);
    } //spinnermenu end


    public void search(String charText){
        // 문자 입력 시 마다 리스트를 지우고 새로 뿌려준다.
        items.clear();
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            items.addAll(searchlist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < searchlist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (searchlist.get(i).getName().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    items.add(searchlist.get(i));
                }
            }
        }
        ItemAdapter itemAdapter = new ItemAdapter();
        menulist.setAdapter(itemAdapter);

    } // search end

    // Admin에 자판기 위치안내 정보 전송
    public void sendiot(String urlcate) {

        WebView webView = new WebView(s1);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(urlcate);
    }

    // AWS 에서 getData
    public void getData(){
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/items";
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
      //  Log.d("[Log]:","ddde"+"  "+url);
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
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for(int i =0; i <ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("itemname");
                    String price = jo.getString("price");
                    String stock = jo.getString("stock");
                    String category = jo.getString("category");
                    String img = jo.getString("image");
                    item item = new item(name,price,stock,category,img,1);
                    items.add(item);
                    // 검색기능 구현을 위해 ArrayList 복사
                    searchlist.add(item);
                    spinnerlist.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ItemAdapter itemAdapter = new ItemAdapter();
            menulist.setAdapter(itemAdapter);

            // menulist 에 있는 메뉴를 클릭 했을 때 choiclist로 이동 (장바구니 개념)
            menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //     OrderAdapter orderAdapter = new OrderAdapter();

                    String name1 = items.get(position).getName();
                    String price1 = items.get(position).getPrice();
                    String stock1 = items.get(position).getStock();
                    String category1 = items.get(position).getCategory();
                    String img1 = items.get(position).getImg();

                    item orderitem = new item(name1, price1, stock1, category1, img1, 1);

                    // 중복 선택 방지 기능 추가해야함
                    orderitems.add(orderitem);

                    for(int i =0; i < orderitems.size()-1; i++) {
                        if (name1 == orderitems.get(i).getName().toString().trim()) {
                            orderitems.remove(orderitem);

                      //      Toast.makeText(s1,"이미 선택하신 메뉴입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                //    choicelist.setAdapter(orderAdapter);

                } //onItemclick end
            });

        } //onPostExecute end
    } // ItemAsync end
    // menu list에 메뉴 목록 뿌리기
    class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = null;
            LayoutInflater inflater = (LayoutInflater) s1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.menu, containinfo, true);

            TextView otx1 = itemView.findViewById(R.id.ctx1);
            TextView otx2 = itemView.findViewById(R.id.ctx2);
            TextView otx3 = itemView.findViewById(R.id.otx3);
            otx1.setText(items.get(position).getName());
            otx2.setText("가격: " + items.get(position).getPrice() + "");
            otx3.setText("재고: " + items.get(position).getStock() + "  "+"카테고리 : "+items.get(position).getCategory());

            ImageView imageView = itemView.findViewById(R.id.imageView);
            String img = items.get(position).getImg();
            String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/img/";

            ItemAdapter.GetImg t1 = new ItemAdapter.GetImg(img, url, imageView);
            t1.start();

            return itemView;
        } // getView end

        // 이미지는 현재 AWS 가 아닌 java Server에서 호출중
        class GetImg extends Thread {
            String img;
            String url;
            ImageView imageView;

            public GetImg(String img, String url, ImageView imageView) {
                this.img = img;
                this.url = url;
                this.imageView = imageView;
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
                    s1.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } // run end
        } // getImg end

    } //Adapter

} //class end