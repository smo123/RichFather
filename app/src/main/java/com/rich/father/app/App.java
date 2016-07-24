package com.rich.father.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.easemob.redpacketsdk.RedPacket;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.rich.father.utils.HttpTool;

/**
 * Created by jinba on 2016/7/14.
 */
public class App extends Application{

    public static final String TAG = App.class.getName();
    public static final boolean DEBUG = true;//是否允许debug输出

    public static final String LOGIN = "http://feigou.ecs31.tomcats.pw/RichDad/user/login.do";

    //本方法保证在5.0以下的机器也能够运行，不要移除
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //环信相关
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);// 默认添加好友时，是不需要验证的，改成需要验证
        EMClient.getInstance().init(this, options);//初始化
        EMClient.getInstance().setDebugMode(true);//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        RedPacket.getInstance().initContext(this);//初始化红包
        RedPacket.getInstance().setDebugMode(true);//打开Log开关 正式发布时请关闭
    }

    //登录
    public static String login(Context context, String url, String name, String password){
        String result = null;
        result = HttpTool.httpPostLogin(context, url, name, password);
        return result;
    }

    //把数据保存到SharedPreferences之中
    public static void saveData2SP(Context context, String pk, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(pk, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //把数据从SharedPreferences读取出来
    public static String getData4SP(Context context, String pk, String key) {
        String result = null;
        SharedPreferences sp = context.getSharedPreferences(pk, context.MODE_PRIVATE);
        result = sp.getString(key, "");
        return result;
    }

    //debug输出
    public static void log(String tag, String info){
        if(DEBUG&&tag != null&&info != null){
            Log.d(tag, info);
        }
    }

}
