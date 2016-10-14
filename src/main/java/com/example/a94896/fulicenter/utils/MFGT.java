package com.example.a94896.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.activity.MainActivity;

//提高效率同意界面的风格
public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
