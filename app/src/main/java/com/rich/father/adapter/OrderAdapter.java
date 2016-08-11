package com.rich.father.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rich.father.R;
import com.rich.father.models.Order;
import com.rich.father.widget.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        TextView tvUser = ViewHolder.get(convertView, R.id.tv_in_money_user);
        TextView tvAmount = ViewHolder.get(convertView, R.id.tv_in_money_amount);
        TextView tvTime = ViewHolder.get(convertView, R.id.tv_in_money_time);

        final Order order = data.get(position);
        if(order != null){
            String isOpen = order.getIsOpen();
            if(isOpen != null&&isOpen.equalsIgnoreCase("1")){
            }else {
                convertView.setBackgroundResource(R.drawable.btn_bg2);
            }
            tvUser.setText(order.getFromUserId());
            tvAmount.setText(order.getMoneyAmount());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(order.getSysdate());
                String strDate = format.format(date);
                tvTime.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

}