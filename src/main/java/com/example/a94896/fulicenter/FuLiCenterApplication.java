package com.example.a94896.fulicenter;

import android.app.Application;

/**
 * Created by 94896 on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;

    private static String Username;

    public FuLiCenterApplication(){
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }
    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }
}
