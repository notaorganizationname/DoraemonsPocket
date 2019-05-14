package com.itcast.whw.activity.recent_news;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itcast.whw.R;
import com.itcast.whw.adapter.NewsRecycleAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsActivity extends AppCompatActivity {

    private static final int SUCCESSED = 1;
    private boolean isAutoPlay;     //标识是否自动图片轮播
    private RecyclerView news_recycle;
    private OkHttpClient okHttpClient;
    private List<News.ResultBean.DataBean> newsData;

    //给recycleview设置数据
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == SUCCESSED) {
                NewsRecycleAdapter newsRecycleAdapter = new NewsRecycleAdapter(newsData, NewsActivity.this);
                news_recycle.setAdapter(newsRecycleAdapter);
                //可以左右滑动
                news_recycle.scrollToPosition(newsData.size());
                newsRecycleAdapter.notifyDataSetChanged();
            }
            return false;
        }
    });
    private Toolbar news_toolbar;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();

        initNewsData();

        //实现图片轮播
        ImgRandom();

    }

    /**
     * 实现图片轮播
     */
    private void ImgRandom() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //得到当前RecyclerView第一个能看到的item的位置
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                position++;
                //滑动到下一个item
                news_recycle.smoothScrollToPosition(position);
                mHandler.postDelayed(this,4200);
            }
        };
        mHandler.postDelayed(runnable,4000);


        news_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {

            //监听recyclerView的状态
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == recyclerView.SCROLL_STATE_IDLE) {    //静止状态
                    if (isAutoPlay){
                        //开启线程
                        mHandler.postDelayed(runnable, 4000);
                        isAutoPlay = false;
                    }
                } else if (newState == recyclerView.SCROLL_STATE_DRAGGING) {   //拖拽状态
                    //停止线程
                    mHandler.removeCallbacks(runnable);
                    isAutoPlay = true;
                } else if (newState == recyclerView.SCROLL_STATE_SETTLING) {   //手指离开后的惯性滚动状态
                    Log.d("NewsActivity", "setting:" + newState + "---recycleView手指离开后的惯性滚动状态");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    //初始化新闻数据
    private void initNewsData() {
        //联网获取新闻数据
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?type=top&key=c2b7616d5db40a27bd5d99240aa719c7")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(NewsActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    String newsJson = response.body().string();
                    Log.d("NewsActivity", newsJson);

                    Gson gson = new Gson();
                    News news = gson.fromJson(newsJson, News.class);
                    newsData = news.getResult().getData();
                    Log.d("NewsActivity", "newsData.size():" + newsData.size());
                    if (newsData != null) {
                        for (News.ResultBean.DataBean n : newsData) {
                            Log.d("NewsActivity", n.getTitle());
                        }
                        Message message = Message.obtain();
                        message.what = SUCCESSED;
                        mHandler.sendMessage(message);
                        response.close();
                    }
                }

            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        news_recycle = (RecyclerView) findViewById(R.id.news_recycle);
        linearLayoutManager = new MyLinearLayoutManager(NewsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        news_recycle.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(news_recycle);
        news_toolbar = (Toolbar) findViewById(R.id.news_toolbar);

        setSupportActionBar(news_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //解决轮播图片太快
    public class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        // 计算滚动速度。如果返回值是3毫秒，这表示着滚动1000像素需要3秒。
        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 0.3f; //返回0.3
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
