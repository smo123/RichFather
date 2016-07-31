package com.rich.father.models;

import java.io.Serializable;

/**
 * Created by jinba on 2016/7/31.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -5551574951837111230L;

    private String	product_id;
    private String	product_imgUrl;
    private String	product_title;
    private String	product_price;
    private String	product_sales;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_imgUrl() {
        return product_imgUrl;
    }

    public void setProduct_imgUrl(String product_imgUrl) {
        this.product_imgUrl = product_imgUrl;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_sales() {
        return product_sales;
    }

    public void setProduct_sales(String product_sales) {
        this.product_sales = product_sales;
    }
}
