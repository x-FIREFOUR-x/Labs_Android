package com.example.lab4.audio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.R;

import java.util.ArrayList;

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {
    private ArrayList<AudioData> audioList;
    private Context context;

    public AudioListAdapter(ArrayList<AudioData> audioList, Context context){
        this.audioList = audioList;
        this.context = context;
    }

    @Override
    public AudioListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.audio_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioData audioData = audioList.get(position);
        holder.titleTextView.setText(audioData.getTitle());

        if(AudioPlayer.getCurrentIndex()==position){
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
        }else {
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AudioPlayer.getInstance().reset();
                AudioPlayer.setCurrentIndex(position);

                Intent intent = new Intent(context, AudioPlayerActivity.class);
                intent.putExtra("LIST", audioList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.audio_item_title);
            iconImageView = itemView.findViewById(R.id.audio_item_icon);
        }
    }
}
