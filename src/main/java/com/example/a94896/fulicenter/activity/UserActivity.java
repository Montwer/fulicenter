package com.example.a94896.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a94896.fulicenter.Dao.SharePrefrenceUtils;
import com.example.a94896.fulicenter.FuLiCenterApplication;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.DisplayUtils;
import com.example.a94896.fulicenter.bean.User;
import com.example.a94896.fulicenter.utils.CommonUtils;
import com.example.a94896.fulicenter.utils.ImageLoader;
import com.example.a94896.fulicenter.utils.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {


    @BindView(R.id.iv_username_photo)
    ImageView ivUsernamePhoto;
    @BindView(R.id.iv_username)
    TextView ivUsername;
    @BindView(R.id.nick)
    TextView nick;

    UserActivity mContext;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        mContext=this;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.user_profile));
    }

    @Override
    protected void initData() {
         user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvater(ImageLoader.getAvatarUrl(user), mContext, ivUsernamePhoto);
            ivUsername.setText(user.getMuserName());
            nick.setText(user.getMuserNick());
        } else {
            finish();
        }
    }

    @Override
    protected void setListener() {

    }
    @OnClick({R.id.iv_user_avatar, R.id.layout_user_profile_username, R.id.layout_user_profile_nickname, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.layout_user_profile_username:
                CommonUtils.showLongToast(R.string.update_user_nick_success);
                break;
            case R.id.layout_user_profile_nickname:
                break;
            case R.id.btn_register:
                logout();
                break;
        }
    }

    private void logout() {
            if (user!=null){
            SharePrefrenceUtils.getInstence(mContext).removeUser();
            FuLiCenterApplication.setUser(null);
                MFGT.gotoLogin(mContext);
        }
        finish();
    }
}
