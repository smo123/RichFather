package com.rich.father.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.models.OutMoneys;
import com.rich.father.utils.HttpAsyncTask;

public class OutMoneyFragment extends BaseFragment implements View.OnClickListener, HttpAsyncTask.IHttpAsyncTask {

    public static final String TAG = OutMoneyFragment.class.getName();
    private static final int REQUIRE_TYPE_OUT_MONEY = 1;//提现
    private int getRequireType;

    private Button btnBack, btnHistory, btnConfirm;
    private EditText editAmount;

    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private OutMoneys outMoneys;

    public static OutMoneyFragment newInstance() {
        OutMoneyFragment fragment = new OutMoneyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragmentManager = activity.getSupportFragmentManager();//Fragment管理器
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_out_money, container, false);
        btnBack = (Button)v.findViewById(R.id.btn_back);
        btnHistory = (Button)v.findViewById(R.id.btn_history);
        btnConfirm = (Button)v.findViewById(R.id.btn_confirm);
        editAmount = (EditText)v.findViewById(R.id.edit_amount);
        btnBack.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                fragmentManager.popBackStack();
                break;
            case R.id.btn_history:
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_main_content, historyFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn_confirm:
                HttpAsyncTask.getInstance(this, REQUIRE_TYPE_OUT_MONEY);
                break;
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object... params) {
        getRequireType = (Integer)params[0];
        switch (getRequireType) {
            case REQUIRE_TYPE_OUT_MONEY:
                String phone = App.getData4SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
                String amount = editAmount.getText()+"";
                outMoneys = App.outMoney(activity, App.OUT_MONEY, phone, amount);
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        switch (getRequireType) {
            case REQUIRE_TYPE_OUT_MONEY:
                if(outMoneys != null){
                    App.log(TAG, "------------outMoneys----------->"+outMoneys.getMsg());
                }
                break;
        }
    }

}
