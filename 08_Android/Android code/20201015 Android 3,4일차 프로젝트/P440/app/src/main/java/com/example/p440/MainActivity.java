package com.example.p440;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons;
    ListView listView;
    LinearLayout listlayout;
    TextView tx_info,edit_name,edit_date,edit_phone;

    // 네트워크 환경 연결결 여부확인
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    // ActionBar 생성
   ActionBar actionBar;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listlayout = findViewById(R.id.listLayout);
        tx_info = findViewById(R.id.tx_info);
        edit_name = findViewById(R.id.edit_name);
        edit_date = findViewById(R.id.edit_date);
        edit_phone = findViewById(R.id.edit_phone);

        persons = new ArrayList<Person>();

        //action bar 생성
        actionBar = getSupportActionBar();


        //브로드캐스트

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo wifi = null;

                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if(wifi != null && wifi.isConnected()){
                      Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
                        actionBar.setLogo(R.drawable.wifi);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

                    }else {
                      Toast.makeText(context, "bye", Toast.LENGTH_SHORT).show();
                        actionBar.setLogo(R.drawable.nwifi);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

                    }
                }

            } // onReceive end
        };  // broadcastReceiver end
        registerReceiver(broadcastReceiver,intentFilter);
     } // onCreate end
    // App 이 꺼졌을 때 연결을 끊는다는 것을 반드시 넣어주어야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 반드시 넣어주어야 함.~~~~~~~~~~~~~~~~~~~~
        unregisterReceiver(broadcastReceiver);
    } // onDestroy end


    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            Person p = datas.get(position);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person, listlayout, true);

            TextView listname = view.findViewById(R.id.list_name);
            TextView listdate = view.findViewById(R.id.list_date);
            TextView listphone = view.findViewById(R.id.list_phone);


            listname.setText(p.getName());
            listdate.setText(p.getDate());
            listphone.setText(p.getPhone());


            return view;
        }
    }; // class PersonAdapter end


    public void setList(final ArrayList<Person> persons){
        final PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);

        //list목록 클릭했을 때
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   persons.remove(position);
                   personAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

    } //setlist end

    public void getData(){
        String name = edit_name.getText().toString();
        String date = edit_date.getText().toString();
        String phone = edit_phone.getText().toString();
        persons.add(new Person(name,date,phone));

        setList(persons);
    }

    public void ckbt(View view){
        getData();
    }
} // Class end