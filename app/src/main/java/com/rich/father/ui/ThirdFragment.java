package com.rich.father.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketsdk.callback.PacketDetailCallback;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.easemob.redpacketsdk.presenter.impl.PacketDetailPresenter;
import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.models.Orders;
import com.rich.father.utils.HttpAsyncTask;

import java.util.HashMap;

public class ThirdFragment extends EaseChatFragment implements HttpAsyncTask.IHttpAsyncTask, View.OnClickListener{

    private static final int REQUIRE_TYPE_BUY = 1;//购买
    private static final int REQUIRE_TYPE_IN_MONEY = 2;//购买
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code

    private FragmentActivity activity;
    private Button btnInMoney, btnOutMoney, btnGetRP, btnChange, btnLogout;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private int getRequireType;
    private String mParam1;
    private String mParam2;

    private Orders orders;

    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        fragmentManager = activity.getSupportFragmentManager();//Fragment管理器
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        btnInMoney = (Button)view.findViewById(R.id.btn_in_money);
        btnOutMoney = (Button)view.findViewById(R.id.btn_out_money);
        btnGetRP = (Button)view.findViewById(R.id.btn_get_red_package);
        btnChange = (Button)view.findViewById(R.id.btn_my_change);
        btnLogout = (Button)view.findViewById(R.id.btn_logout);
        btnInMoney.setOnClickListener(this);
        btnOutMoney.setOnClickListener(this);
        btnGetRP.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        String phone = App.getData4SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
        if(phone != null&&phone.equalsIgnoreCase("18775769566")){
            btnChange.setVisibility(View.VISIBLE);
            btnGetRP.setText(getResources().getString(R.string.btn_get_red_package));
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SEND_RED_PACKET:
                    if (data != null){
                        //获取参数
                        String moneyID = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_ID);
                        String fromUserId = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_FROM_USER_ID);
                        String toUserId = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_ID);

                        //保存参数
                        App.saveData2SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_ID, moneyID);
                        App.saveData2SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_FROM_USER_ID, fromUserId);
                        App.saveData2SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_RECEIVER_ID, toUserId);

                        /*PacketDetailPresenter presenter = new PacketDetailPresenter(mContext, this);
                          presenter.getMoneyDetail(mRedPacketInfo, 0, PageUtil.PAGE_LIMIT);
                          调用这个方法 PageUtil.PAGE_LIMIT 这个 是分页用的 你单聊 可以传个 大于0的数字
                        * this 要传一个callback
                        * mRedPacketInfo 里设置一下红包id 就可以了 */
                        PacketDetailPresenter presenter = new PacketDetailPresenter(activity, new PacketDetailCallback() {
                            @Override
                            public void showSinglePacketDetail(RedPacketInfo redPacketInfo) {
                                App.log("xu", "fromUserId---->"+redPacketInfo.fromUserId);
                                App.log("xu", "toUserId---->"+redPacketInfo.toUserId);
                                App.log("xu", "moneyAmount---->"+redPacketInfo.moneyAmount);
                                App.log("xu", "moneyID---->"+redPacketInfo.moneyID);
                                App.log("xu", "moneyMsgDirect---->"+redPacketInfo.moneyMsgDirect);
                                App.log("xu", "takenMoney---->"+redPacketInfo.takenMoney);
                                HttpAsyncTask.getInstance(ThirdFragment.this, REQUIRE_TYPE_IN_MONEY, App.IN_MONEY, redPacketInfo.fromUserId, redPacketInfo.toUserId,
                                        redPacketInfo.moneyAmount, redPacketInfo.moneyID, redPacketInfo.moneyMsgDirect, redPacketInfo.takenMoney);
                            }

                            @Override
                            public void showGroupPacketDetail(HashMap<String, Object> hashMap, String s, String s1, String s2) {

                            }

                            @Override
                            public void showDetailError(String s, String s1) {

                            }
                        });
                        RedPacketInfo redPacketInfo = new RedPacketInfo();
                        redPacketInfo.moneyID = moneyID;
                        redPacketInfo.moneyMsgDirect = RPConstant.MESSAGE_DIRECT_SEND;//方向是接收红包
                        redPacketInfo.chatType = RPConstant.CHATTYPE_SINGLE;
                        presenter.getMoneyDetail(redPacketInfo, 0, 1);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_in_money:
                HttpAsyncTask.getInstance(ThirdFragment.this, REQUIRE_TYPE_BUY);
                break;
            case R.id.btn_out_money:
                OutMoneyFragment outMoneyFragment = OutMoneyFragment.newInstance();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_main_content, outMoneyFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn_get_red_package:
                String phone = App.getData4SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
                if(!phone.equalsIgnoreCase("-1")){
                    OrderListFragment orderListFragment = OrderListFragment.newInstance(phone);
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.layout_main_content, orderListFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    App.toast(activity, getResources().getString(R.string.invalite_login));
                }
                break;
            case R.id.btn_my_change:
                RedPacketUtil.startChangeActivity(getActivity());
                break;
            case R.id.btn_logout:
                App.saveData2SP(activity, App.SP_PACKAGE_USER, App.SP_KEY_LOGIN_STATUS, "-1");
                //此方法为异步方法
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        App.log(TAG, "-----------环信登出成功--------------");
                    }
                    @Override
                    public void onProgress(int progress, String status) {

                    }
                    @Override
                    public void onError(int code, String message) {
                        App.log(TAG, "-----------环信登出失败--------------");
                    }
                });
                Intent intent = new Intent();
                intent.setClass(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
                break;
        }
    }

    @Override
    public void onPreExecute() {
        //LoadingDialog.showDialog(activity);
    }

    @Override
    public Object doInBackground(Object... params) {
        getRequireType = (Integer)params[0];
        switch (getRequireType){
            case REQUIRE_TYPE_BUY:
                RedPacketUtil.startRedPacketActivityForResult(ThirdFragment.this, EaseConstant.CHATTYPE_SINGLE, "18775769566", REQUEST_CODE_SEND_RED_PACKET);
                break;
            case REQUIRE_TYPE_IN_MONEY:
                String url = params[1]+"";
                String fromUserId = params[2]+"";
                String toUserId = params[3]+"";
                String moneyAmount = params[4]+"";
                String moneyID = params[5]+"";
                String moneyMsgDirect = params[6]+"";
                String takenMoney = params[7]+"";
                orders = App.inMoney(activity, url, fromUserId, toUserId, moneyAmount, moneyID, moneyMsgDirect, takenMoney);
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        //LoadingDialog.dismissDialog();
        switch (getRequireType){
            case REQUIRE_TYPE_BUY:
                break;
            case REQUIRE_TYPE_IN_MONEY:
                if(orders != null){
                    App.log(TAG, "----------orders------->"+orders.getMsg());
                }
                break;
        }
    }

}
