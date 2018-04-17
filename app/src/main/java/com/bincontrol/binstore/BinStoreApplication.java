package com.bincontrol.binstore;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.trade.biz.AlibcTradeBiz;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.common.AlibcTradeCommon;

import java.util.HashMap;

public class BinStoreApplication extends Application {

    public static BinStoreApplication application = null;
    public static AlibcTaokeParams alibcTaokeParams = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        AlibcTradeCommon.turnOnDebug();
        AlibcTradeBiz.turnOnDebug();

        // 初始化淘客参数
        initTaokeParams();

        // 电商SDK初始化
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(BinStoreApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
                AlibcTradeSDK.setTaokeParams(alibcTaokeParams);
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(BinStoreApplication.this, "初始化失败，错误码=" + code + " / 错误消息=" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTaokeParams() {
        alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.adzoneid = "428110369";
        alibcTaokeParams.pid = "mm_128206999_44178876_428110369";
        alibcTaokeParams.subPid = "mm_128206999_44178876_428110369";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", "24847671");
    }

}
