package com.duynam.demo1.retrofit;

import com.duynam.demo1.model.Image;
import com.duynam.demo1.model.search.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LockImage {

    @GET("/photos")
    Call<List<Image>> getImage(@Query("client_id") String client_id, @Query("page") int page, @Query("per_page") int per_page);

    ///search/photos?page=1&query=office
    @GET("search/photos")
    Call<Search> getSearchImage(@Query("client_id") String client_id, @Query("page") int page, @Query("per_page") int per_page, @Query("query") String query);
}
