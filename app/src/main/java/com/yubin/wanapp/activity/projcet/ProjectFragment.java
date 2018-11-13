package com.yubin.wanapp.activity.projcet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseFragment;
import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.data.model.ProjectDataImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjectFragment extends BaseFragment implements ProjectContract.View {

    @BindView(R.id.project_tab_layout)
    SlidingTabLayout projectTabLayout;
    @BindView(R.id.project_divider)
    View projectDivider;
    @BindView(R.id.project_viewpager)
    ViewPager projectViewpager;
    @BindView(R.id.normal_view)
    LinearLayout normalView;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private ProjectContract.Presenter mPresenter;
    private List<Projecttree.ProjectBean> mDatas;
    private int currentPage;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
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
    protected int getLayoutId() {
        return R.layout.fragment_projcet;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        new ProjectPresenter(this, ProjectDataImpl.getInstance());
        mPresenter.getProjectListData();
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
    public void showProjectList(List<Projecttree.ProjectBean> value) {
        mDatas = value;
        for (Projecttree.ProjectBean data : mDatas) {
            ProjectListFragment projectListFragment = ProjectListFragment.newInstance(data.getId());
            mFragments.add(projectListFragment);
        }
        projectViewpager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mDatas == null? 0 : mDatas.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mDatas.get(position).getName();
            }
        });
        projectViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        projectTabLayout.setViewPager(projectViewpager);
        projectViewpager.setCurrentItem(0);
        projectTabLayout.setVisibility(View.VISIBLE);
        projectViewpager.setVisibility(View.VISIBLE);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(ProjectContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
