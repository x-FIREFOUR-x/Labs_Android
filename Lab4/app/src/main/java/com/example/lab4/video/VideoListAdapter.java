package com.example.lab4.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.R;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private ArrayList<VideoData> videoList;
    private Context context;

    public VideoListAdapter(ArrayList<VideoData> videoList, Context context){
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.video_recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoListAdapter.ViewHolder holder, int position) {
        VideoData videoData = videoList.get(position);
        holder.videoScreenImageView.setImageBitmap(videoData.getBitmap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                System.out.println(videoData.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView videoScreenImageView;

        public ViewHolder(View itemView){
            super(itemView);
            videoScreenImageView = itemView.findViewById(R.id.video_item_screen);
        }
    }
}
