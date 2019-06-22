package com.example.photogrid.DownlodPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PhotoServices {

    //https://api.thecatapi.com/v1/images/search
    //https://api.thecatapi.com/v1/images/search?breed_ids=beng
    @GET("/v1/images/search")
    Call<List<PhotoCat>> get(@Header("x-api-key") String tok);






}
