package com.rich.father.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rich.father.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText tvUserName, editPassword, editConfirmPassword, editPhoneNumber, editWeChat, editQQ;
    private ImageButton btnBack;
    private Button btnRegister;

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
        btnBack = (ImageButton) findViewById(R.id.btn_back);
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
                break;
        }
    }
}

