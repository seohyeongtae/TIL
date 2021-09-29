package com.example.user_ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

// 장바구니 리스트 및 QR 코드 생성
public class Fragment4 extends Fragment {

    SecondActivity s;

    Button orbutton;
    TextView textView;
    ListView listView;
    LinearLayout containcart;

    ArrayList<receipts> receiptsitems;


    String useridinfo;

    public Fragment4(SecondActivity s, String useridinfo) {
        this.s = s;
        this.useridinfo = useridinfo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_4,container,false);
        orbutton = v.findViewById(R.id.orbutton);
        listView = v.findViewById(R.id.containercart);
        containcart = v.findViewById(R.id.containcart);
        textView = v.findViewById(R.id.carttx);

        // Toast.makeText(s,"info = "+useridinfo,Toast.LENGTH_SHORT).show();

        receiptsitems = new ArrayList<>();
        getData();

        // 주문하기 버튼 누르면 QR 생성 기존 items Data를 JSon 형식으로 생성
        orbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(s);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dview = layoutInflater.inflate(R.layout.qrcode,(ViewGroup) v.findViewById(R.id.qrlayout));
                ImageView qrimageView = dview.findViewById(R.id.imageView2);

                builder.setView(dview);
                builder.setTitle("QR코드를 인식해주세요");

//                JSONObject obj = new JSONObject();
//                try {
//                    JSONArray jArray = new JSONArray();//배열이 필요할때
//                    for (int i = 0; i < receiptsitems.size(); i++)//배열
//                    {
//                        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
//                        sObject.put("itemname", receiptsitems.get(i).getItemname());
//                        sObject.put("price", receiptsitems.get(i).getPrice());
//                        sObject.put("quantity", receiptsitems.get(i).getQuantity());
//                        sObject.put("category", receiptsitems.get(i).getCategory());
//                        jArray.put(sObject);
//                    }
//                    // id, age 등 사용자 정보는 따로 Object 로 전송
//                    obj.put("id", useridinfo);
//                    obj.put("age", "userage");
//                    obj.put("item", jArray);//배열을 넣음
//
//                    // Toast.makeText(s,obj.toString(),Toast.LENGTH_SHORT).show();
//                    //System.out.println(obj.toString());
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                // QRCode 생성
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    Hashtable hints = new Hashtable();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

                    BitMatrix bitMatrix = multiFormatWriter.encode(useridinfo.toString(), BarcodeFormat.QR_CODE,200,200, hints);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrimageView.setImageBitmap(bitmap);
                }catch (Exception e){

                }
                builder.show();

            }
        });

        return v;
    }

    // AWS 에서 우선 items 데이터 호출 (cartlist 데이터 구축시 그 데이터 호출)
    public void getData(){
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/receipts/"+useridinfo+"/수령대기";
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
     //   Log.d("[Log]:","ddde"+"  "+url);
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
        protected void onPostExecute(final String s1) {

            // Toast.makeText(s,s1,Toast.LENGTH_SHORT).show();
            JSONArray ja = null;
            try {
                ja = new JSONArray(s1);
                for(int i =0; i <ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);

                    String orderid = jo.getString("orderid");
                    String orderdate = jo.getString("orderdate");
                    String itemname = jo.getString("itemname");
                    int price = jo.getInt("price");
                    String category = jo.getString("category");
                    String image = jo.getString("image");
                    int quantity = jo.getInt("quantity");
                    String status = jo.getString("status");
                    receipts receipts = new receipts(orderid,orderdate,itemname,price,category,image,quantity,status);
                    receiptsitems.add(receipts);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ItemAdapter itemAdapter = new ItemAdapter();
            listView.setAdapter(itemAdapter);

            // list 메뉴 클릭하여 주문취소 (환불)
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(s);
                    builder.setTitle("주문취소 (환불)");
                    builder.setMessage("해당 메뉴를 환불 처리 하시겠습니까?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ItemAdapter itemAdapter = new ItemAdapter();

                            String delorderid = receiptsitems.get(position).getOrderid();
                            String delitemname = receiptsitems.get(position).getItemname();
                            int delquantity = receiptsitems.get(position).getQuantity();
                            delItem(delorderid,delitemname,delquantity);

                            int delprice = receiptsitems.get(position).getPrice();
                            int totaldelprice = delquantity*delprice;

                            refund(useridinfo,delitemname,delquantity);

                            updateBalance(totaldelprice);
                            receiptsitems.remove(position);
                            getData();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        } //onPostExecute end
    } // ItemAsync end

    // 환불시 DB 데이터 삭제
    public void delItem(String id, String name,int quantity){
        String delorderid = id;
        String delitemname = name;
        int delquantity = quantity;
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/orderlist/"+delorderid;
        JSONObject jsonObjectdel = new JSONObject();
        try {
            jsonObjectdel.put("itemname", delitemname);
         //   jsonObjectdel.put("quantity", delquantity);
        }catch (JSONException e){

        }
        new DelTask().execute(url, jsonObjectdel.toString());

    } // delItem end

    // 환불 DB 추가
    public void refund(String userid, String item, int qul){

        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/refund/";
        JSONObject jsonObjectrefund = new JSONObject();
        try {
            jsonObjectrefund.put("userid", userid);
            jsonObjectrefund.put("itemname", item);
            jsonObjectrefund.put("quantity", qul);
        }catch (JSONException e){

        }
        new RefundTask().execute(url, jsonObjectrefund.toString());
    }


    // 환불시 잔액 갱신
    public void updateBalance(int balance){
        int totaldelprice = balance;
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
        JSONObject jsonObjectbalnace = new JSONObject();
        try {
            jsonObjectbalnace.put("balance", totaldelprice);
        }catch (JSONException e){

        }
        new BalanceTask().execute(url, jsonObjectbalnace.toString());
    }

    // OrderList 삭제 (환불)
    private class DelTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectDel.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    // 환불 DB 추가
    private class RefundTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectUtil.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    // 환불시 잔액 ++
    private class BalanceTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectPut.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    // ListView 의 정보 출력
    class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return receiptsitems.size();
        }

        @Override
        public Object getItem(int position) {
            return receiptsitems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = null;
            LayoutInflater inflater = (LayoutInflater) s.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.cart, containcart, true);

            TextView otx1 = itemView.findViewById(R.id.ctx1);
            TextView otx2 = itemView.findViewById(R.id.ctx2);
            TextView otx3 = itemView.findViewById(R.id.otx3);
            TextView otx4 = itemView.findViewById(R.id.ctx3);
            otx1.setText(receiptsitems.get(position).getItemname());
            otx2.setText("가격: " + receiptsitems.get(position).getPrice() + "");
            otx3.setText("수량: " + receiptsitems.get(position).getQuantity() + "");
            otx4.setText("주문날짜: "+receiptsitems.get(position).getOrderdate() + "");

            ImageView imageView = itemView.findViewById(R.id.imageView);
            String img = receiptsitems.get(position).getImage();
            String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/img/";

            GetImg t1 = new GetImg(img, url, imageView);
            t1.start();

            return itemView;
        } // getView end

            // 현재 이미지는 java Server 에서 호출 중
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
                    s.runOnUiThread(new Runnable() {
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

} // class end