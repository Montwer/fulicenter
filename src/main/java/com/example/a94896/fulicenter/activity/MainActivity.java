package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
    }
}
