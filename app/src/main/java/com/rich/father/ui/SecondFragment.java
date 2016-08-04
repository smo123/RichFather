package com.rich.father.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.utils.RedPacketUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.rich.father.R;
import com.rich.father.app.App;

public class SecondFragment extends EaseChatFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentActivity activity;
    private String mParam1;
    private String mParam2;

    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        String orderId = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_ORDER_ID);
        String receiveId = App.getData4SP(activity, App.SP_PACKAGE_REDPACKET, App.SP_KEY_RECEIVED_ID);
        EMMessage message = EMMessage.createTxtSendMessage("领取红包", receiveId);
        message.setAttribute(RedPacketConstant.EXTRA_RED_PACKET_ID, orderId);//订单号
        RedPacketUtil.openRedPacket(activity, EaseConstant.CHATTYPE_SINGLE, message);

        return v;
    }

}
