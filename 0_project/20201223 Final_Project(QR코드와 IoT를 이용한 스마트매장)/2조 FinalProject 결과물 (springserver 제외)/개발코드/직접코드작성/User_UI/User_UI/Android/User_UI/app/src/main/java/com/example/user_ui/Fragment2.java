package com.example.user_ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    SecondActivity s1;
    String useridinfo;
    String username;

    Button updatebt;
    TextView idtx;
    TextView nametx;
    TextView balnacetx;

    FragmentManager fragmentManager;

    HttpAsync httpAsync;
    USERS user;

    ListView buylist;
    LinearLayout containbuy;
    ArrayList<receipts> receipts;
    // 회원정보
    public Fragment2(SecondActivity s1,String useridinfo, String username) {
        this.s1 =s1;
        this.useridinfo = useridinfo;
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2,container,false);

        updatebt = v.findViewById(R.id.updatebt);
        idtx = v.findViewById(R.id.idtx);
        nametx = v.findViewById(R.id.nametx);
        balnacetx = v.findViewById(R.id.balancetx);
        buylist = v.findViewById(R.id.buylist);
        containbuy = v.findViewById(R.id.containbuy);

        receipts = new ArrayList<>();

        fragmentManager =s1.getSupportFragmentManager();

        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
        // Data 불러오기 및 로그인은 이후 구성
        // Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

        updatebt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fragmentManager.beginTransaction().replace(R.id.framelayout,s1.fragment6).commit();
           }
       });

       getData();

        return v;
    } // onCreateView end

    // 회원 정보 가지고 오기
    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 데이터가 받는 동안 progressDialog 띄운다움 데이터가 다내려오면 꺼지게 만든다. onPostExecute 에서
            progressDialog = new ProgressDialog(s1);
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
            progressDialog.dismiss();
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
            idtx.setText(user.getUserid());
            nametx.setText(username);
            balnacetx.setText(user.getBalance()+" 원");
        }
    }// HttpAsync end

    // 구매내역 불러오기 (수령완료)
    // AWS 에서 getData
    public void getData(){
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/receipts/"+useridinfo+"/수령완료";
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
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String orderid = jo.getString("orderid");
                    String orderdate = jo.getString("orderdate");
                    String itemname = jo.getString("itemname");
                    int price = jo.getInt("price");
                    String category = jo.getString("category");
                    String image = jo.getString("image");
                    int quantity = jo.getInt("quantity");
                    String status = jo.getString("status");
                    receipts receipt = new receipts(orderid, orderdate, itemname, price, category, image, quantity, status);
                    receipts.add(receipt);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ItemAdapter itemAdapter = new ItemAdapter();
            buylist.setAdapter(itemAdapter);


        } //onPostExecute end
    } // ItemAsync end
    // menu list에 메뉴 목록 뿌리기
    class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return receipts.size();
        }

        @Override
        public Object getItem(int position) {
            return receipts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = null;
            LayoutInflater inflater = (LayoutInflater) s1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.buy, containbuy, true);

            TextView buydate = itemView.findViewById(R.id.buydate);
            TextView buyname = itemView.findViewById(R.id.buyname);
            TextView buystock = itemView.findViewById(R.id.buystock);
            buydate.setText(receipts.get(position).getOrderdate());
            buyname.setText("제품명: " + receipts.get(position).getItemname());
            buystock.setText("구매수량: " + receipts.get(position).getQuantity() + "");

            return itemView;
        } // getView end

    } //Adapter end




} // class end