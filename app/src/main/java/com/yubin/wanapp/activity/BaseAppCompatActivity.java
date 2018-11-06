package com.yubin.wanapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yubin.wanapp.R;
import com.yubin.wanapp.interf.IDialogController;
import com.yubin.wanapp.util.DialogHelper;

import butterknife.ButterKnife;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements IDialogController {
    protected String TAG;
    private boolean mIsDestroy;
    private Fragment mFragment;
    public BaseAppCompatActivity activityInstance;

    private ProgressDialog _waitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();

        Log.e(TAG, "onCreate");
        if (initBundle(getIntent().getExtras())) {
            setContentView(getLayoutId());
            activityInstance = this;
            initWindow();
            initData();
            ButterKnife.bind(this);
            initView();
            requestData();
            initBroadCast();
            bindEvent();
        } else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        mIsDestroy = true;
        super.onDestroy();
        activityInstance = null;
        unbindBroad();
    }

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {
                    transaction.hide(mFragment).show(fragment);
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
        }
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    protected abstract int getLayoutId();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void requestData() {
    }

    protected void bindEvent() {
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }

    /**
     * 初始化广播
     */
    protected void initBroadCast() {
    }

    /**
     * 解绑广播
     */
    protected void unbindBroad() {
    }

    /**
     * 显示加载等待视图,显示"加载中..."
     *
     * @return
     */
    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    /**
     * 显示加载等待视图,显示传入字符串资源值
     *
     * @param resid
     * @return
     */
    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    /**
     * 显示加载等待视图,显示传入字符串
     *
     * @param message
     * @return
     */
    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_waitDialog == null) {
            _waitDialog = DialogHelper.getProgressDialog(this, message);
        }
        if (_waitDialog != null) {
            _waitDialog.setMessage(message);
            _waitDialog.show();
        }
        return _waitDialog;
    }

    /**
     * 隐藏加载等待视图
     */
    @Override
    public void hideWaitDialog() {
        if (_waitDialog != null && _waitDialog.isShowing()) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
