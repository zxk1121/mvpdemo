package com.app.demo.application;

import android.app.Application;
import android.content.Context;

import com.app.demo.api.RetrofitService;

/**
 * Created by zxk on 17-3-9.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initConfig();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        RetrofitService.init();  //初始化网络请求
    }

    public static Context getContext() {
        return mContext;
    }
}
