package com.rich.father.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rich.father.R;
import com.rich.father.models.Order;
import com.rich.father.widget.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jinba on 2016/7/31.
 */
public class OrderAdapter extends BaseAdapter {

    private static final String TAG = OrderAdapter.class.getName();

    HashMap<Integer,View> viewHashMap = new HashMap<>();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Order> data;

    public OrderAdapter(Context context, ArrayList<Order> data) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(viewHashMap.get(position)==null){
            convertView = inflater.inflate(R.layout.item_order, parent, false);
            viewHashMap.put(position, convertView);
        }else{
            convertView = viewHashMap.get(position);
        }
        ImageView ivProductIcon = ViewHolder.get(convertView, R.id.iv_product_icon);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_product_title);
        TextView tvPrice = ViewHolder.get(convertView, R.id.tv_product_price);
        TextView tvSales = ViewHolder.get(convertView, R.id.tv_product_sales);

        final Order order = data.get(position);
        if(order != null){
            tvTitle.setText(order.getFromUserId());
            tvPrice.setText(order.getMoneyAmount());
            tvSales.setText(order.getDate());
        }
        return convertView;
    }

}