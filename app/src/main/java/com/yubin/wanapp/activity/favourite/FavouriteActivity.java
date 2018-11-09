package com.yubin.wanapp.activity.favourite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseAppCompatActivity;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.FavoriteArticleDetailData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteActivity extends BaseAppCompatActivity implements FavouriteContract.View {
    @BindView(R.id.fav_list)
    RecyclerView favList;
    @BindView(R.id.fav_refresh)
    SmartRefreshLayout favRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FavouriteContract.Presenter mPresenter;
    private final int index = 0;
    private FavouriteAdapter adapter;
    private LinearLayoutManager layoutManager;

    public static void show(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, FavouriteActivity.class));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favourite;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        setTitle(R.string.fav_title);
        layoutManager = new LinearLayoutManager(activityInstance);
        favList.setLayoutManager(layoutManager);
        favList.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        //设置 Header 为 贝塞尔雷达 样式
        favRefresh.setRefreshHeader(new BezierRadarHeader(activityInstance).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        favRefresh.setRefreshFooter(new BallPulseFooter(activityInstance).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    protected void initData() {
        super.initData();
        new FavouritePresenter(this);
        mPresenter.getFavouriteArticles(index);

    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        favRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getFavouriteArticles(index);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        favRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPresenter.getFavouriteArticles(index);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void showFavouriteArticles(final List<FavoriteArticleDetailData> list) {
        if (adapter != null) {
            adapter.updateData(list);
        } else {
            adapter = new FavouriteAdapter(activityInstance, list);
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(activityInstance, DetailActivity.class);
                    FavoriteArticleDetailData data = list.get(position);
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.ID, data.getId());
                    startActivity(intent);
                }
            });
            favList.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(FavouriteContract.Presenter presenter) {
        mPresenter = presenter;
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
