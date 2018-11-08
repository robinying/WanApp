package com.yubin.wanapp.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.GuideBean;
import com.yubin.wanapp.data.model.TagDetailData;

import java.util.List;

import butterknife.BindView;

public class TagDetailActivity extends BaseAppCompatActivity implements TagDetailContract.View{

    @BindView(R.id.tag_detail_list)
    RecyclerView tagDetailList;
    @BindView(R.id.tag_detail_refresh)
    SmartRefreshLayout tagDetailRefresh;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    private LinearLayoutManager layoutManager;
    private TagDetailAdapter adapter;
    private TagDetailContract.Presenter mPresenter;
    private int cid;
    private int curPage;
    private int index;
    private int pageCount;


    public static void show(Context context,int cid) {
        if (context != null) {
            context.startActivity(new Intent(context, TagDetailActivity.class)
                    .putExtra("cid",cid));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        Intent intent =getIntent();
        cid = intent.getIntExtra("cid",0);
        new TagDetailPresenter(this, TagDetailData.getInstance());
        mPresenter.getTagDetail(index,cid);
        curPage = index;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_detail;
    }

    @Override
    public void initView() {
        layoutManager = new LinearLayoutManager(activityInstance);
        tagDetailList.setLayoutManager(layoutManager);
        tagDetailList.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        //设置 Header 为 贝塞尔雷达 样式
        tagDetailRefresh.setRefreshHeader(new BezierRadarHeader(activityInstance).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        //tagDetailRefresh.setRefreshFooter(new BallPulseFooter(activityInstance).setSpinnerStyle(SpinnerStyle.Scale));
    }


    @Override
    protected void bindEvent() {
        super.bindEvent();
        tagDetailRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getTagDetail(index,cid);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        tagDetailRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                curPage ++;
                Log.d("robin","pageCount ="+pageCount);
                if (curPage <= pageCount) {
                    mPresenter.getTagDetail(curPage, cid);
                }
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void showTagDetail(final List<ArticleDetailData> list) {
        if(adapter !=null){
            adapter.updateData(list);
        }else{
            adapter = new TagDetailAdapter(activityInstance, list);
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(activityInstance, DetailActivity.class);
                    ArticleDetailData data = list.get(position);
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.ID, data.getId());
                    startActivity(intent);
                }
            });
        }
        tagDetailList.setAdapter(adapter);
        pageCount = mPresenter.getPageCount();
    }

    @Override
    public void showEmptyView(boolean show) {
        emptyView.setVisibility(show?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(TagDetailContract.Presenter presenter) {
        mPresenter= presenter;
    }
}
