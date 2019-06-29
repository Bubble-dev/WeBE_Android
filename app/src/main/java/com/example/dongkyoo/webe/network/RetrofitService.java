package com.example.dongkyoo.webe.network;

import com.example.dongkyoo.webe.vos.Group;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST(Network.SERVER_URL + "/groups")
    Call<Group> requestCreateNewGroup(@Field("name") String name);


}
