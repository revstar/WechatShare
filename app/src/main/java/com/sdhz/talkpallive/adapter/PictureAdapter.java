package com.sdhz.talkpallive.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sdhz.talkpallive.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create on 2019/4/23 17:35
 * author revstar
 * Email 1967919189@qq.com
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {

    private Context mContext;
    List<Integer> list;


    public PictureAdapter(Context context, List<Integer> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.picture_item,null);
        PictureViewHolder viewHolder=new PictureViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.igImage.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class  PictureViewHolder extends RecyclerView.ViewHolder{

        ImageView igImage;
        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            igImage=itemView.findViewById(R.id.ig_image);
        }
    }
}
