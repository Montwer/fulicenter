package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.a94896.fulicenter.Dao.SharePrefrenceUtils;
import com.example.a94896.fulicenter.Dao.UserDao;
import com.example.a94896.fulicenter.FuLiCenterApplication;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.bean.User;
import com.example.a94896.fulicenter.utils.L;
import com.example.a94896.fulicenter.utils.MFGT;

public class SplashMainActivity extends AppCompatActivity {
    private static final String TAG=SplashMainActivity.class.getSimpleName();
    private final long sleepTime=2000;
    SplashMainActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        mContext=this;
    }
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               User user = FuLiCenterApplication.getUser();
                L.e(TAG," FuLiCenter,user="+user);
                String username= SharePrefrenceUtils.getInstence(mContext).getUser();
                L.e(TAG,"fulicenter,username="+username);
                if (user==null&&username!=null) {
                    UserDao dao = new UserDao(mContext);
                    user=dao.getUser(username);
                    L.e(TAG,"database,user="+user);
                    if (user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashMainActivity.this);
                finish();
            }
        },sleepTime);
    }
}
