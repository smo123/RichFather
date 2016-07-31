package com.rich.father.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rich.father.R;
import com.rich.father.adapter.ProductAdapter;
import com.rich.father.app.App;
import com.rich.father.models.Product;
import com.rich.father.models.Products;
import com.rich.father.utils.HttpAsyncTask;

import java.util.ArrayList;

public class FirstFragment extends BaseFragment implements HttpAsyncTask.IHttpAsyncTask{

    public static final String TAG = FirstFragment.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listViewProduct;
    private ProductAdapter productAdapter;

    private String mParam1;
    private String mParam2;

    private Products products;
    private ArrayList<Product> productArrayList;

    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        HttpAsyncTask.getInstance(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        listViewProduct = (ListView)v.findViewById(R.id.list_product);
        return v;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object... params) {
        products = App.getProducts(getActivity(), App.GET_PRODUCTS);
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        App.log(TAG, "-----111------->"+products);
        if(products != null){
            productArrayList = products.getData();
            App.log(TAG, "-----2222------->"+productArrayList);
            productAdapter = new ProductAdapter(getActivity(), productArrayList);
            listViewProduct.setAdapter(productAdapter);
        }
    }
}
