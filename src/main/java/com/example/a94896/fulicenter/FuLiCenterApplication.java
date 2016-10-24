package com.example.a94896.fulicenter;

import android.app.Application;

import com.example.a94896.fulicenter.bean.User;

/**
 * Created by 94896 on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;



    private static String Username;
    private static User user;

    public void onCreate(){
        super.onCreate();
        application=this;
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

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
