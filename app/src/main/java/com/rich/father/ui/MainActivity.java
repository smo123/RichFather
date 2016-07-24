package com.rich.father.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.rich.father.R;

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

        //默认启动
        tvMenuHome.setSelected(true);
        FirstFragment firstFragment = FirstFragment.newInstance("", "");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout_main_content, firstFragment);
        transaction.commit();

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
