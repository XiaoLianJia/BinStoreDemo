package com.bincontrol.binstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;

import java.util.HashMap;
import java.util.Map;

import static com.bincontrol.binstore.BinStoreApplication.alibcTaokeParams;

public class WebViewProxyActivity extends AppCompatActivity {

    private WebView webView;
    private Map<String, String> exParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        AlibcBasePage alibcBasePage = new AlibcDetailPage("532128520567");
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTrade.show(WebViewProxyActivity.this, webView, null, null, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new TradeCallback());
    }

    private void initView() {

        LinearLayout linearLayout = new LinearLayout(this, null);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.addView(webView, params);
        setContentView(linearLayout);
    }

    // 登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    // 调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
