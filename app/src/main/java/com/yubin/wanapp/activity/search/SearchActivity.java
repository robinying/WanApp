package com.yubin.wanapp.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.BaseAppCompatActivity;
import com.yubin.wanapp.activity.SplashActivity;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.activity.detail.TagDetailAdapter;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.model.GetArticlesData;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseAppCompatActivity implements SearchContract.View {


    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    private LinearLayoutManager layoutManager;
    private TagDetailAdapter adapter;
    private SearchContract.Presenter mPresenter;
    private final int INDEX = 0;

    public static void show(Context context){
        if (context != null) {
            context.startActivity(new Intent(context, SearchActivity.class));
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();
        new SearchPresenter(this, GetArticlesData.getInstance());
        layoutManager = new LinearLayoutManager(activityInstance);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //确认搜索后就隐藏键盘和热词布局，给文章列表腾出空间
                hideImn();
                hideTagFlowLayout(true);
                mPresenter.getQueryList(INDEX,query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hideTagFlowLayout(false);
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    public void showQueryArticles(final List<ArticleDetailData> list) {
        if(adapter !=null){
            adapter.updateData(list);
        }else {
            adapter = new TagDetailAdapter(activityInstance,list);
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
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showEmptyView(boolean show) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void hideImn() {
        InputMethodManager manager = (InputMethodManager) activityInstance.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
    }

    private void hideTagFlowLayout(boolean hide) {
        if (hide) {
            flowLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            flowLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }
}
