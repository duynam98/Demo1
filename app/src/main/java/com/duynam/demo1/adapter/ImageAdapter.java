package com.duynam.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duynam.demo1.R;
import com.duynam.demo1.holder.ImageHolder;
import com.duynam.demo1.holder.Loadholder;
import com.duynam.demo1.model.COmmonImage;
import com.duynam.demo1.utils.Constains;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<COmmonImage> imageList;
    private OnItemClickListener listener;


    public ImageAdapter(Context context, List<COmmonImage> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constains.ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageHolder(view);
        } else if (viewType == Constains.LOAD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_load, parent, false);
            return new Loadholder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        COmmonImage pos = imageList.get(position);
        if (holder instanceof ImageHolder) {
            Glide.with(context).load(pos.getUrls().getRegular()).into(((ImageHolder) holder).img_lock);
            ((ImageHolder) holder).img_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(pos.getUrls().getRegular(), pos.getUrls().getFull());
                }
            });
        } else if (holder instanceof Loadholder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (imageList.get(position) == null) {
            return Constains.LOAD;
        } else return Constains.ITEM;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addLoadMore() {
        imageList.add(null);
    }

    public void removeLoadMore() {
        if (imageList == null || imageList.size() == 0)
            return;
        if (imageList.get(imageList.size() - 1) == null) {
            imageList.remove(imageList.size() - 1);
            notifyItemRemoved(imageList.size() - 1);
        }
    }

    public void setSearchList(List<COmmonImage> resultList) {
        imageList = resultList;
    }

    public interface OnItemClickListener {
        void OnItemClick(String displayurl, String downloadurl);
    }

}
