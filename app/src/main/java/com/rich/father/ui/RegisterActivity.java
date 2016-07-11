package com.rich.father.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rich.father.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText tvUserName, editPassword, editConfirmPassword, editPhoneNumber, editWeChat, editQQ;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.password);
        editConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        editPhoneNumber = (EditText) findViewById(R.id.tv_phone_number);
        editWeChat = (EditText) findViewById(R.id.edit_wechat);
        editQQ = (EditText) findViewById(R.id.edit_qq);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                break;
        }
    }
}

