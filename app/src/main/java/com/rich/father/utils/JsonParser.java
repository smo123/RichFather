package com.rich.father.utils;

import com.google.gson.Gson;
import com.rich.father.models.Orders;
import com.rich.father.models.Products;
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

    //解析产品
    public static Products parserProducts(String jsonData){
        Products products = null;
        Gson gson = new Gson();
        products = gson.fromJson(jsonData, Products.class);
        return products;
    }

    //解析订单
    public static Orders parserOrders(String jsonData){
        Orders orders = null;
        Gson gson = new Gson();
        orders = gson.fromJson(jsonData, Orders.class);
        return orders;
    }

}
