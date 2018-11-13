package com.yubin.wanapp.activity.projcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.data.model.ProjectDataImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjectListFragment extends BaseFragment implements ProjectListContract.View {
    @BindView(R.id.project_list_recycler_view)
    RecyclerView projectListRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout normalView;
    private int cid;
    private int index =0;
    private ProjectListContract.Presenter mPresenter;
    private ProjectListAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        cid = bundle.getInt("cid");
        Log.d("robin","cid ="+cid);
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        new ProjectListPresenter(this, ProjectDataImpl.getInstance());
        Log.d("robin","getProjectListData");
        mPresenter.getProjectListData(index, cid);
        layoutManager = new LinearLayoutManager(getContext());
        projectListRecyclerView.setLayoutManager(layoutManager);
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.getProjectListData(index,cid);
                refreshLayout.finishRefresh(2000);
            }
        });
        normalView.setVisibility(View.VISIBLE);
        projectListRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public static ProjectListFragment newInstance(int cid) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt("cid", cid);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(ProjectListContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showProjectViewPager(List<ArticleDetailData> value) {
        if(adapter !=null){
            adapter.updateData(value);
        }else{
            adapter = new ProjectListAdapter(getContext(),value);
            adapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    ArticleDetailData data = value.get(position);
                    intent.putExtra(DetailActivity.URL, data.getLink());
                    intent.putExtra(DetailActivity.TITLE, data.getTitle());
                    intent.putExtra(DetailActivity.ID, data.getId());
                    startActivity(intent);
                }
            });
            projectListRecyclerView.setAdapter(adapter);
        }
    }
}
