package com.rich.father.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.rich.father.R;
import com.rich.father.app.App;

public class ThirdFragment extends EaseChatFragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentActivity activity;
    private Button btnGetRP, btnChange, btnLogout;

    private String mParam1;
    private String mParam2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        btnGetRP = (Button)view.findViewById(R.id.btn_get_red_package);
        btnChange = (Button)view.findViewById(R.id.btn_my_change);
        btnLogout = (Button)view.findViewById(R.id.btn_logout);
        btnGetRP.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_red_package:
                String red_packet_id = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_ID);
                String red_packet_receiver_id = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_RECEIVER_ID);
                String red_packet_greeting = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RED_PACKET_GREETING);
                EMMessage message = EMMessage.createTxtSendMessage(red_packet_greeting, red_packet_receiver_id);
                message.setAttribute(RedPacketConstant.EXTRA_RED_PACKET_ID, red_packet_id);//订单号
                RedPacketUtil.openRedPacket(activity, EaseConstant.CHATTYPE_SINGLE, message);
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
                break;
        }
    }

}
