package com.rich.father.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.rich.father.R;
import com.rich.father.app.App;
import com.rich.father.utils.HttpAsyncTask;

public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpAsyncTask.IHttpAsyncTask {

    private static final String TAG = LoginActivity.class.getName();

    private AutoCompleteTextView tvUserName;
    private EditText editPassword;
    private Button btnLogin, btnRegister;

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
                HttpAsyncTask.getInstance(this);//登陆
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
        String uerName = tvUserName.getText()+"";
        String password = editPassword.getText()+"";
        String str = App.login(this, App.LOGIN,  uerName, password);
        return str;
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public void onPostExecute(Object result) {
        App.log(TAG, "-----------str------------->"+result);
    }
}

