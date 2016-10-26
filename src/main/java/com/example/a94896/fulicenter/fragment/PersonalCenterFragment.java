package com.example.a94896.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a94896.fulicenter.Dao.UserDao;
import com.example.a94896.fulicenter.FuLiCenterApplication;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.activity.MainActivity;
import com.example.a94896.fulicenter.bean.MessageBean;
import com.example.a94896.fulicenter.bean.Result;
import com.example.a94896.fulicenter.bean.User;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.utils.ImageLoader;
import com.example.a94896.fulicenter.utils.L;
import com.example.a94896.fulicenter.utils.MFGT;
import com.example.a94896.fulicenter.utils.OkHttpUtils;
import com.example.a94896.fulicenter.utils.ResultUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by clawpo on 2016/10/24.
 */

public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    User user = null;
    MainActivity mContext;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);
        if (user != null) {
            MFGT.gotoLogin(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }

    public void onResume() {
        super.onResume();
        User user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            syncUserInfo();
            synCollectsCount();
        }
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void gotoSettings() {
        MFGT.gotoSettings(mContext);
    }
    @OnClick(R.id.layout_center_collect)
    public void gotoCollects(){
        MFGT.gotoCollects(mContext);
    }

    private void syncUserInfo() {
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                if (result!= null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void synCollectsCount() {
        NetDao.getCollectsCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null && result.isSuccess()) {
                    tvCollectCount.setText(result.getMsg());
                } else {
                    tvCollectCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                tvCollectCount.setText(String.valueOf(0));
                L.e(TAG, "error=" + error);
            }
        });
    }
}