package com.yubin.wanapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blankj.utilcode.util.ToastUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.util.BasePreference;
import com.yubin.wanapp.util.ConstantUtil;
import com.yubin.wanapp.view.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseAppCompatActivity {


    @BindView(R.id.clear_cache)
    ItemView clearCache;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void show(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, SettingsActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle(R.string.action_settings);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }


    @OnClick(R.id.clear_cache)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_cache:
                ToastUtils.showShort(R.string.clear_cache_success);
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
