package com.easemob.redpacketui.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketsdk.bean.TokenData;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.easemob.redpacketui.RedPacketConstant;
import com.easemob.redpacketui.ui.activity.RPChangeActivity;
import com.easemob.redpacketui.ui.activity.RPRedPacketActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;

/**
 * Created by max on 16/5/24.
 */
public class RedPacketUtil {

    //进入发红包页面
    public static void startRedPacketActivityForResult(Fragment fragment, int chatType, final String toChatUsername, int requestCode) {
        //发送者头像url
        String fromAvatarUrl = "none";
        //发送者昵称 设置了昵称就传昵称 否则传id
        String fromNickname = EMClient.getInstance().getCurrentUser();
        EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
        if (easeUser != null) {
            fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
            fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
        }
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.fromAvatarUrl = fromAvatarUrl;
        redPacketInfo.fromNickName = fromNickname;
        //接收者Id或者接收的群Id
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            redPacketInfo.toUserId = toChatUsername;
            redPacketInfo.chatType = RPConstant.CHATTYPE_SINGLE;
        }
        Intent intent = new Intent(fragment.getContext(), RPRedPacketActivity.class);
        intent.putExtra(RPConstant.EXTRA_RED_PACKET_INFO, redPacketInfo);
        intent.putExtra(RPConstant.EXTRA_TOKEN_DATA, getTokenData());
        fragment.startActivityForResult(intent, requestCode);
    }

    //拆红包的方法
    public static void openRedPacket(final FragmentActivity activity, final int chatType, final EMMessage message) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCanceledOnTouchOutside(false);
        //接收者头像url 默认值为none
        String toAvatarUrl = "none";//测试用图片url:http://i.imgur.com/DvpvklR.png
        //接收者昵称 默认值为当前用户ID
        String toNickname = EMClient.getInstance().getCurrentUser();
        String currentUserId = toNickname;
        String moneyId = message.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_ID, "");
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.moneyID = moneyId;
        redPacketInfo.toAvatarUrl = toAvatarUrl;
        redPacketInfo.toNickName = toNickname;
        redPacketInfo.moneyMsgDirect = RPConstant.MESSAGE_DIRECT_RECEIVE;//方向是接收红包
        redPacketInfo.toUserId = currentUserId;
        redPacketInfo.chatType = chatType;
        RPOpenPacketUtil.getInstance().openRedPacket(redPacketInfo, getTokenData(), activity, new RPOpenPacketUtil.RPOpenPacketCallBack() {
            @Override
            public void onSuccess(String senderId, String senderNickname) {

            }

            @Override
            public void showLoading() {
                progressDialog.show();
            }

            @Override
            public void hideLoading() {
                progressDialog.dismiss();
            }

            @Override
            public void onError(String code, String message) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //进入零钱页面
    public static void startChangeActivity(Context context) {
        Intent intent = new Intent(context, RPChangeActivity.class);
        String fromNickname = EMClient.getInstance().getCurrentUser();
        String fromAvatarUrl = "none";
        EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
        if (easeUser != null) {
            fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
            fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
        }
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.fromNickName = fromNickname;
        redPacketInfo.fromAvatarUrl = fromAvatarUrl;
        intent.putExtra(RPConstant.EXTRA_RED_PACKET_INFO, redPacketInfo);
        intent.putExtra(RPConstant.EXTRA_TOKEN_DATA, getTokenData());
        context.startActivity(intent);
    }

    @NonNull
    private static TokenData getTokenData() {
        TokenData tokenData = new TokenData();
        tokenData.imUserId = EMClient.getInstance().getCurrentUser();
        //此处使用环信id代替了appUserId 开发者可传入App的appUserId
        tokenData.appUserId = EMClient.getInstance().getCurrentUser();
        tokenData.imToken = EMClient.getInstance().getChatConfig().getAccessToken();
        return tokenData;
    }

}
