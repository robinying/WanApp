package com.yubin.wanapp.activity.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.activity.home.ArticlesAdapter;
import com.yubin.wanapp.activity.home.HomeTabFragment;
import com.yubin.wanapp.activity.home.OnCategoryOnClickListener;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.GuideBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Yubin.Ying
 * time : 2018/11/7
 */
public class GuideFragment extends BaseFragment implements GuideContract.View {
    @BindView(R.id.recyclerview_guide)
    RecyclerView recyclerviewGuide;
    @BindView(R.id.guide_refresh)
    SmartRefreshLayout guideRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    private GuideContract.Presenter mPresenter;
    private LinearLayoutManager layoutManager;
    private GuideAdapter adapter;

    public GuideFragment() {

    }

    public static GuideFragment newInstance(){
        return new GuideFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        new GuidePresenter(this, GuideRemoteData.getInstance());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerviewGuide.setLayoutManager(layoutManager);
        recyclerviewGuide.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        //设置 Header 为 贝塞尔雷达 样式
        guideRefresh.setRefreshHeader(new BezierRadarHeader(mContext).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        guideRefresh.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getGuideData();
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        guideRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getGuideData();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        guideRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPresenter.getGuideData();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }


    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showGuideData(final List<GuideBean.DataBean> list) {
        if (adapter != null) {
            adapter.updateData(list);
        } else {
            adapter = new GuideAdapter(getContext(), list);
            recyclerviewGuide.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void initViews(View view) {

    }

    public void jumpToTop(){
        if(recyclerviewGuide!=null){
            recyclerviewGuide.scrollToPosition(0);
        }
    }

    @Override
    public void setPresenter(GuideContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
