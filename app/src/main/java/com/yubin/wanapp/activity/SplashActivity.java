package com.yubin.wanapp.activity;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.login.LoginActivity;
import com.yubin.wanapp.activity.splash.SplashFragmentAdapter;
import com.yubin.wanapp.util.BasePreference;
import com.yubin.wanapp.util.Constant;
import com.yubin.wanapp.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.image_btn_pre)
    ImageButton imageBtnPre;
    @BindView(R.id.img_indicator_0)
    ImageView imgIndicator0;
    @BindView(R.id.img_indicator_1)
    ImageView imgIndicator1;
    @BindView(R.id.img_indicator_2)
    ImageView imgIndicator2;
    @BindView(R.id.btn_finish)
    AppCompatButton btnFinish;
    @BindView(R.id.image_btn_next)
    ImageButton imageBtnNext;
    private SplashActivity activityInstance;
    private ImageView[] indicators;
    private int[] bgColors;
    private int currentPosition;
    private String[] permissionArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        activityInstance = this;
        if(BasePreference.getBoolean(ConstantUtil.SKIP_SPLASH,false)){
            jumpToLogin();
        }else{
            checkPermissions();
            SplashFragmentAdapter pageAdapter = new SplashFragmentAdapter(getSupportFragmentManager());
            container.setAdapter(pageAdapter);
            indicators = new ImageView[]{imgIndicator0, imgIndicator1, imgIndicator2};
            bgColors = new int[]{ContextCompat.getColor(this,R.color.colorPrimary)
                    ,ContextCompat.getColor(this,R.color.cyan_500)
                    ,ContextCompat.getColor(this,R.color.light_blue_500)
            };

            container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int color = (int) new ArgbEvaluator().evaluate(positionOffset, bgColors[position], bgColors[position == 2 ? position  : position+1]);
                    container.setBackgroundColor(color);
                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                    container.setBackgroundColor(bgColors[position]);
                    updateIndicators(position);
                    imageBtnPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                    imageBtnNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                    btnFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(i == position ? R.drawable.ic_indicator_selected : R.drawable.ic_indicator_unselected);
        }
    }

    @OnClick({R.id.image_btn_pre, R.id.image_btn_next,R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_btn_pre:
                currentPosition -= 1;
                container.setCurrentItem(currentPosition);
                break;
            case R.id.image_btn_next:
                currentPosition += 1;
                container.setCurrentItem(currentPosition);
                break;
            case R.id.btn_finish:
                BasePreference.putBoolean(ConstantUtil.SKIP_SPLASH,true);
                jumpToLogin();
                break;
        }
    }

    private void jumpToLogin(){
        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
