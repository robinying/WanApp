package com.yubin.wanapp.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.BannerDetailData;
import com.yubin.wanapp.data.model.GetArticlesData;
import com.yubin.wanapp.data.model.GetRemoteBannerData;
import com.yubin.wanapp.data.model.LoginDataGetSource;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.yubin.wanapp.util.GlideLoader;
import com.yubin.wanapp.util.NetworkUtil;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class HomeTabFragment extends BaseFragment implements HomeContract.View{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private Banner mBanner;
    private ArticlesAdapter adapter;
    private HomeContract.Presenter mPresent;
    private static final  int index =0;
    private int currentPage;
    private boolean isFirstLoad=true;
    private LinearLayoutManager layoutManager;



    public static HomeTabFragment newInstance(){
        return new HomeTabFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad){
            mPresent.getArticles(index, true, true);
            mPresent.getBanner();
            currentPage = index;
            isFirstLoad = false;
        }else {
            mPresent.getArticles(currentPage,false,false);
        }
        if(mBanner!=null){
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresent.unSubscribe();
        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        new HomePresenter(this,GetRemoteBannerData.getInstance(),GetArticlesData.getInstance());
        mBanner = (Banner) getActivity().getLayoutInflater().inflate(R.layout.banner_header, null);
        mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getContext().getResources().getDisplayMetrics().heightPixels/4));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = index;
                mPresent.getArticles(currentPage,true,false);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    loadMore();
                }
            }
        });

    }


    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void setLoadingIndicator(final boolean isActive) {
        if(refreshLayout!=null) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(isActive);
                }
            });
        }
    }

    @Override
    public void showArticles(final List<ArticleDetailData> list) {
        if (adapter!=null){
            adapter.updateData(list);
        }else {
            adapter = new ArticlesAdapter(getContext(), list);
            adapter.setCategoryListener(new OnCategoryOnClickListener() {
                @Override
                public void onClick(View view, int position) {
//                    Intent intent = new Intent(getContext(), CategoryActivity.class);
//                    ArticleDetailData data = list.get(position);
//                    if (data.getChapterName().isEmpty()) {
//                        return;
//                    }
//                    intent.putExtra(CategoryActivity.CATEGORY_ID, data.getChapterId());
//                    intent.putExtra(CategoryActivity.CATEGORY_NAME, data.getChapterName());
//                    startActivity(intent);
                }
            });
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    ArticleDetailData data = list.get(position);
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.ID, data.getId());
                    startActivity(intent);
                }
            });
            adapter.setHeaderView(mBanner);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(boolean toShow) {
        if(emptyView !=null) {
            emptyView.setVisibility(toShow ? View.VISIBLE : View.INVISIBLE);
        }
        if(nestedScrollView !=null) {
            nestedScrollView.setVisibility(!toShow ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void showBanner(final List<BannerDetailData> list) {
        List<String> urls = new ArrayList<>();
        for(BannerDetailData item:list){
            urls.add(item.getImagePath());
        }
        mBanner.setImages(urls)
                .isAutoPlay(true)
                .setDelayTime(10000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(getContext(),DetailActivity.class);
                        BannerDetailData data = list.get(position);
                        intent.putExtra(DetailActivity.URL, data.getUrl());
                        intent.putExtra(DetailActivity.TITLE, data.getTitle());
                        intent.putExtra(DetailActivity.ID, data.getId());
                        startActivity(intent);
                    }
                })
                .start();

    }

    @Override
    public void hideBanner() {
        mBanner.setVisibility(View.GONE);

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresent= presenter;
    }

    @Override
    public void showAutoLoginFail(){

    }

    @Override
    public void jumpToTop() {
        nestedScrollView.scrollTo(0,0);
    }


    private void loadMore(){
        boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(getContext());
        if (isNetworkAvailable){
            currentPage+=1;
            mPresent.getArticles(currentPage,true,false);
        }else {
            Toast.makeText(getContext(),R.string.network_error,Toast.LENGTH_LONG).show();
        }

    }
}
