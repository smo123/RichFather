package com.rich.father.models;

import java.io.Serializable;

/**
 * Created by jinba on 2016/7/28.
 */
public class RequireResult implements Serializable{
    private static final long serialVersionUID = -5305923315442650988L;

    private String status;
    private String msg;

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
}
