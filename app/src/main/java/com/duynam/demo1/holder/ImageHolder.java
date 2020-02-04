package com.duynam.demo1.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duynam.demo1.R;

public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView img_lock;
    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        img_lock = itemView.findViewById(R.id.img_lock);
    }
}
