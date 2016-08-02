package com.rich.father.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.rich.father.R;
import com.rich.father.app.App;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    private TextView tvMenuHome, tvMenuFriend, tvMenuMe;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMenuHome = (TextView)findViewById(R.id.tv_menu_home);
        tvMenuFriend = (TextView)findViewById(R.id.tv_menu_friend);
        tvMenuMe = (TextView)findViewById(R.id.tv_menu_me);
        tvMenuHome.setOnClickListener(this);
        tvMenuFriend.setOnClickListener(this);
        tvMenuMe.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();//Fragment管理器

        autoLogin();//自动登录环信

        //默认启动
        String loginStatus = App.getData4SP(this, App.SP_PACKAGE_USER, App.SP_KEY_LOGIN_STATUS);
        if(loginStatus.equalsIgnoreCase("0")){
            tvMenuHome.setSelected(true);
            FirstFragment firstFragment = FirstFragment.newInstance("", "");
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.layout_main_content, firstFragment);
            transaction.commit();
        }else {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        reSetSelected();
        clearBackStack();
        switch (v.getId()){
            case R.id.tv_menu_home:
                tvMenuHome.setSelected(true);
                FirstFragment firstFragment = FirstFragment.newInstance("", "");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_main_content, firstFragment);
                transaction.commit();
                break;
            case R.id.tv_menu_friend:
                tvMenuFriend.setSelected(true);
                SecondFragment secondFragment = SecondFragment.newInstance("", "");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_main_content, secondFragment);
                transaction.commit();
                break;
            case R.id.tv_menu_me:
                tvMenuMe.setSelected(true);
                ThirdFragment thirdFragment = ThirdFragment.newInstance("", "");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_main_content, thirdFragment);
                transaction.commit();
                break;
        }
    }

    //自动登录环信
    private void autoLogin(){
        String loginStatus = App.getData4SP(this, App.SP_PACKAGE_USER, App.SP_KEY_LOGIN_STATUS);
        String phone = App.getData4SP(this, App.SP_PACKAGE_USER, App.SP_KEY_PHONE);
        String password = App.getData4SP(this, App.SP_PACKAGE_USER, App.SP_KEY_PASSWORD);
        if(loginStatus.equalsIgnoreCase("0")&&!phone.equalsIgnoreCase("-1")&&!password.equalsIgnoreCase("-1")){
            EMClient.getInstance().login(phone, password,new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    App.log(TAG, "-------------环信登录成功----------");
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    App.log(TAG, "-------------登录聊天服务器失败----------");
                }
            });
        }
    }

    //清空BackStack
    private void clearBackStack(){
        int len = fragmentManager.getBackStackEntryCount();
        for(int i=0; i<len; i++){
            fragmentManager.popBackStack();
        }
    }

    /*重置菜单状态*/
    private void reSetSelected(){
        tvMenuHome.setSelected(false);
        tvMenuFriend.setSelected(false);
        tvMenuMe.setSelected(false);
    }

}
