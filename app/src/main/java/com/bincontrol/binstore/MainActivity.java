package com.bincontrol.binstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 登录
     */
    public void login(View view) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(MainActivity.this, "登录成功 ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(MainActivity.this, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 退出登录
     */
    public void logout(View view) {

        AlibcLogin alibcLogin = AlibcLogin.getInstance();

        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(MainActivity.this, "登出成功 ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "登录失败 ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 电商交易
     */
    public void trade(View view) {
        Intent transactionIntent = new Intent(MainActivity.this, TradeActivity.class);
        startActivity(transactionIntent);
    }


    /**
     * 订单和购物车
     */
    public void orderAndCart(View view) {
        Intent mineIntent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(mineIntent);
    }


    /**
     * webview代理
     */
    public void webview(View view) {
        Intent webviewIntent = new Intent(MainActivity.this, WebViewProxyActivity.class);
        startActivity(webviewIntent);
    }
    

    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

}
