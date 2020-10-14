## WorkShop P351 (20201004)

### 2일차, 3일차 교육 내용 활용

![KakaoTalk_20201014_175612859](Android(WS_2,3%EC%9D%BC%EC%B0%A8%EC%A2%85%ED%95%A9)/KakaoTalk_20201014_175612859.jpg)

> Progress 는 Spin Style 사용 
>
> AlertDialog 는 별도 View 만들지 않고 사용

![KakaoTalk_20201014_180418664](Android(WS_2,3%EC%9D%BC%EC%B0%A8%EC%A2%85%ED%95%A9)/KakaoTalk_20201014_180418664.jpg)

### MainActivity.java

```java
package com.example.p351;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment_setting fragment_setting;

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle("메뉴");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        fragment1 = new Fragment1(this);
        fragment2 = new Fragment2(this);
        fragment3 = new Fragment3(this);
        fragment_setting = new Fragment_setting();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment1).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bottom1){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment1).commit();
                }else if(item.getItemId() == R.id.bottom2){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment2).commit();
                }else if(item.getItemId() == R.id.bottom3){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout,fragment3).commit();
                }

                return false;
            }
        }); // end function

    } // onCreat end

    // Action bar 설정 항목별 지정 res-> menu -> 만든 xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return true;
    }
    // Action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.top1){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment_setting).commit();
        }
        return super.onOptionsItemSelected(item);
    }
} // class end
```



### Fragment1.java

> Toast 사용 

```java
package com.example.p351;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment1 extends Fragment {

    Button button;
    MainActivity m;
    public Fragment1(MainActivity m) {
        this.m = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup =null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_1,container,false);
        button = viewGroup.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(m, "Fragment1 ...", Toast.LENGTH_SHORT).show();
            }
        });

        return viewGroup;
    }

}
```



### Fragment2.java

>Aler Dialog 사용

```java
package com.example.p351;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2 extends Fragment {

    Button button2;
    AlertDialog alertDialog;
    MainActivity m;

    public Fragment2(MainActivity m) {
        this.m = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_2,container,false);
        button2 = viewGroup.findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(m);
                builder.setTitle("Fragment2 AlertDialog");
                builder.setIcon(R.drawable.d2);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(m, "Yes click", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(m, "No click", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } // onClick end
        }); // ClickListener end

        return viewGroup;
    } // onCreateView end
} // Class end
```



### Fragment3.java

>ProgressDialog (Style Spin) 사용

```java
package com.example.p351;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment3 extends Fragment {

    Button button3;
    ProgressDialog progressDialog;
    MainActivity m;

    public Fragment3(MainActivity m) {
        this.m = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_3,container,false);
        button3 = viewGroup.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(m);
                progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Fragment3 Loading..");
                progressDialog.show();

              }
        });
        // 이거 꼭 viewGroup 으로 바꿔야 한다..........
        return viewGroup;
    } // onCreateView end
} // Class end
```



### Fragment_setting.java

> 따로 함수 구현 안함 Fragment  blank 그대로 사용



### XML 구성 - 상세한 내용은 3일차 참고

###  (Fragment , 상/하단 Menu : Actionbar Option, bottom  Navigation 사용)

