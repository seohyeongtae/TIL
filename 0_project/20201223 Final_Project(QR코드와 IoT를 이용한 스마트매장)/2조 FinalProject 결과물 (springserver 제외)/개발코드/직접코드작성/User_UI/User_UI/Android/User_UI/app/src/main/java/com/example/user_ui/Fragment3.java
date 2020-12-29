package com.example.user_ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


// Menulist 및 결제하기 부분
public class Fragment3 extends Fragment {
    ListView menulist;
    ListView choicelist;
    TextView totalprice;
    Button paybt;
    ArrayList<item> items;
    ArrayList<item> orderitems;
    LinearLayout containorder;

    ArrayList<item> searchlist;
    TextView searchtx;

    ArrayList<item> spinnerlist;

    SecondActivity s1;
    String useridinfo;
    FragmentManager fragmentManager;
    Menu menu;
    Spinner spinner;

    USERS user;
    HttpAsync httpAsync;

    // 총 금액 나타내기 위해
    int ordertotal;

    public Fragment3(SecondActivity s1,String useridinfo) {
        this.s1 =s1;
        this.useridinfo = useridinfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3,container,false);

        menulist = v.findViewById(R.id.menulist);
        choicelist = v.findViewById(R.id.choicelist);
        totalprice = v.findViewById(R.id.totalprice);
        paybt = v.findViewById(R.id.paybt);
        containorder = v.findViewById((R.id.containorder));
        ordertotal = 0;

        items =new ArrayList<>();
        orderitems = new ArrayList<>();

        menu = s1.bottomNavigationView.getMenu();
        fragmentManager =s1.getSupportFragmentManager();

        user = new USERS();
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
        // Data 불러오기 및 로그인은 이후 구성
        // Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

        // 결제 버튼 클릭시 fragment4(장바구니)로 이동 및 하단 아이콘 변경
        paybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(s1);
                builder.setTitle("결제하기");
                builder.setMessage("결제하시겠습니까?\n"+"현재 잔액 : " +user.getBalance()+" 원");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(user.getBalance() < ordertotal){
                            Toast.makeText(s1,"잔액이 부족합니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(s1,"결제가 완료되었습니다",Toast.LENGTH_LONG).show();
                        menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                        menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                        menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                        menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_check_24);
                        menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);
                        fragmentManager.beginTransaction().replace(R.id.framelayout,s1.fragment4).commit();

                        // 데이터 보내는 거 구현해야 한다. =========================================================
                        sendData();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        // AWS 에서 cartlist 불러오기 위해 (지금은 그냥 전체 items 테이블 호출)
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


    // AWS 에서 getData
    public void getData(){
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/items";
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
                    OrderAdapter orderAdapter = new OrderAdapter();

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
                        ordertotal -= items.get(position).getOrder() * Integer.parseInt(items.get(position).getPrice());
                        Toast.makeText(s1,"이미 선택하신 상품입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    choicelist.setAdapter(orderAdapter);
                    ordertotal += items.get(position).getOrder() * Integer.parseInt(items.get(position).getPrice());
                    totalprice.setText("총 결제금액: "+ordertotal + "원");

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
            itemView = inflater.inflate(R.layout.menu, containorder, true);

            TextView otx1 = itemView.findViewById(R.id.ctx1);
            TextView otx2 = itemView.findViewById(R.id.ctx2);
            TextView otx3 = itemView.findViewById(R.id.otx3);
            otx1.setText(items.get(position).getName());
            otx2.setText("가격: " + items.get(position).getPrice() + "");
            otx3.setText("재고: " + items.get(position).getStock() + "");

            ImageView imageView = itemView.findViewById(R.id.imageView);
            String img = items.get(position).getImg();
            String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/img/";

            GetImg t1 = new GetImg(img, url, imageView);
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

    } //Adapter end


    // choice list 에 menu list 에서 클릭한 메뉴 뿌리기
    class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderitems.size();
        }

        @Override
        public Object getItem(int position) {
            return orderitems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        // orderlist View -> 메뉴 선택시
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View choiceView = null;
            LayoutInflater inflater = (LayoutInflater) s1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            choiceView = inflater.inflate(R.layout.order, containorder, true);
            final OrderAdapter orderAdapter = new OrderAdapter();


            TextView ctx1 = choiceView.findViewById(R.id.ctx1);
            TextView ctx2 = choiceView.findViewById(R.id.ctx2);
            final TextView stock = choiceView.findViewById(R.id.stock);
            Button button = choiceView.findViewById(R.id.button);
            final Button button2 = choiceView.findViewById(R.id.button2);
            final Button delete = choiceView.findViewById(R.id.delete);


            ctx1.setText(orderitems.get(position).getName());
            ctx2.setText(orderitems.get(position).getPrice());
            stock.setText(orderitems.get(position).getOrder() + "");


            // 수량 - 버튼 눌렀을때 item vo 에 order 수량 변경
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 구매수량
                    int number = orderitems.get(position).getOrder();
                    // 제품 가격
                    if(number > 1){
                       number --;
                       orderitems.get(position).setOrder(number);
                       stock.setText(number+"");
                       ordertotal -= Integer.parseInt(orderitems.get(position).getPrice());
                       totalprice.setText("총 결제금액: "+ordertotal + "원");
                    }else if(number == 1){
                        Toast.makeText(s1,"구매 수량을 1개 이상 선택해주세요",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            // 수량 + 버튼 눌렀을때
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 구매수량
                    int number = orderitems.get(position).getOrder();

                    if (number < Integer.parseInt(orderitems.get(position).stock)){
                        number ++;
                        orderitems.get(position).setOrder(number);
                        stock.setText(number+"");
                        ordertotal += Integer.parseInt(orderitems.get(position).getPrice());
                        totalprice.setText("총 결제금액: "+ordertotal + "원");
                       } else if(number == Integer.parseInt(orderitems.get(position).stock)){
                        Toast.makeText(s1,"자판기 내 재고가 부족합니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

            // 선택상품 삭제했을경우
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(s1);
                    builder.setTitle("선택 상품 삭제");
                    builder.setMessage("선택한 상품을 삭제하시겠습니까?");

                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ordertotal -= orderitems.get(position).getOrder() * Integer.parseInt(orderitems.get(position).getPrice());
                            totalprice.setText("총 결제금액: "+ordertotal + "원");
                            orderitems.remove(position);
                            choicelist.setAdapter(orderAdapter);
                        }
                    });
                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            return choiceView;

        }//getview end

    } //orderAdapter end

    // DB DATA 보내기
    public void sendData() {
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/orderlist/" + useridinfo;
        String url2 = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
        JSONArray jsonArrayitem = new JSONArray();
        JSONObject jsonObjectbalance = new JSONObject();
        try {
            for (int i = 0; i < orderitems.size(); i++) {
                JSONObject jsonObjectitem = new JSONObject();
                jsonObjectitem.put("itemname", orderitems.get(i).getName());
                jsonObjectitem.put("quantity", orderitems.get(i).getOrder());

                jsonArrayitem.put(jsonObjectitem);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            jsonObjectbalance.put("balance",-ordertotal);
        }catch (JSONException e){

        }

        Log.d("log","Array:"+jsonArrayitem.toString());

        // 영수증 DB
        new InsertTask().execute(url, jsonArrayitem.toString());
        // 잔액 차감
        new InsertTask2().execute(url2, jsonObjectbalance.toString());
    }

    // 영수증 등록
    private class InsertTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectUtil.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }
    // 잔액차감
    private class InsertTask2 extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectPut.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }


    // 회원 정보 가지고 오기
    class HttpAsync extends AsyncTask<String,String,String> {


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
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        // return 되는 값이 오는 곳 이클립스에서 out.print 한 값이 들어온다.
        @Override
        protected void onPostExecute(String s) {
            // 데이터가 다 내려오면 progressDialog 종료 후 Login 여부 확인 및 각 코드 실행
            // trim 으로 받은 이유는 혹시모르는 빈 스페이스 공간도 없애기 위해
            JSONObject jo = null;
            try {
                jo = new JSONObject(s);
                String userid = jo.getString("userid");
                String pwd = jo.getString("pwd");
                int balance = jo.getInt("balance");
                String name = jo.getString("name");
                String gender = jo.getString("gender");
                int age = jo.getInt("age");
                user = new USERS(userid,pwd,balance,name,gender,age);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }// HttpAsync end

} // class end