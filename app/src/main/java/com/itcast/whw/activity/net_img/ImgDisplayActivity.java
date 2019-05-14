package com.itcast.whw.activity.net_img;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itcast.whw.R;


public class ImgDisplayActivity extends AppCompatActivity {

    private ImageView zack_iv_display;

//    private ArrayList<String> imgs = new ArrayList<>();
//
//    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_display);
        initView();
        initData();
//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(zack_rv_display);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        zack_rv_display.setLayoutManager(layoutManager);
//        RvDisplayAdapter adapter = new RvDisplayAdapter(imgs, this);
//        zack_rv_display.setAdapter(adapter);
    }

    private void initView() {
        zack_iv_display = findViewById(R.id.zack_iv_display);
    }

    private void initData(){
        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(zack_iv_display);
//        imgs = intent.getStringArrayListExtra("imgs");
//        position = intent.getIntExtra("position", -1);
    }
}
