package com.example.a94896.fulicenter.net;

import android.content.Context;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.bean.BoutiqueBean;
import com.example.a94896.fulicenter.bean.CategoryChildBean;
import com.example.a94896.fulicenter.bean.CategoryGroupBean;
import com.example.a94896.fulicenter.bean.GoodsDetailsBean;
import com.example.a94896.fulicenter.bean.NewGoodsBean;
import com.example.a94896.fulicenter.bean.Result;
import com.example.a94896.fulicenter.utils.MD5;


public class NetDao {
    /**
     * 下载新品首页数据
     * @param pageId
     * @param listener
     */
    public static void downloadGoods(Context context,int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,String.valueOf(catId))
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    /**
     * 下载商品详情信息
     * @param goodsId
     * @param listener
     */
    public static void downloadGoodsDetails(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID,goodsId+"")
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    /**
     * 下载精选首页数据
     * @param listener
     */
    public static void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
    /**下载分类商品的大类信息
     */
    public static void downloadCategoryGroupList(Context context, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    /**
     * 下载分类中小类商品信息
     * @param groupId
     * @param listener
     */
    public static void downloadCategoryChildList(Context context, int groupId,OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(groupId))
                .addParam(I.PAGE_ID, String.valueOf(I.PAGE_ID_DEFAULT))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
    /**
     * 从服务端下载分类列表小列表中的过滤标签中的一组商品信息
     * @param catId
     * @param pageId
     * @param listener
     */
    public static void downloadCategoryGoods(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
public static void register(Context context, String username, String nickname, String password, OkHttpUtils.OnCompleteListener<Result>listener){
    OkHttpUtils<Result>utils=new OkHttpUtils<>(context);
    utils.setRequestUrl(I.REQUEST_REGISTER)
            .addParam(I.User.USER_NAME,username)
            .addParam(I.User.NICK,nickname)
            .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
            .targetClass(Result.class)
            .post()
            .execute(listener);
}
public static void login(Context context, String username, String password, OkHttpUtils.OnCompleteListener<String> listener){
    OkHttpUtils<String>utils=new OkHttpUtils<>(context);
    utils.setRequestUrl(I.REQUEST_LOGIN)
            .addParam(I.User.USER_NAME,username)
            .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
            .targetClass(String.class)
            .execute(listener);
}
    public static void updateNick(Context context, String username, String nick, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,nick)
                .targetClass(String.class)
                .execute(listener);
    }
}
