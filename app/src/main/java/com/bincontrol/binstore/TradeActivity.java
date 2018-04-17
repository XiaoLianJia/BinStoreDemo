package com.bincontrol.binstore;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

import static com.bincontrol.binstore.BinStoreApplication.alibcTaokeParams;

public class TradeActivity extends AppCompatActivity {

    private EditText etUrl;
    private EditText etCommodityId;
    private EditText etShopId;

    // 是否是淘客商品类型
    private Boolean isTaokeCommodity = false;

    // 页面打开方式，默认，H5，Native
    private AlibcShowParams alibcShowParams = null;

    // 淘客参数，包括pid，unionid，subPid
    private AlibcTaokeParams taokeParams = null;

    private Map<String, String> exParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        setTitle(getResources().getString(R.string.trade));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        initView();
    }

    private void initView() {
        etUrl = (EditText) findViewById(R.id.et_url);
        etCommodityId = (EditText) findViewById(R.id.et_commodityId);
        etShopId = (EditText) findViewById(R.id.et_shopId);
    }


    /**
     * 打开指定链接
     */
    public void showUrl(View view) {

        String text = etUrl.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(TradeActivity.this, "URL为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isTaokeCommodity) {
            taokeParams = alibcTaokeParams;
        } else {
            // 若非淘客商品，taokeParams设置为null即可
            taokeParams = null;
        }

        AlibcTrade.show(this, new AlibcPage(text), alibcShowParams, taokeParams, exParams, new TradeCallback());
    }


    /**
     * 显示商品详情页
     */
    public void showCommodityDetail(View view) {

        String text = etCommodityId.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(TradeActivity.this, "商品id为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcDetailPage(etCommodityId.getText().toString());

        if (isTaokeCommodity) {
            taokeParams = alibcTaokeParams;
        } else {
            // 若非淘客商品，taokeParams设置为null即可
            taokeParams = null;
        }

        AlibcTrade.show(this, alibcBasePage, alibcShowParams, taokeParams, exParams , new TradeCallback());
    }


    /**
     * 添加到购物车
     */
    public void addCommodityToCart(View view) {

        String text = etCommodityId.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(TradeActivity.this, "商品id为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcAddCartPage(etCommodityId.getText().toString());

        if (isTaokeCommodity) {
            taokeParams = alibcTaokeParams;
        } else {
            // 若非淘客商品，taokeParams设置为null即可
            taokeParams = null;
        }

        AlibcTrade.show(this, alibcBasePage, alibcShowParams, taokeParams, exParams , new TradeCallback());
    }


    /**
     * 显示店铺
     */
    public void showShop(View view) {

        String text = etShopId.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(TradeActivity.this, "店铺id为空", Toast.LENGTH_SHORT).show();
            return;
        }

        AlibcBasePage alibcBasePage;
        alibcBasePage = new AlibcShopPage(etShopId.getText().toString());
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams , new TradeCallback());
    }


    // 设置打开方式：默认方式
    public void onCheckTradeOpenModeDefault(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置打开方式：H5方式
    public void onCheckTradeOpenModeH5(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.H5, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置打开方式：taobao方式
    public void onCheckTradeOpenModeTaobao(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("taobao_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置打开方式：tmall方式
    public void onCheckTradeOpenModeTMall(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("tmall_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置商品类型：普通商品
    public void onCheckCommonCommodity(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            isTaokeCommodity = false;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置商品类型：淘客商品
    public void onCheckTaokeCommodity(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            isTaokeCommodity = true;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置商品id是否混淆：不混淆
    public void onCheckNotConfuse(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            String commodityId = "522166121586";
            if (etCommodityId.getText().toString().equals("AAHPt-dcABxGVVui-VRMv5Gm")) {
                etCommodityId.setText(commodityId);
            }
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // 设置商品id是否混淆：混淆
    public void onCheckConfuse(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            String commodityId = "AAHPt-dcABxGVVui-VRMv5Gm";
            if (etCommodityId.getText().toString().equals("522166121586")) {
                etCommodityId.setText(commodityId);
            }
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
