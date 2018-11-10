package com.yubin.wanapp.activity.nav;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.detail.DetailActivity;
import com.yubin.wanapp.activity.detail.TagDetailAdapter;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.NaviData;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by robin on 2018-11-10.
 */

public class NaviAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<NaviData.NaviBean> mList;

    public NaviAdapter(Context context, List<NaviData.NaviBean> list){
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        mList = list;
    }

    public void updateData(List<NaviData.NaviBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_navi, parent, false);
        return new NaviAdapter.NaviViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NaviAdapter.NaviViewHolder naviViewHolder = (NaviAdapter.NaviViewHolder) holder;
        List<NaviData.NaviBean.NaviArticlesBean> articlesList = mList.get(position).getArticles();
        naviViewHolder.naviFLowLayout.setAdapter(new TagAdapter<NaviData.NaviBean.NaviArticlesBean>(articlesList) {
            @Override
            public View getView(FlowLayout parent, int position, NaviData.NaviBean.NaviArticlesBean articlesBean) {
                if(articlesBean ==null){
                    return null;
                }
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tfl,
                        naviViewHolder.naviFLowLayout, false);
                Random random = new Random();
                int r = random.nextInt(150);
                int g = random.nextInt(150);
                int b = random.nextInt(150);
                tv.setText(articlesBean.getTitle());
                tv.setTextColor(Color.rgb(r,g,b));

                return tv;
            }
        });
        naviViewHolder.naviFLowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                NaviData.NaviBean.NaviArticlesBean data = articlesList.get(position);
                intent.putExtra(DetailActivity.URL, data.getLink());
                intent.putExtra(DetailActivity.TITLE, data.getTitle());
                intent.putExtra(DetailActivity.ID, data.getId());
                mContext.startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    class NaviViewHolder extends RecyclerView.ViewHolder{

        TagFlowLayout naviFLowLayout;
        TextView naviTv;




        public NaviViewHolder(final View itemView) {
            super(itemView);
            naviFLowLayout = (TagFlowLayout) itemView.findViewById(R.id.item_navi_flowlayout);
            naviTv = (TextView) itemView.findViewById(R.id.item_navi_tv);

        }
    }
}
