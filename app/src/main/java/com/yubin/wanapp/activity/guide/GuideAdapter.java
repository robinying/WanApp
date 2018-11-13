package com.yubin.wanapp.activity.guide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.detail.TagDetailActivity;
import com.yubin.wanapp.activity.home.OnCategoryOnClickListener;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.GuideBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author : Yubin.Ying
 * time : 2018/11/7
 */
public class GuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<GuideBean.DataBean> mList;


    public GuideAdapter(Context context, List<GuideBean.DataBean> list){
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        mList = list;
    }

    public void updateData(List<GuideBean.DataBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_guide, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final GuideViewHolder guideViewHolder = (GuideViewHolder) holder;
        final GuideBean.DataBean children = mList.get(position);
        guideViewHolder.tvGuide.setText(children.getName());
        ArrayList<String> mVals = new ArrayList<>();
        for (int i = 0; i < children.getChildren().size(); i++) {
            mVals.add(children.getChildren().get(i).getName());
        }
        guideViewHolder.tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @SuppressLint("ResourceType")
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                Random random = new Random();
                int r = random.nextInt(150);
                int g = random.nextInt(150);
                int b = random.nextInt(150);

                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tfl, guideViewHolder.tagFlowLayout, false);
                tv.setTextColor(Color.rgb(r,g,b));
                tv.setText(s);
                return tv;
            }
        });
        guideViewHolder.tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TagDetailActivity.show(context.getApplicationContext(),children.getChildren().get(position).getId(),children.getChildren().get(position).getName());
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


    class GuideViewHolder extends RecyclerView.ViewHolder{

        TagFlowLayout tagFlowLayout;
        TextView tvGuide;



        public GuideViewHolder(View itemView) {
            super(itemView);
            tvGuide = (TextView) itemView.findViewById(R.id.tv_guide);
            tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.tfl);

        }


    }
}
