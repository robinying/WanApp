package com.yubin.wanapp.activity.nav;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.data.NaviData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by robin on 2018-11-10.
 */

public class NavGuideFragment extends BaseFragment implements NavContract.View {
    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout navigationTabLayout;
    @BindView(R.id.navigation_divider)
    View navigationDivider;
    @BindView(R.id.navigation_RecyclerView)
    RecyclerView navigationRecyclerView;
    @BindView(R.id.normal_view)
    LinearLayout normalView;
    private NavContract.Presenter mPresent;
    private NaviAdapter adapter;
    private LinearLayoutManager layoutManager;

    public static NavGuideFragment newInstance() {
        return new NavGuideFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        layoutManager = new LinearLayoutManager(getContext());
        navigationRecyclerView.setLayoutManager(layoutManager);
        navigationRecyclerView.setVisibility(View.VISIBLE);
        new NavPresenter(this);
        mPresent.getNavData();
        navigationTabLayout.setVisibility(View.VISIBLE);
        navigationDivider.setVisibility(View.VISIBLE);
        normalView.setVisibility(View.VISIBLE);
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
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(NavContract.Presenter presenter) {
        mPresent = presenter;
    }

    @Override
    public void showNavData(List<NaviData.NaviBean> list) {
        navigationTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int i) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int i) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int i) {
                return new TabView.TabTitle.Builder()
                        .setContent(list.get(i).getName())
                        .setTextColor(ContextCompat.getColor(getContext(), R.color.shallow_green),
                                ContextCompat.getColor(getContext(), R.color.shallow_grey))
                        .build();
            }

            @Override
            public int getBackground(int i) {
                return -1;
            }
        });
        if(adapter !=null){
            adapter.updateData(list);
        }else {
            adapter = new NaviAdapter(getContext(),list);
            navigationRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(boolean show) {

    }


}
