package com.duynam.demo1.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitImage {

    public static LockImage lockImage;
    public static LockImage getInstance(){
        if (lockImage == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.unsplash.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            lockImage = retrofit.create(LockImage.class);
        }
        return lockImage;
    }
}
