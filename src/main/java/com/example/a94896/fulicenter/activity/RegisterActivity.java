package com.example.a94896.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.DisplayUtils;
import com.example.a94896.fulicenter.bean.Result;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.CommonUtils;
import com.example.a94896.fulicenter.utils.L;
import com.example.a94896.fulicenter.utils.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
private  static final  String TAG=RegisterActivity.class.getSimpleName();
    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.nick)
    EditText mnick;
    @BindView(R.id.password)
    EditText mpassword;
    @BindView(R.id.confirm_password)
    EditText mconfirmPassword;
    @BindView(R.id.btn_register)
    Button mbtnRegister;


    String username;
    String nicknmae;
    String password;
    RegisterActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext =this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "帐号注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_register)
    public void CheckedInput() {
         username = musername.getText().toString().trim();
         nicknmae = mnick.getText().toString().trim();
         password = mpassword.getText().toString().trim();
        String confirmPwd = mconfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showLongToast(R.string.illegal_user_name);
            musername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(nicknmae)) {
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            mnick.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showLongToast(R.string.password_connot_be_empty);
            mpassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            CommonUtils.showLongToast(R.string.confirm_password_connot_be_empty);
            mconfirmPassword.requestFocus();
            return;
        } else if (!password.equals(confirmPwd)) {
            CommonUtils.showLongToast(R.string.two_input_password);
            mconfirmPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd=new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.register));
        pd.show();
        NetDao.register(mContext, username, nicknmae, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result==null){
                    CommonUtils.showLongToast(R.string.register_fail);
                }else {
                    if (result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                    }else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        musername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(R.string.register_fail);
                L.e(TAG,"register error="+error);
            }
        });
    }
}