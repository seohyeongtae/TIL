package com.example.testpos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView menulist;
    ListView choicelist;
    TextView totalprice;
    Button qrbt;
    Button menubt;
    ArrayList<item> items;
    ArrayList<item> orderitems;
    Array orderarray;
    LinearLayout containorder;

    int ordertotal = 0;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menulist = findViewById(R.id.menulist);
        choicelist = findViewById(R.id.choicelist);
        totalprice = findViewById(R.id.totalprice);
        qrbt = findViewById(R.id.qrbt);
        menubt = findViewById(R.id.menubt);
        containorder = findViewById((R.id.containorder));


        items =new ArrayList<>();
        orderitems = new ArrayList<>();
        int d = items.size();

        getData();
    } // onCreate end


    // 버튼 클릭시 QRcode 생성
    public void creatqr(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dview = layoutInflater.inflate(R.layout.qrcode,(ViewGroup) v.findViewById(R.id.qrlayout));
        ImageView qrimageView = dview.findViewById(R.id.imageView2);


        builder.setView(dview);
        builder.setTitle("QR코드를 인식해주세요");

        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < orderitems.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("name", orderitems.get(i).getName());
                sObject.put("price", orderitems.get(i).getPrice());
                sObject.put("order", orderitems.get(i).getOrder());
                jArray.put(sObject);
            }
            obj.put("id", "id01");
            obj.put("age", "userage");
            obj.put("item", jArray);//배열을 넣음

            Toast.makeText(MainActivity.this,obj.toString(),Toast.LENGTH_SHORT);
            //System.out.println(obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // QRCode 생성
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(obj.toString(), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrimageView.setImageBitmap(bitmap);
        }catch (Exception e){

        }
        builder.show();

    }

    public void getData(){
        String url = "http://192.168.0.31/testpos/items.jsp";
        ItemAsync itemAsync = new ItemAsync();
        itemAsync.execute(url);
        Log.d("[Log]:","ddde"+"  "+url);
    }

    class ItemAsync extends AsyncTask<String,Void,String>{
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
//            super.onPostExecute(s);
//            if (!TextUtils.isEmpty(s) && s!=null) {
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//
//            }
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for(int i =0; i <ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.getString("name");
                    String price = jo.getString("price");
                    String stock = jo.getString("stock");
                    String category = jo.getString("category");
                    String img = jo.getString("image");
                    item item = new item(name,price,stock,category,img,1);
                    items.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ItemAdapter itemAdapter = new ItemAdapter();
            menulist.setAdapter(itemAdapter);

            // menulist 에 있는 메뉴를 클릭 했을 때
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

// 왜 안되는거야 ㄴㅁ어리ㅏㅁ너리ㅏㅁㅇ넒ㄴ얿저대ㅑ겹ㅈ대ㅑ겹재9ㅑㄷ겨ㅐㅑㅂ젹0924801483032럼ㄴ아ㅣㄷ러ㅣㅏㅁㅇ너리ㅏㅁㅇ너리ㅏㅁㄴ어리마;넝리ㅏ
                        orderitems.add(orderitem);
                        if(orderitems.size() == 0){
                            choicelist.setAdapter(orderAdapter);
                            ordertotal += items.get(position).getOrder() * Integer.parseInt(items.get(position).getPrice());
                            totalprice.setText(ordertotal + "");
                        }


                            for(int i = 0; i < orderitems.size()-1; i++){
                                if(orderitems.get(position).getName().equals( orderitems.get(i).getName())){
                                    Toast.makeText(MainActivity.this,"중복되었습니다.",Toast.LENGTH_SHORT);
                                    Log.d("[Log]:","whatname"+"  "+orderitems.get(i).getName());
                                    item orderitem1 = new item(name1, price1, stock1, category1, img1, 1);
                                    orderitems.remove(orderitem1);
                                    break;

                                }else{
                                    Log.d("[Log]:","elsewhatname"+"  "+orderitems.get(i).getName());
                                    choicelist.setAdapter(orderAdapter);
                                    ordertotal += items.get(position).getOrder() * Integer.parseInt(items.get(position).getPrice());
                                    totalprice.setText(ordertotal + "");
                                    break;
                                }
                            }

                    //                    Log.d("[Log]:", "safasdfsafd" + "  " + name1);


                }
            });

        } //onPostExecute end
    } // ItemAsync end

    // choice list 에 클릭한 메뉴 뿌리기
    class OrderAdapter extends BaseAdapter{

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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            choiceView = inflater.inflate(R.layout.order, containorder, true);


                TextView ctx1 = choiceView.findViewById(R.id.ctx1);
                TextView ctx2 = choiceView.findViewById(R.id.ctx2);
                final TextView stock = choiceView.findViewById(R.id.stock);
                Button button = choiceView.findViewById(R.id.button);
                Button button2 = choiceView.findViewById(R.id.button2);
                final String[] num = {"1"};

                    ctx1.setText(orderitems.get(position).getName());
                    ctx2.setText(orderitems.get(position).getPrice());
                    stock.setText(orderitems.get(position).getOrder() + "");




            final int[] total = {Integer.parseInt(orderitems.get(position).getPrice()) * orderitems.get(position).getOrder()};
            final int[] ordernum = {0};

            // 수량 - 버튼 눌렀을때
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ordernum[0] = Integer.parseInt(num[0]);
                        if( ordernum[0] > 0){
                        ordernum[0]--;
                        num[0] = ordernum[0] +"";
                        total[0] -= Integer.parseInt(orderitems.get(position).getPrice())* ordernum[0];
                        orderitems.get(position).setOrder( Integer.parseInt(num[0]));
                        stock.setText(orderitems.get(position).getOrder()+"");
                        ordertotal -= Integer.parseInt(orderitems.get(position).getPrice());
                        totalprice.setText(ordertotal+"");
                    }
                }
            });
            // 수량 + 버튼 눌렀을때
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordernum[0] = Integer.parseInt(num[0]);
                    if (ordernum[0] < Integer.parseInt(orderitems.get(position).stock)){
                        ordernum[0]++;
                        num[0] = ordernum[0] +"";
                        total[0] += Integer.parseInt(orderitems.get(position).getPrice())* ordernum[0];
                        orderitems.get(position).setOrder( Integer.parseInt(num[0]));
                        stock.setText(orderitems.get(position).getOrder()+"");
                        ordertotal += Integer.parseInt(orderitems.get(position).getPrice());
                        totalprice.setText(ordertotal+"");

                    }
                }
            });

            return choiceView;

        }//getview end

    } //orderAdapter end

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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.menu, containorder, true);

            TextView otx1 = itemView.findViewById(R.id.ctx1);
            TextView otx2 = itemView.findViewById(R.id.ctx2);
            TextView otx3 = itemView.findViewById(R.id.otx3);
            otx1.setText(items.get(position).getName());
            otx2.setText("가격: " + items.get(position).getPrice() + "");
            otx3.setText("재고: " + items.get(position).getStock() + "");

            ImageView imageView = itemView.findViewById(R.id.imageView);
            String img = items.get(position).getImg();
            String url = "http://192.168.0.31/testpos/img/";

            GetImg t1 = new GetImg(img, url, imageView);
            t1.start();

            return itemView;
        } // getView end

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
                    runOnUiThread(new Runnable() {
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