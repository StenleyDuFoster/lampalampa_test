package com.stenleone.testl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("/")
    Call<List<Post>> getPosts();
}