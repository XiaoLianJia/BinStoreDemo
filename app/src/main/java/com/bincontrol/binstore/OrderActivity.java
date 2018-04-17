package com.bincontrol.binstore;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    // 订单页面参数，仅在H5方式下有效
    private int orderType = 0;

    // 页面打开方式，默认，H5，Native
    private AlibcShowParams alibcShowParams;

    private Map<String, String> exParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
    }


    /**
     * 分域显示我的订单，或者显示我的全部订单
     */
    public void showOrder(View view) {

        Boolean isShowAllOrders = true;
        switch (view.getId()) {
            case R.id.btn_showOrder:
                isShowAllOrders = false;
                break;
            case R.id.btn_showOrderAll:
                isShowAllOrders = true;
                break;
            default:
                break;
        }

        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, isShowAllOrders);
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new TradeCallback());
    }


    /**
     * 显示我的购物车
     */
    public void showCart(View view) {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new TradeCallback());
    }


    // 设置打开方式：默认方式
    public void onCheckOrderOpenModeDefault(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置打开方式：H5方式
    public void onCheckOrderOpenModeH5(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.H5, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置打开方式：taobao方式
    public void onCheckOrderOpenModeTaobao(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("taobao_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置打开方式：tmall方式
    public void onCheckOrderOpenModeTMall(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("tmall_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置订单页面参数：全部
    public void onCheckOrderAll(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 0;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置订单页面参数：待付款
    public void onCheckOrderToPay(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 1;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置订单页面参数：待发货
    public void onCheckOrderToSend(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 2;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置订单页面参数：待收货
    public void onCheckOrderToReceive(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 3;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置订单页面参数：待评价
    public void onCheckOrderToComment(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 4;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

}
