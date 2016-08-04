package com.rich.father.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.models.RequireResult;
import com.rich.father.utils.HttpAsyncTask;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, HttpAsyncTask.IHttpAsyncTask {

    private static final String TAG = RegisterActivity.class.getName();
    private static final int REQUIRE_TYPE_REGISTER = 0;//登录环信
    private int requireType;

    private EditText tvUserName, editPassword, editConfirmPassword, editPhoneNumber, editWeChat, editQQ, editInviteCode;
    private Button btnBack;
    private Button btnRegister;

    private String uerName;
    private String password, confirmPassword;
    private String phone;
    private String wechat;
    private String qq;
    private String inviteCode;

    private RequireResult requireResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(R.layout.activity_register);
        tvUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
        editPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        editWeChat = (EditText) findViewById(R.id.edit_wechat);
        editQQ = (EditText) findViewById(R.id.edit_qq);
        editInviteCode = (EditText) findViewById(R.id.edit_invite_code);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_register:
                if(checkParams()){
                    HttpAsyncTask.getInstance(this, REQUIRE_TYPE_REGISTER);
                }
                break;
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object... params) {
        requireType = (Integer)params[0];
        switch (requireType) {
            case REQUIRE_TYPE_REGISTER:
                try {
                    //注册失败会抛出HyphenateException
                    EMClient.getInstance().createAccount(phone, password);//同步方法
                    requireResult = App.register(RegisterActivity.this, App.REGISTER, uerName, password, phone, wechat, qq, inviteCode);
                }catch (Exception e){
                    e.printStackTrace();
                    requireResult = null;
                }
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        switch (requireType) {
            case REQUIRE_TYPE_REGISTER:
                if(requireResult != null){
                    String status = requireResult.getStatus();
                    if(status.equalsIgnoreCase("0")){
                        App.toast(this, requireResult.getMsg());
                        finish();
                    }else {
                        App.toast(this, requireResult.getMsg());
                    }
                }else {
                    App.toast(this, getResources().getString(R.string.register_fail));
                }
                break;
        }
    }

    //验证参数合法性
    private boolean checkParams(){
        uerName = tvUserName.getText()+"";
        password = editPassword.getText()+"";
        confirmPassword = editConfirmPassword.getText()+"";
        phone = editPhoneNumber.getText()+"";
        wechat = editWeChat.getText()+"";
        qq = editQQ.getText()+"";
        inviteCode = editInviteCode.getText()+"";
        if(uerName.length()<2){
            App.toast(this, getResources().getString(R.string.password_username_err));
            return false;
        }
        if(password.length()<6){
            App.toast(this, getResources().getString(R.string.password_length_err));
            return false;
        }
        if(!password.equalsIgnoreCase(confirmPassword)){
            App.toast(this, getResources().getString(R.string.password_match_err));
            return false;
        }
        if (!App.isMobileNO(phone)){
            App.toast(this, getResources().getString(R.string.password_phone_err));
            return false;
        }
        if(wechat.length()<2){
            App.toast(this, getResources().getString(R.string.password_wechat_err));
            return false;
        }
        if(qq.length()<2){
            App.toast(this, getResources().getString(R.string.password_qq_err));
            return false;
        }
        if(inviteCode.length()<2||TextUtils.isEmpty(inviteCode)){
            App.toast(this, getResources().getString(R.string.password_invitecode_err));
            return false;
        }
        return true;
    }

}

