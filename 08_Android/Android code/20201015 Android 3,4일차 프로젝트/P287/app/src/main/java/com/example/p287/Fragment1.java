package com.example.p287;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    TextView textView;
    EditText editText;
    ImageView imageView;

    public Fragment1(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        // Fragment 는 직접 viewGroup을 만들어서 findView를 써야 한다.
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        textView = viewGroup.findViewById(R.id.textView);
        editText = viewGroup.findViewById(R.id.editText);
        imageView = viewGroup.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                textView.setText(str);
            }
        });
        return viewGroup;
    }


}
