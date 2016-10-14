package com.example.a94896.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a94896.fulicenter.R;

public class SplashMainActivity extends AppCompatActivity {
private static final long sleepTime=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }
    protected void onStart(){
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start=System.currentTimeMillis();
                long costTime= System.currentTimeMillis()-start;
                if (sleepTime-costTime>0){
                    try{
                        Thread.sleep(sleepTime-costTime);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashMainActivity.this,MainActivity.class));
            }
        }).start();
    }
}
