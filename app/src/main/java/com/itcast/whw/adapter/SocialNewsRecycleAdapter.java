package com.itcast.whw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itcast.whw.R;
import com.itcast.whw.activity.recent_news.ContentActivity;
import com.itcast.whw.activity.recent_news.News;

import java.util.List;

/**
 * 处理社会新闻数据的Adapter
 */
public class SocialNewsRecycleAdapter extends RecyclerView.Adapter<SocialNewsRecycleAdapter.ViewHolder> {
    private List<News.ResultBean.DataBean> newsList;
    private Context mContext;

    public SocialNewsRecycleAdapter(List<News.ResultBean.DataBean> newsList , Context context){
        this.mContext=context;
        this.newsList=newsList;
    }
    @Override
    public SocialNewsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View news_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(news_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SocialNewsRecycleAdapter.ViewHolder holder, int position) {
        if(newsList==null||newsList.isEmpty()){
            return;
        }
        News.ResultBean.DataBean newData = newsList.get(position);

        final String content_url = newData.getUrl();
        String imgUrl = newData.getThumbnail_pic_s();

        if(imgUrl!=null){
            //加载图片
            Glide.with(mContext).load(imgUrl).into(holder.news_recycle_img);
        }

        holder.news_recycle_title.setText(newData.getTitle());
        holder.news_author_name.setText(newData.getAuthor_name());
        String date = newData.getDate();
        holder.news_date.setText(date);

        //跳转到新闻详情界面
        holder.news_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) mContext;
                Intent intent = new Intent(mContext, ContentActivity.class);
                intent.putExtra("content_url",content_url);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView news_recycle_img;
        private TextView news_recycle_title;
        private TextView news_author_name;
        private TextView news_date;
        private View news_item;
        public ViewHolder(View itemView) {
            super(itemView);
            news_item = itemView;
            news_recycle_img= itemView.findViewById(R.id.social_news_img);
            news_recycle_title= itemView.findViewById(R.id.social_news_title);
            news_author_name= itemView.findViewById(R.id.socialNews_author_name);
            news_date= itemView.findViewById(R.id.social_news_date);
        }
    }
}
