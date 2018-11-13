package com.yubin.wanapp.activity.favourite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.detail.TagDetailAdapter;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.FavoriteArticleDetailData;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<FavoriteArticleDetailData> mList;
    private OnRecyclerViewItemOnClickListener listener;

    public FavouriteAdapter(Context context, List<FavoriteArticleDetailData> list){
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        mList = list;
    }

    public void updateData(List<FavoriteArticleDetailData> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_favourite, parent, false);
        return new FavouriteAdapter.FavouriteViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final FavouriteAdapter.FavouriteViewHolder favViewHolder = (FavouriteAdapter.FavouriteViewHolder) holder;
        favViewHolder.authorName.setText(mList.get(position).getAuthor());
        favViewHolder.title.setText(mList.get(position).getTitle());
        favViewHolder.articleTime.setText(mList.get(position).getNiceDate());
        favViewHolder.chapterName.setText(mList.get(position).getChapterName().trim());

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

    class FavouriteViewHolder extends RecyclerView.ViewHolder{

        TextView authorName;
        TextView title;
        TextView chapterName;
        TextView articleTime;
        LinearLayout favLL;
        OnRecyclerViewItemOnClickListener listener;




        public FavouriteViewHolder(final View itemView, final OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            favLL = (LinearLayout) itemView.findViewById(R.id.fav_ll);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            chapterName = (TextView) itemView.findViewById(R.id.chapter_name);
            title = (TextView) itemView.findViewById(R.id.fav_title);
            articleTime = (TextView) itemView.findViewById(R.id.article_time);
            favLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
