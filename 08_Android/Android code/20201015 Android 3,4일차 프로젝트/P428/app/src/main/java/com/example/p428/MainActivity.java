package com.example.p428;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons;
    ListView listView;
    LinearLayout person_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        person_container = findViewById(R.id.person_container);

        // list 목록 클릭했을 경우 AlertDialog
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person p = persons.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //alertdialog 에 들어갈 view 불러오기 및 생성 layout 자체에 id 생성후 불러와야 한다.
                LayoutInflater layoutInflater = getLayoutInflater();
                View dview = layoutInflater.inflate(R.layout.dialog,
                        (ViewGroup) findViewById(R.id.dlayout));

                // imageView2 세팅
                ImageView dimg = dview.findViewById(R.id.imageView2);
                dimg.setImageResource(p.getImg());
                builder.setView(dview);

                builder.setTitle("Hi");
                builder.setMessage("NAME:"+p.getName());
                builder.setIcon(p.getImg());

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }); // setOnItemClickListener end

    } //onCreate end

    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas) {
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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person, person_container,true);

            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_id = view.findViewById(R.id.tx_id);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_age = view.findViewById(R.id.tx_age);
            Person p = datas.get(position);

            im.setImageResource(p.getImg());
            tx_id.setText(p.getId());
            tx_name.setText(p.getName());
            // int 를 setText 하려면 +"" 를 해야 한다.
            tx_age.setText(p.getAge()+"");
            return view;
        } // getView end
    } // class personadapter end


    public void setList(final ArrayList<Person> persons){
        PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);
    } // setList end

    public void getData(){
        persons = new ArrayList<>();
        persons.add(new Person(R.drawable.p1,"0001","이말숙",29));
        persons.add(new Person(R.drawable.p2,"0002","김말숙",21));
        persons.add(new Person(R.drawable.p3,"0003","정말숙",22));
        persons.add(new Person(R.drawable.p4,"0004","상말숙",23));
        persons.add(new Person(R.drawable.p5,"0005","최말숙",24));
        persons.add(new Person(R.drawable.p6,"0006","서말숙",25));
        persons.add(new Person(R.drawable.p7,"0007","오말숙",26));
        persons.add(new Person(R.drawable.p8,"0008","성말숙",27));
        persons.add(new Person(R.drawable.p9,"0009","강말숙",28));
        setList(persons);
    }

    public void ckbt(View v){
        getData();
    }

} // Class end