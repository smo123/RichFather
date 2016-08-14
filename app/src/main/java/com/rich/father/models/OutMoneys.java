package com.rich.father.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jinba on 2016/8/14.
 */
public class OutMoneys implements Serializable{
    private static final long serialVersionUID = -530592334224426988L;
    private String status;
    private String msg;
    private ArrayList<OutMoney> data;

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

    public ArrayList<OutMoney> getData() {
        return data;
    }

    public void setData(ArrayList<OutMoney> data) {
        this.data = data;
    }
}
