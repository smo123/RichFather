package com.rich.father.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jinba on 2016/7/28.
 */
public class Orders implements Serializable {
    private static final long serialVersionUID = -5305923311224426988L;

    private String status;
    private String msg;
    private ArrayList<Order> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Order> getData() {
        return data;
    }

    public void setData(ArrayList<Order> data) {
        this.data = data;
    }
}
