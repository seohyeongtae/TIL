package com.example.p426;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listView Id 세팅
        listView = findViewById(R.id.listView);
    } // onCreate end

    // onItemClick 에서 datas 배열을 사용했기 때문에 final 로 바뀌었다.
    public void setList(final ArrayList<String> datas){
        // 그냥 데이터를 넣는 것이 아니라 ArrayAdapter 를 만든 뒤 list에 넣어야 한다.
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,  // 화면 정보를 넣어줌
                datas
        );
        listView.setAdapter(adapter);

        // listView에 출력된 list를 클릭했을 경우
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //int position 은 몇번째 list인지에 대한 정보 0 부터 시작
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Hello");
                // datas 정보 가져오기 는 datas.get(position) 사용 datas가 배열이기 때문에 순서로 가져올 수 있다.
                builder.setMessage("Are you deleted this Item:"+ datas.get(position));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datas.remove(position);
                        //데이터 삭제 후 화면을 재세팅해야 반영된 것이 보여진다.
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            } //onItemClick end
        }); // setOnItemClickListener end
    } // setList end
    // 데이터 생성 , 네트워크에서 데이터 가져오기로 사용하면 된다.
    public void getData(){
        datas = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            datas.add("Item:"+i);
        }
        setList(datas);
    }

    public void ckbt(View v){
        getData();
    }


} // Class end