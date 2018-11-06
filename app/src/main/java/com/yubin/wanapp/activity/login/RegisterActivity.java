package com.yubin.wanapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.activity.MainActivity;
import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.LoginDetailDataDao;
import com.yubin.wanapp.data.model.LoginDataGetSource;
import com.yubin.wanapp.data.model.LoginType;
import com.yubin.wanapp.util.ToastUtil;
import com.yubin.wanapp.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.regist_iv)
    ImageView registIv;
    @BindView(R.id.rg_account)
    ClearEditText rgAccount;
    @BindView(R.id.rg_password)
    ClearEditText rgPassword;
    @BindView(R.id.rg_repassword)
    ClearEditText rgRepassword;
    @BindView(R.id.btn_register)
    AppCompatButton btnRegister;
    @BindView(R.id.to_login)
    TextView toLogin;
    private LoginContract.Presenter mPresenter;
    private LoginDetailDataDao userDataDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        new LoginPresent(this, LoginDataGetSource.getInstance());
        userDataDao = App.getContext().getDaoSession().getLoginDetailDataDao();
    }

    @Override
    public void showLoginError(String errorMsg) {
        ToastUtil.makeText(errorMsg);
    }

    @Override
    public void saveUserData(LoginDetailData loginDetailData) {
        userDataDao.deleteAll();
        userDataDao.insertOrReplaceInTx(loginDetailData);
        jumpToMain();
    }

    @Override
    public void showNetworkError() {
        ToastUtil.makeText(R.string.network_error);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick({R.id.btn_register, R.id.to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if(isValid()){
                    mPresenter.check(rgAccount.getText().toString(),rgPassword.getText().toString(),rgRepassword.getText().toString(), LoginType.TYPE_REGISTER);
                }
                break;
            case R.id.to_login:
                jumpToLogin();
                break;
        }
    }

    private boolean isValid(){
        if(StringUtils.isEmpty(rgAccount.getText().toString())){
            ToastUtil.makeText(R.string.account_error);
            return false;
        }else if (rgPassword.getText().toString().length() < 6 || rgRepassword.getText().toString().length() <6) {
            ToastUtil.makeText(R.string.password_error);
            return false;
        }
        return true;
    }

    private void jumpToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void jumpToMain() {
        //登陆成功执行此逻辑
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
