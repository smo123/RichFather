package com.rich.father.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.easemob.redpacketsdk.RedPacket;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.rich.father.models.RequireResult;
import com.rich.father.utils.HttpTool;
import com.rich.father.utils.JsonParser;

/**
 * Created by jinba on 2016/7/14.
 */
public class App extends Application{

    public static final String TAG = App.class.getName();
    public static final boolean DEBUG = true;//是否允许debug输出

    private static final String BASE_URL = "http://feigou.ecs31.tomcats.pw/";//正式环境
    //private static final String BASE_URL = "http://192.168.1.102:8080/";//测试环境

    public static final String REGISTER = BASE_URL+"RichDad/user/regist.do";
    public static final String LOGIN = BASE_URL+"RichDad/user/login.do";

    public static final String SP_PACKAGE_USER = "user";
    public static final String SP_KEY_LOGIN_STATUS = "login_status";
    public static final String SP_KEY_USER_NAME = "user_name";

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
        EMClient.getInstance().setDebugMode(false);//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        RedPacket.getInstance().initContext(this);//初始化红包
        RedPacket.getInstance().setDebugMode(false);//打开Log开关 正式发布时请关闭
    }

    //注册
    public static RequireResult register(Context context, String url, String name, String password, String phone, String wechat, String qq, String inviteCode){
        RequireResult requireResult = null;
        String result = HttpTool.httpPostRegister(context, url, name, password, phone, wechat, qq, inviteCode);
        if(result != null){
            requireResult = JsonParser.parserRequireResult(result);
        }
        return requireResult;
    }

    //登录
    public static RequireResult login(Context context, String url, String name, String password){
        RequireResult requireResult = null;
        String result = HttpTool.httpPostLogin(context, url, name, password);
        if(result != null){
            requireResult = JsonParser.parserRequireResult(result);
        }
        return requireResult;
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
        result = sp.getString(key, "-1");
        return result;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    //debug输出
    public static void log(String tag, String info){
        if(DEBUG&&tag != null&&info != null){
            Log.d(tag, info);
        }
    }

    /**显示toast提示*/
    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
