package com.example.a94896.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.a94896.fulicenter.Dao.SharePrefrenceUtils;
import com.example.a94896.fulicenter.Dao.UserDao;
import com.example.a94896.fulicenter.FuLiCenterApplication;
import com.example.a94896.fulicenter.I;
import com.example.a94896.fulicenter.R;
import com.example.a94896.fulicenter.Views.DisplayUtils;
import com.example.a94896.fulicenter.bean.Result;
import com.example.a94896.fulicenter.bean.User;
import com.example.a94896.fulicenter.net.NetDao;
import com.example.a94896.fulicenter.net.OkHttpUtils;
import com.example.a94896.fulicenter.utils.CommonUtils;
import com.example.a94896.fulicenter.utils.L;
import com.example.a94896.fulicenter.utils.MFGT;
import com.example.a94896.fulicenter.utils.ResultUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
private static final String TAG=LoginActivity.class.getSimpleName();
    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.password)
    EditText mpassword;


    String username;
    String password;
    LoginActivity mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mcontext=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mcontext,getResources().getString(R.string.login));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkedInput();
                break;
            case R.id.btn_register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkedInput() {
        username= musername.getText().toString().trim();
         password=mpassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)){
            mpassword.requestFocus(R.string.password_connot_be_empty);
            return;
        }
        Login();
    }

    private void Login() {
        final ProgressDialog pd=new ProgressDialog(mcontext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        L.e(TAG,"username="+username+",password="+password);
        NetDao.login(mcontext,username,password,new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result=ResultUtils.getResultFromJson(s,User.class);
                L.e( TAG,"result"+result);
                if (result==null){
                    CommonUtils.showLongToast(R.string.login_fail);
                }else {
                    if (result.isRetMsg()){
                    User user=(User)result.getRetData();
                        L.e(TAG,"user="+user);
                        UserDao dao=new UserDao(mcontext);
                        boolean isSuccess=dao.saveUser(user);
                        if (isSuccess){
                            SharePrefrenceUtils.getInstence(mcontext).saveUser(user.getMuserName());
                            FuLiCenterApplication.setUser(user);
                            MFGT.finish(mcontext);
                        }else {
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }

                    }else {
                        if (result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        }else if (result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showLongToast(R.string.login_fail_error_password);
                        }else {
                            CommonUtils.showLongToast(R.string.login_fail);
                        }
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"error="+error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_OK&&requestCode== I.REQUEST_CODE_REGISTER){
            String name=data.getStringExtra(I.User.USER_NAME);
            musername.setText(name);
        }
    }
}
