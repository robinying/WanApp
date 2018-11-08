package com.yubin.wanapp.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.activity.BaseAppCompatActivity;
import com.yubin.wanapp.activity.MainActivity;
import com.yubin.wanapp.activity.SplashActivity;
import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.LoginDetailDataDao;
import com.yubin.wanapp.data.model.LoginDataGetSource;
import com.yubin.wanapp.data.model.LoginType;
import com.yubin.wanapp.util.StringUtils;
import com.yubin.wanapp.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseAppCompatActivity implements LoginContract.View {

    @BindView(R.id.splash_login)
    ImageView splashLogin;
    @BindView(R.id.edit_username)
    TextInputEditText editUsername;
    @BindView(R.id.edit_password)
    TextInputEditText editPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.regist_tx)
    TextView registTx;
    private LoginContract.Presenter mPresenter;
    private LoginDetailDataDao logindataDao;

    public static void show(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();
        new LoginPresent(this, LoginDataGetSource.getInstance());
        logindataDao = App.getContext().getDaoSession().getLoginDetailDataDao();
//        LoginDetailData localdata =logindataDao.queryBuilder().limit(1).where(LoginDetailDataDao.Properties.Username.isNotNull()).unique();
//
//        if (localdata != null && !StringUtils.isEmpty(localdata.getUsername()) && !StringUtils.isEmpty(localdata.getStorePassword())) {
//            mPresenter.autoLogin(localdata.getUsername(),localdata.getStorePassword(),LoginType.TYPE_LOGIN);
//            //jumpToMain();
//        }
    }

    @Override
    public void showLoginError(String errorMsg) {
        hideWaitDialog();
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void saveUserData(LoginDetailData loginDetailData) {
        hideWaitDialog();
        logindataDao.deleteAll();
        loginDetailData.setStorePassword(editPassword.getText().toString());
        logindataDao.insertOrReplaceInTx(loginDetailData);
        jumpToMain();
    }

    @Override
    public void showNetworkError() {
        hideWaitDialog();
        ToastUtils.showShort(R.string.network_error);
    }

    @Override
    public void showAutoLogin() {
        hideWaitDialog();
        ToastUtils.showShort("成功登陆");
        jumpToMain();
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter =presenter;
    }

    private void jumpToMain() {
        //登陆成功执行此逻辑
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void jumpToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    @OnClick({R.id.btn_login, R.id.regist_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if(isValid()){
                    showWaitDialog();
                    mPresenter.check(editUsername.getText().toString(),editPassword.getText().toString(),null, LoginType.TYPE_LOGIN);
                }
                break;
            case R.id.regist_tx:
                jumpToRegister();
                break;
        }
    }

    private boolean isValid(){
        if (StringUtils.isEmpty(editUsername.getText().toString())) {
            ToastUtil.makeText(R.string.account_error);
            return false;
        } else if (editPassword.getText().toString().length() < 6) {
            ToastUtil.makeText(R.string.password_error);
            return false;
        }
        return true;
    }
}
