package com.example.a94896.fulicenter;

import android.app.Application;

/**
 * Created by 94896 on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    public FuLiCenterApplication(){
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }
}
