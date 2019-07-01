package com.example.dongkyoo.webe.network;

import com.example.dongkyoo.webe.vos.Group;
import com.example.dongkyoo.webe.vos.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("groups")
    Call<Group> requestCreateNewGroup(@Field("name") String name);

    @GET("users/login")
    Call<User> login(@Query("id") String id, @Query("password") String password);

    @POST("users/signUp")
    Call<User> signUp(@Body User user);
}
