package com.duynam.demo1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duynam.demo1.R;
import com.duynam.demo1.model.ListPathFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalImageAdapter extends RecyclerView.Adapter<LocalImageAdapter.ViewHolder> {

    ArrayList<String> f;
    Context context;

    public LocalImageAdapter(ArrayList<String> f, Context context) {
        this.f = f;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
        holder.img_local.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        return f.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_local;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_local = itemView.findViewById(R.id.img_lock);
        }
    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
