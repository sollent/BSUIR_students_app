package com.example.diana.myapplication.Common;

import com.example.diana.myapplication.Remote.IMyAPI;
import com.example.diana.myapplication.Remote.RetrofitClient;

public class Common {

    private static final String BASE_URL = "http://10.0.2.2:8000";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IMyAPI.class);
    }

}
