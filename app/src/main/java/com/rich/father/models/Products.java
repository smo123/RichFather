package com.rich.father.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jinba on 2016/7/31.
 */
public class Products implements Serializable {

    private static final long serialVersionUID = -4536694203156144556L;
    private String status;
    private String msg;
    private ArrayList<Product> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }
}
