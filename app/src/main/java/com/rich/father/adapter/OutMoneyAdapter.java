package com.rich.father.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rich.father.R;
import com.rich.father.models.OutMoney;
import com.rich.father.widget.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by jinba on 2016/7/31.
 */
public class OutMoneyAdapter extends BaseAdapter {

    private static final String TAG = OutMoneyAdapter.class.getName();

    HashMap<Integer,View> viewHashMap = new HashMap<>();
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutMoney> data;

    public OutMoneyAdapter(Context context, ArrayList<OutMoney> data) {
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
            convertView = inflater.inflate(R.layout.item_history, parent, false);
            viewHashMap.put(position, convertView);
        }else{
            convertView = viewHashMap.get(position);
        }
        TextView tvAmount = ViewHolder.get(convertView, R.id.tv_out_money_amount);
        TextView tvApplyTime = ViewHolder.get(convertView, R.id.tv_out_money_apply_time);
        TextView tvSuccessTime = ViewHolder.get(convertView, R.id.tv_out_money_success_time);
        TextView tvStatus = ViewHolder.get(convertView, R.id.tv_out_money_status);

        final OutMoney outMoney = data.get(position);
        if(outMoney != null){
            String isOpen = outMoney.getFoase();
            if(isOpen != null&&isOpen.equalsIgnoreCase("1")){
            }else {
                convertView.setBackgroundResource(R.drawable.btn_bg2);
            }
            tvAmount.setText(outMoney.getW_price());
            tvApplyTime.setText(outMoney.getW_data());
            tvSuccessTime.setText(outMoney.getW_sdata());
            //tvStatus.setText(outMoney.get);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(outMoney.getW_sdata());
                String strDate = format.format(date);
                tvSuccessTime.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

}