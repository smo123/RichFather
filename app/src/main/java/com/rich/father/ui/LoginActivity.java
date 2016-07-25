package com.rich.father.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.utils.HttpAsyncTask;

public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpAsyncTask.IHttpAsyncTask {

    private static final String TAG = LoginActivity.class.getName();
    private static final int REQUIRE_TYPE_LOGIN_HX = 0;//登录环信
    private static final int REQUIRE_TYPE_LOGIN_RF = 1;//登录富爸爸
    private int requireType;

    private AutoCompleteTextView tvUserName;
    private EditText editPassword;
    private Button btnLogin, btnRegister;

    private String loginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(R.layout.activity_login);
        tvUserName = (AutoCompleteTextView) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                HttpAsyncTask.getInstance(this, REQUIRE_TYPE_LOGIN_HX);//登陆
                break;
            case R.id.btn_register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public Object doInBackground(Object... params) {
        //String uerName = tvUserName.getText()+"";
        //String password = editPassword.getText()+"";
        String uerName = "smo2";
        String password = "868688";
        requireType = (Integer)params[0];
        switch (requireType){
            case REQUIRE_TYPE_LOGIN_HX:
                EMClient.getInstance().login(uerName, password,new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        App.log(TAG, "-------------登录聊天服务器成功---------->");
                        HttpAsyncTask.getInstance(LoginActivity.this, REQUIRE_TYPE_LOGIN_RF);//登陆富爸爸
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        App.log(TAG, "-------------登录聊天服务器失败---------->");
                    }
                });
                break;
            case REQUIRE_TYPE_LOGIN_RF:
                loginResult = App.login(this, App.LOGIN,  uerName, password);
                break;
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        switch (requireType){
            case REQUIRE_TYPE_LOGIN_HX:
                break;
            case REQUIRE_TYPE_LOGIN_RF:
                App.log(TAG, "-------------loginResult---------->"+loginResult);
                break;
        }
    }
}

