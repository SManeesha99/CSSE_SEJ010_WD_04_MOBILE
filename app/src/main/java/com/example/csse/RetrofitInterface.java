package com.example.csse;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {


    @POST("/user/login/")
    Call<Void> loginUser(@Body HashMap<String, String>map);

    @POST("/travel/setsource/")
    Call<Void> setSource(@Body HashMap<String, String>map);

    @POST("/travel/setdestination/")
    Call<Void> setDestination(@Body HashMap<String, String>map);
}