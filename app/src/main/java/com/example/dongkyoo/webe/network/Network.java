package com.example.dongkyoo.webe.network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static final String BASE_URL = "http://192.168.56.1";
    private static final String PORT = "1234";
    private static final String WEB_APP_NAME = "WeBE";
    private static final Network network = new Network();

    public static final String SERVER_URL = BASE_URL + ":" + PORT + "/" + WEB_APP_NAME + "/";
    public static final String APPLICATION_JSON = "application/json; charset=utf-8";

    public static List<Object> makeObjectList(Object... objects) {
        List<Object> list = new ArrayList<>();
        for (Object object : objects) {
            list.add(object);
        }
        return list;
    }

    public static Network getInstance() {
        return network;
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RetrofitService retrofitService = retrofit.create(RetrofitService.class);
}
