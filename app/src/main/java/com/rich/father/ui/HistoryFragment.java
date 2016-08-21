package com.rich.father.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.rich.father.R;
import com.rich.father.adapter.OutMoneyAdapter;
import com.rich.father.app.App;
import com.rich.father.models.OutMoney;
import com.rich.father.models.OutMoneys;
import com.rich.father.utils.HttpAsyncTask;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment implements HttpAsyncTask.IHttpAsyncTask {

    public static final String TAG = HistoryFragment.class.getName();
    private static final int REQUIRE_TYPE_IN_MONEY_HISTORY = 1;
    private int getRequireType;

    private Button btnBack;
    private ListView outMoneyListView;

    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private OutMoneyAdapter outMoneyAdapter;
    private OutMoneys outMoneys;
    private ArrayList<OutMoney> outMoneysArrayList;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragmentManager = activity.getSupportFragmentManager();//Fragment管理器
        HttpAsyncTask.getInstance(this, REQUIRE_TYPE_IN_MONEY_HISTORY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        btnBack = (Button)v.findViewById(R.id.btn_back);
        outMoneyListView = (ListView)v.findViewById(R.id.list_history);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        return v;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object... params) {
        getRequireType = (Integer)params[0];
        switch (getRequireType) {
            case REQUIRE_TYPE_IN_MONEY_HISTORY:
                String phone = App.getData4SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
                outMoneys = App.outMoneyHistory(activity, App.OUT_MONEY_HISTORY, phone);
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        switch (getRequireType){
            case REQUIRE_TYPE_IN_MONEY_HISTORY:
                if(outMoneys != null){
                    outMoneysArrayList = outMoneys.getData();
                    outMoneyAdapter = new OutMoneyAdapter(activity, outMoneysArrayList);
                    outMoneyListView.setAdapter(outMoneyAdapter);
                }
                break;
        }
    }
}
