package com.itcast.whw.activity.recent_news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itcast.whw.R;

public class ContentActivity extends AppCompatActivity {

    private WebView news_web_view;
    private String content_url;
    private Toolbar news_content_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        content_url = intent.getStringExtra("content_url");
        initView();
    }

    private void initView() {
        news_web_view = (WebView) findViewById(R.id.news_web_view);
        news_web_view.getSettings().setJavaScriptEnabled(true);
        news_web_view.setWebViewClient(new WebViewClient());
        if (content_url != null) {
            news_web_view.loadUrl(content_url);
        }

        news_content_bar = (Toolbar) findViewById(R.id.news_content_bar);

        setSupportActionBar(news_content_bar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
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
