package com.yubin.wanapp.activity.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseAppCompatActivity;
import com.yubin.wanapp.data.FavoriteArticleDetailDataDao;

import butterknife.BindView;

public class DetailActivity extends BaseAppCompatActivity {

    @BindView(R.id.main_detail)
    FrameLayout mainDetail;
    private WebviewFragment detailFragment;
    public static final String ID = "ID";
    public static final String URL = "URL";
    public static final String TITLE = "TITLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            detailFragment = (WebviewFragment) getSupportFragmentManager().getFragment(savedInstanceState, WebviewFragment.class.getSimpleName());
        } else {
            detailFragment = WebviewFragment.newInstance();
        }
        replaceFragment(R.id.main_detail,detailFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (detailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, WebviewFragment.class.getSimpleName(), detailFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //To make the AgentWeb handle the "On BackPress" logic .
        if (detailFragment.onFragmentKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
