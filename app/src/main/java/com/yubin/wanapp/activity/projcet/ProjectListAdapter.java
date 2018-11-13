package com.yubin.wanapp.activity.projcet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yubin.wanapp.R;
import com.yubin.wanapp.activity.detail.TagDetailAdapter;
import com.yubin.wanapp.activity.home.ArticlesAdapter;
import com.yubin.wanapp.activity.home.OnCategoryOnClickListener;
import com.yubin.wanapp.activity.home.OnRecyclerViewItemOnClickListener;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.util.GlideLoader;
import com.yubin.wanapp.util.StringUtils;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ArticleDetailData> mList;
    private OnRecyclerViewItemOnClickListener listener;

    public ProjectListAdapter(Context context, List<ArticleDetailData> list){
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
        View view = inflater.inflate(R.layout.item_project_list, parent, false);
        return new ProjectListAdapter.ProjectViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ProjectListAdapter.ProjectViewHolder projectViewHolder = (ProjectListAdapter.ProjectViewHolder) holder;
        projectViewHolder.authorName.setText(mList.get(position).getAuthor());
        projectViewHolder.projectTitle.setText(mList.get(position).getTitle());
        projectViewHolder.projectDesc.setText(mList.get(position).getDesc());
        projectViewHolder.timeTv.setText(mList.get(position).getNiceDate());
        Glide.with(mContext).load(mList.get(position).getEnvelopePic()).into(projectViewHolder.imageView);
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

    class ProjectViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView projectTitle;
        TextView projectDesc;
        TextView authorName;
        TextView timeTv;

        OnRecyclerViewItemOnClickListener listener;




        public ProjectViewHolder(final View itemView, final OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            cardView = (CardView) itemView.findViewById(R.id.project_card);
            imageView = (ImageView) itemView.findViewById(R.id.item_project_iv);
            projectTitle = (TextView) itemView.findViewById(R.id.item_project_title_tv);
            projectDesc = (TextView) itemView.findViewById(R.id.item_project_list_content_tv);
            authorName = (TextView) itemView.findViewById(R.id.item_project_list_author_tv);
            timeTv = (TextView) itemView.findViewById(R.id.item_project_list_time_tv);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
