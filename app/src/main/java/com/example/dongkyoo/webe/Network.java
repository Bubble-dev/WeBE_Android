package com.example.dongkyoo.webe;

import java.util.ArrayList;
import java.util.List;

public class Network {

    public static final String BASE_URL = "http://192.168.56.1";
    public static final String PORT = "8080";
    public static final String WEB_APP_NAME = "WeBE";
    public static final String SERVER_URL = BASE_URL + ":" + PORT + "/" + WEB_APP_NAME;
    public static final String APPLICATION_JSON = "application/json; charset=utf-8";

    public interface OnNetworkProcessListener {
        void onSuccess(List<Object> resultList);
        void onFailure();
    }

    public static List<Object> makeObjectList(Object... objects) {
        List<Object> list = new ArrayList<>();
        for (Object object : objects) {
            list.add(object);
        }
        return list;
    }
}
