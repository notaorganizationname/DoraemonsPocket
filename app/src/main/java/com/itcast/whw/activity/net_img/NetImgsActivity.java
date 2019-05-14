package com.itcast.whw.activity.net_img;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.itcast.whw.R;
import com.itcast.whw.adapter.RvImgAdapter;

import java.util.ArrayList;

public class NetImgsActivity extends AppCompatActivity {

    private RecyclerView zack_rv_imgs;

    private ArrayList<String> imgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_imgs);
        initView();
        initData();
        RvImgAdapter adapter = new RvImgAdapter(imgs, this);
        zack_rv_imgs.setAdapter(adapter);
        zack_rv_imgs.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initView() {
        zack_rv_imgs = (RecyclerView) findViewById(R.id.zack_rv_imgs);
    }

    private void initData() {
        Intent intent = getIntent();
        imgs = intent.getStringArrayListExtra("imgs");
    }
}
