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
import com.duynam.demo1.model.search.Result;

import java.util.List;

public class SearchImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Result> resultList;
    Context context;
    int ITEM = 1;
    int LOAD = 2;
    private OnItemClickListener listener;

    public SearchImageAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_load, parent, false);
            return new Loadholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Result pos = resultList.get(position);
        if (holder instanceof ImageHolder){
            Glide.with(context).load(pos.getUrls().getSmall()).into(((ImageHolder) holder).img_lock);
            ((ImageHolder) holder).img_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(pos.getUrls().getSmall(), pos.getUrls().getFull());
                }
            });
        }else if (holder instanceof Loadholder){

        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == resultList.size() - 2){
            return LOAD;
        }else {
            return ITEM;
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(String displayurl, String downloadurl);
    }
}
