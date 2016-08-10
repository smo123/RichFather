package com.rich.father.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.rich.father.R;
import com.rich.father.adapter.OrderAdapter;
import com.rich.father.app.App;
import com.rich.father.models.Order;
import com.rich.father.models.Orders;
import com.rich.father.utils.HttpAsyncTask;

import java.util.ArrayList;

public class OrderListFragment extends EaseChatFragment implements HttpAsyncTask.IHttpAsyncTask, RedPacketUtil.IOpenRedPacket {

    private static final int REQUIRE_TYPE_IN_MONEY_HISTORY = 1;//购买
    private static final int REQUIRE_TYPE_OPENT_RED_PACKET = 2;//购买
    private static final String PARAM_FROM_USER_ID = "fromUserId";

    private Button btnBack;
    private ListView listViewOrder;
    private OrderAdapter orderAdapter;

    private FragmentActivity activity;
    private int getRequireType;
    private String fromUserId;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Orders orders;
    private ArrayList<Order> ordersArrayList;

    public static OrderListFragment newInstance(String fromUserId) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_FROM_USER_ID, fromUserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragmentManager = activity.getSupportFragmentManager();//Fragment管理器
        if (getArguments() != null) {
            fromUserId = getArguments().getString(PARAM_FROM_USER_ID);
        }
        HttpAsyncTask.getInstance(this, REQUIRE_TYPE_IN_MONEY_HISTORY, fromUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        btnBack = (Button)v.findViewById(R.id.btn_back);
        listViewOrder = (ListView)v.findViewById(R.id.list_order);
        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order)parent.getItemAtPosition(position);
                //String red_packet_id = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_ID);
                //String red_packet_receiver_id = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_RECEIVER_ID);
                String red_packet_id = order.getMoneyID();
                String red_packet_receiver_id = order.getToUserId();
                EMMessage message = EMMessage.createTxtSendMessage("领红包", red_packet_receiver_id);
                message.setAttribute(RedPacketConstant.EXTRA_RED_PACKET_ID, red_packet_id);//订单号
                RedPacketUtil.openRedPacket(activity, OrderListFragment.this, EaseConstant.CHATTYPE_SINGLE, message);
            }
        });
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
                fromUserId = (String) params[1];
                orders = App.inMoneyHistory(activity, App.IN_MONEY_HISTORY, fromUserId);
                break;
            case REQUIRE_TYPE_OPENT_RED_PACKET:
                String moneyID = (String) params[1];
                orders = App.updateRedPacket(activity, App.OPEN_RED_PACKET, moneyID);
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
                if(orders != null){
                    ordersArrayList = orders.getData();
                    orderAdapter = new OrderAdapter(activity, ordersArrayList);
                    listViewOrder.setAdapter(orderAdapter);
                }
                break;
            case REQUIRE_TYPE_OPENT_RED_PACKET:
                if(orders != null){
                    App.log(TAG, "----------orders------->"+orders.getMsg());
                }
                break;
        }
    }

    @Override
    public void open(String fromUserId, String moneyAmount, String moneyID) {
        HttpAsyncTask.getInstance(this, REQUIRE_TYPE_OPENT_RED_PACKET, moneyID);
    }
}
