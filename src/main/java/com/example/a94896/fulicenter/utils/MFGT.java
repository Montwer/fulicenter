package com.example.a94896.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.activity.BoutiqueChildActivity;
import com.example.a94896.fulicenter.activity.CategoryChildActivity;
import com.example.a94896.fulicenter.activity.GoodsDetailActivity;
import com.example.a94896.fulicenter.activity.MainActivity;
import com.example.a94896.fulicenter.bean.BoutiqueBean;

import static android.R.id.list;

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
        startActivity(context,intent);
    }
    public static void startActivity(Context context, Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoGoodsDetailActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
    public static void gotoCategoryChildActivity(Context context, int catId){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
//        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);

        startActivity(context,intent);
    }
}
