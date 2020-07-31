package com.example.myapplication;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;

public class Menu2Frag extends Fragment {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    PhotoView photoView1;
    TextView time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_menu2_frag_store,container,false);
        btn1 = (Button)view.findViewById(R.id.button1);
        btn2 = (Button)view.findViewById(R.id.button2);
        btn3 = (Button)view.findViewById(R.id.button3);
        btn4 = (Button)view.findViewById(R.id.button4);
        btn5 = (Button)view.findViewById(R.id.button5);
        photoView1=(PhotoView)view.findViewById(R.id.photo_view1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setImageResource(R.drawable.img_subway);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setImageResource(R.drawable.busan_img_subway);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setImageResource(R.drawable.daegoo_img_subway);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setImageResource(R.drawable.gwangjoo_img_subway);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView1.setImageResource(R.drawable.daejeon_img_subway);
            }
        });
        return view;
    }


}