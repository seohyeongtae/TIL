package com.example.p427;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Person> persons;
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
    } // onCreate end

    // UI 를 만들었을 경우 나만의 Adapter를 만들어야 한다. 기본 제공 List 이미를 썼을떄는 그냥 써도된다.
    class PersonAdapter extends BaseAdapter{

        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            // 데이터의 갯수 만큼 뿌리겠다.
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
            // position 에 따라 화면을 달라
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // person.xml 레이아웃에 넣겠다. id container 로 하여 받아왔다.
            view = inflater.inflate(R.layout.person, container, true);

            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_phone = view.findViewById(R.id.tx_phone);
            Person p = datas.get(position);

            // setImgeResource 안에는 int가 들어가야 하므로 int 형식으로 data를 만들자.
            im.setImageResource(p.getId());
            tx_name.setText(p.getName());
            tx_phone.setText(p.getPhone());
            return view;
        }
    } // class PersonAadpter end

    public void setList(ArrayList<Person> persons){
        PersonAdapter personAadpter = new PersonAdapter(persons);
        listView.setAdapter(personAadpter);

    } // setList end

    public void getData(){
        // id 값은 int 이미지이기 떄문에
    persons = new ArrayList<>();
        persons.add(new Person(R.drawable.p1,"Lee Malsook","010-1253-2983"));
        persons.add(new Person(R.drawable.p2,"dsa Malsook","010-4567-2983"));
        persons.add(new Person(R.drawable.p3,"Kim Malsook","010-7545-2983"));
        persons.add(new Person(R.drawable.p4,"Jee Malsook","010-8987-2983"));
        persons.add(new Person(R.drawable.p5,"Seo Malsook","010-1238-2983"));
        persons.add(new Person(R.drawable.p6,"MAl Malsook","010-9878-2983"));
        persons.add(new Person(R.drawable.p7,"Son Malsook","010-5458-2983"));
        persons.add(new Person(R.drawable.p8,"Rim Malsook","010-4687-2983"));
        persons.add(new Person(R.drawable.p9,"Pim Malsook","010-3547-2983"));
        setList(persons);
    } // getData end

    public void ckbt(View v){
        getData();
    }
} // Class end