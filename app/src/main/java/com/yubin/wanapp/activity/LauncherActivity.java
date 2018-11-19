package com.yubin.wanapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.login.LoginActivity;
import com.yubin.wanapp.activity.login.LoginContract;
import com.yubin.wanapp.activity.login.LoginPresent;
import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.LoginDetailDataDao;
import com.yubin.wanapp.data.model.LoginDataGetSource;
import com.yubin.wanapp.data.model.LoginType;
import com.yubin.wanapp.util.BasePreference;
import com.yubin.wanapp.util.Constant;
import com.yubin.wanapp.util.ConstantUtil;
import com.yubin.wanapp.util.StringUtils;
import com.yubin.wanapp.view.CountDownView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends BaseAppCompatActivity implements LoginContract.View{


    @BindView(R.id.countdown)
    CountDownView countdown;
    @BindView(R.id.splash_pic)
    ImageView splashPic;
    private LoginContract.Presenter mPresenter;
    private boolean autoLogin = false;
    private String[] permissionArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private LoginDetailDataDao logindataDao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        super.initView();
        new LoginPresent(this, LoginDataGetSource.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkPermissions();
        }
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        countdown.setOnFinishAction(new CountDownView.Action() {
            @Override
            public void onAction() {
                if(autoLogin){
                    MainActivity.show(activityInstance);
                    ToastUtils.showShort(R.string.auto_login_success);
                    finish();
                }else{
                    if(BasePreference.getBoolean(ConstantUtil.SKIP_SPLASH,false)){
                        LoginActivity.show(activityInstance);
                        finish();
                    }else{
                        SplashActivity.show(activityInstance);
                        finish();
                    }
                }
            }
        });
        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoLogin) {
                    MainActivity.show(activityInstance);
                    //ToastUtils.showShort(R.string.auto_login_success);
                    finish();
                } else {
                    if (BasePreference.getBoolean(ConstantUtil.SKIP_SPLASH, false)) {
                        LoginActivity.show(activityInstance);
                    } else {
                        SplashActivity.show(activityInstance);
                    }
                }
            }
        });
        countdown.start();
        logindataDao = App.getContext().getDaoSession().getLoginDetailDataDao();
        LoginDetailData localdata =logindataDao.queryBuilder().limit(1).where(LoginDetailDataDao.Properties.Username.isNotNull()).unique();

        if (localdata != null && !StringUtils.isEmpty(localdata.getUsername()) && !StringUtils.isEmpty(localdata.getStorePassword())) {
            mPresenter.autoLogin(localdata.getUsername(),localdata.getStorePassword(), LoginType.TYPE_LOGIN);
        }
    }


    @Override
    public void showLoginError(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void saveUserData(LoginDetailData loginDetailData) {

    }

    @Override
    public void showNetworkError() {
        ToastUtils.showShort(R.string.network_error);
    }

    @Override
    public void showAutoLogin() {
        autoLogin = true;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void checkPermissions() {
        if (AndPermission.hasPermission(activityInstance, permissionArray)) {
        } else {
            AndPermission.with(activityInstance)
                    .requestCode(Constant.REQUEST_PERMISSIONS)
                    .permission(permissionArray)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                            if (AndPermission.hasPermission(activityInstance, permissionArray)) {

                            }
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            if (AndPermission.hasPermission(activityInstance, permissionArray)) {

                                return;
                            }
                            AndPermission.defaultSettingDialog(activityInstance, Constant.REQUEST_PERMISSIONS)
                                    .setTitle("权限申请失败")
                                    .setMessage("您已禁用 \"读写手机存储\" 权限，请在设置中授权！")
                                    .setPositiveButton("好，去设置")
                                    .show();
                        }
                    })
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(activityInstance, rationale).show();
                        }
                    })
                    .start();
        }
    }


}
