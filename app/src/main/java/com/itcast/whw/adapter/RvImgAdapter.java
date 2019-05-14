package com.itcast.whw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itcast.whw.R;
import com.itcast.whw.activity.net_img.ImgDisplayActivity;

import java.util.ArrayList;

public class RvImgAdapter extends RecyclerView.Adapter<RvImgAdapter.MyViewHolder> {

    private ArrayList<String> imgs;
    private Context context;

    public RvImgAdapter(ArrayList<String> imgs, Context context){
        this.imgs = imgs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View inflate = LayoutInflater.from(context).inflate(R.layout.item_imgs, null, false);
        MyViewHolder viewHolder = new MyViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        Glide.with(context).load(imgs.get(i)).into(myViewHolder.imageView);

        final int position = i;
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                Intent intent = new Intent(context, ImgDisplayActivity.class);
                intent.putExtra("imgUrl", imgs.get(position));
                intent.putStringArrayListExtra("imgs", imgs);
                intent.putExtra("position", i);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.iv_net);
        }
    }

}
