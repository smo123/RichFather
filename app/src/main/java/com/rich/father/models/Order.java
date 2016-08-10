package com.rich.father.models;

import java.io.Serializable;

/**
 * Created by jinba on 2016/7/28.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = -5305923315224426988L;

    private String fromUserId;
    private String toUserId;
    private String moneyAmount;
    private String moneyID;
    private String moneyMsgDirect;
    private String takenMoney;
    private String sysdate;
    private String isOpen;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMoneyID() {
        return moneyID;
    }

    public void setMoneyID(String moneyID) {
        this.moneyID = moneyID;
    }

    public String getMoneyMsgDirect() {
        return moneyMsgDirect;
    }

    public void setMoneyMsgDirect(String moneyMsgDirect) {
        this.moneyMsgDirect = moneyMsgDirect;
    }

    public String getTakenMoney() {
        return takenMoney;
    }

    public void setTakenMoney(String takenMoney) {
        this.takenMoney = takenMoney;
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }
}
