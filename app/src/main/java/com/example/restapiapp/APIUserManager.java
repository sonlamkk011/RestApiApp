package com.example.restapiapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIUserManager {
//    String SERVER_URL = "https://630d8dc5b37c364eb705c668.mockapi.io/api/v1/";
    String SERVER_URL = "https://spring-autumn.herokuapp.com/api/v1/";

    @GET("users")
    Call<List<User>> getListData();

    @GET("users/{id}")
    Call<User> findById(@Path("id") long id);

    @POST("users")
    Call<User> save(@Body User user);

    @PUT("users/{id}")
    Call<User> update(@Path("id") long id, @Body User user);

    @DELETE("users/{id}")
    Call<User> delete(@Path("id") long id);
}
