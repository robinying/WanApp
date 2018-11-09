package com.yubin.wanapp.activity.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public class TagDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ArticleDetailData> mList;
    private OnRecyclerViewItemOnClickListener listener;

    public TagDetailAdapter(Context context, List<ArticleDetailData> list){
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        mList = list;
    }

    public void updateData(List<ArticleDetailData> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tag_detai, parent, false);
        return new TagDetailViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TagDetailViewHolder tagDetailViewHolder = (TagDetailViewHolder) holder;
        tagDetailViewHolder.authorName.setText(mList.get(position).getAuthor());
        tagDetailViewHolder.title.setText(mList.get(position).getTitle());

    }

    public void setItemClickListener(OnRecyclerViewItemOnClickListener listener){
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    class TagDetailViewHolder extends RecyclerView.ViewHolder{

        TextView authorName;
        TextView title;
        LinearLayout tagLL;
        OnRecyclerViewItemOnClickListener listener;




        public TagDetailViewHolder(final View itemView, final OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            tagLL = (LinearLayout) itemView.findViewById(R.id.tag_ll);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            title = (TextView) itemView.findViewById(R.id.tag_title);
            tagLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
