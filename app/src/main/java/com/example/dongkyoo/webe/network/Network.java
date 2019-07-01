package com.example.dongkyoo.webe.network;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static final String BASE_URL = "http://172.30.1.19";
    private static final String TEST_URL = "http://localhost";
    public static final int PORT = 1234;
    private static final String WEB_APP_NAME = "WeBE";

    public static final String SERVER_URL;
    static {
        if (Build.FINGERPRINT.equals("robolectric")) {
            SERVER_URL = TEST_URL + ":" + PORT + "/" + WEB_APP_NAME + "/";
        } else {
            SERVER_URL = BASE_URL + ":" + PORT + "/" + WEB_APP_NAME + "/";
        }
    }
    public static final String APPLICATION_JSON = "application/json; charset=utf-8";

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final Network network = new Network();

    public static Network getInstance() {
        return network;
    }

    public RetrofitService retrofitService = retrofit.create(RetrofitService.class);
}
