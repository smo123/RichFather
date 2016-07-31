package com.rich.father.utils;

import com.google.gson.Gson;
import com.rich.father.models.RequireResult;

/**
 * Created by jinba on 2016/7/26.
 */
public class JsonParser {

    public static final String TAG = JsonParser.class.getName();

    //解析请求结果
    public static RequireResult parserRequireResult(String jsonData){
        RequireResult requireResult = null;
        Gson gson = new Gson();
        requireResult = gson.fromJson(jsonData, RequireResult.class);
        return requireResult;
    }

}
