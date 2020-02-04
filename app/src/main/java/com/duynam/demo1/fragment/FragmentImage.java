package com.duynam.demo1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duynam.demo1.DetailsImageActivity;
import com.duynam.demo1.R;
import com.duynam.demo1.adapter.ImageAdapter;
import com.duynam.demo1.adapter.SearchImageAdapter;
import com.duynam.demo1.model.COmmonImage;
import com.duynam.demo1.model.Image;
import com.duynam.demo1.model.search.Result;
import com.duynam.demo1.model.search.Search;
import com.duynam.demo1.retrofit.RetrofitImage;
import com.duynam.demo1.utils.Constains;
import com.duynam.demo1.utils.FileCache;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentImage extends Fragment implements ImageAdapter.OnItemClickListener, SearchImageAdapter.OnItemClickListener {

    public RecyclerView rvListImage;
    public static final String client_id = "d0922c4e1ab6e3b4b611103f7c318c9bd3ebd9db42cc06cdd7afe7edea350078";
    private int page = 1;
    private int per_page = 20;
    private boolean loading;
    public boolean search;
    public List<COmmonImage> imageList;
    private ImageAdapter adapter;
    public GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        initView(view);
        imageList = new ArrayList<>();
        getImage(client_id, page, per_page);
        adapter = new ImageAdapter(getContext(), imageList);
        adapter.setListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case 2:
                        return 1;
                    case 1:
                        return 2;
                    default:
                        return -1;
                }
            }
        });
        rvListImage.setLayoutManager(gridLayoutManager);
        rvListImage.setAdapter(adapter);

        rvListImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;
                
                int currentPosition = gridLayoutManager.findLastVisibleItemPosition();
                int size = adapter.getItemCount();
                if (currentPosition < size - 2) {
                    page++;
                    loading = true;
                    adapter.addLoadMore();
                    loading = true;
                    if (search){
                        loadMoreSearch();
                    }else {
                        loadMore();
                    }
                }
            }
        });

        return view;
    }

    private List<COmmonImage> convertToCommonData(List<Image> imageList) {
        List<COmmonImage> cOmmonImages = new ArrayList<>();
        for (Image image : imageList) {
            COmmonImage cOmmonImage = new COmmonImage();
            cOmmonImage.setUrls(image.getUrls());
            cOmmonImages.add(cOmmonImage);
        }
        return cOmmonImages;
    }

    private List<COmmonImage> convertToCommonDats(List<Result> list) {
        List<COmmonImage> cOmmonImages = new ArrayList<>();
        for (Result image : list) {
            COmmonImage cOmmonImage = new COmmonImage();
            cOmmonImage.setUrls(image.getUrls());
            cOmmonImages.add(cOmmonImage);
        }
        return cOmmonImages;
    }

    private void loadMore() {
        getImage(client_id, page, per_page);
    }

    private void loadMoreSearch() {
        String query = FileCache.readCache(getContext(), Constains.cacheName);
        search(query);
    }

    private void initView(View view) {
        rvListImage = view.findViewById(R.id.rv_listImage);
    }


    private void getImage(String client_id, int page, int per_page) {
        RetrofitImage.getInstance().getImage(client_id, page, per_page).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadMore();
                        imageList.addAll(convertToCommonData(response.body()));
                        adapter.notifyDataSetChanged();
                        loading = false;
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                loading = false;
            }
        });
    }

    @Override
    public void OnItemClick(String displayurl, String downloadurl) {
        Intent intent = new Intent(getContext(), DetailsImageActivity.class);
        intent.putExtra("show", displayurl);
        intent.putExtra("down", downloadurl);
        startActivity(intent);
    }

    public void search(String query) {
        RetrofitImage.getInstance().getSearchImage(client_id, page, per_page, query).enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadMore();
                        imageList.addAll(convertToCommonDats(response.body().getResults()));
                        //adapter.setSearchList(convertToCommonDats(resultList));
                        adapter.notifyDataSetChanged();
                        loading = false;
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                loading = false;
            }
        });
    }
}
