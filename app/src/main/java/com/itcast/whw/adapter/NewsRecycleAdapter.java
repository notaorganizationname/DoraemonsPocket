package com.itcast.whw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itcast.whw.R;
import com.itcast.whw.activity.recent_news.News;

import java.util.List;

/**
 * 新闻数据处理的Adapter
 */
public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> {
    private List<News> newsList;
    private Context mContext;

    public NewsRecycleAdapter(List<News> newsList , Context context){
        this.mContext=context;
        this.newsList=newsList;
    }
    @Override
    public NewsRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View news_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycle_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(news_item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecycleAdapter.ViewHolder holder, int position) {
        if(newsList==null||newsList.isEmpty()){
            return;
        }
        News news = newsList.get(position%newsList.size());
        holder.news_recycle_title.setText(news.getNews_title());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView news_recycle_img;
        private TextView news_recycle_title;
        private View news_item;
        public ViewHolder(View itemView) {
            super(itemView);
            news_item = itemView;
            news_recycle_img= itemView.findViewById(R.id.news_recycle_img);
            news_recycle_title= itemView.findViewById(R.id.news_recycle_title);
        }
    }
}
