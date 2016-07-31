package com.rich.father.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.models.Product;
import com.rich.father.widget.ViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jinba on 2016/7/31.
 */
public class ProductAdapter extends BaseAdapter {

    private static final String TAG = ProductAdapter.class.getName();

    HashMap<Integer,View> viewHashMap = new HashMap<>();
    private Context context;
    private LayoutInflater inflater;
    private List<Product> data;

    public ProductAdapter(Context context, List<Product> data) {
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
            convertView = inflater.inflate(R.layout.item_news, parent, false);
            viewHashMap.put(position, convertView);
        }else{
            convertView = viewHashMap.get(position);
        }
        ImageView ivProductIcon = ViewHolder.get(convertView, R.id.iv_product_icon);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_product_title);
        TextView tvPrice = ViewHolder.get(convertView, R.id.tv_product_price);
        TextView tvSales = ViewHolder.get(convertView, R.id.tv_product_sales);

        final Product product = data.get(position);
        if(product != null){
            Glide.with(context).load(App.BASE_URL+product.getProduct_imgUrl()).centerCrop().into(ivProductIcon);
            tvTitle.setText(product.getProduct_title());
            tvPrice.setText(product.getProduct_price());
            tvSales.setText(product.getProduct_sales());
        }
        return convertView;
    }

}