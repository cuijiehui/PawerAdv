package com.coresun.powerbank;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.coresun.powerbank.di.component.AppComponent;
import com.coresun.powerbank.di.component.DaggerAppComponent;
import com.coresun.powerbank.di.module.AppModule;
import com.coresun.powerbank.di.module.NetWorkModule;
import com.coresun.powerbank.util.LogUtils;
import com.coresun.powerbank.util.NetWorkStateUtils;

/**
 * 应用入口，全局变量。
 * Created by Android on 2017/8/25.
 */

public class AdvApp extends Application {

    private static Context context;
    private static AppComponent appComponent;
    private static boolean networkStates;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netWorkModule(new NetWorkModule(this))
                .build();
        appComponent.inject(this);


        new LogUtils.Builder()
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("SZCM")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose


        networkStates = NetWorkStateUtils.isNetworkConnected(this);
        appComponent.getDataManager().setExitCount(1);


        //自动更新apk包
        Intent apk_install = new Intent("com.hilan.updater");
        // apk 包名称
        apk_install.putExtra("from", "com.coresun.powerbank");
        // apk 所在位置
        apk_install.putExtra("path", "/sdcard/powerbank.apk");
        // APK 启动 activity
        apk_install.putExtra("class_from", "com.coresun.powerbank.ui.splash.SplashActivity");
        // 执行消息
        sendBroadcast(apk_install);

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getAppContext() {
        return context;
    }

    public static Boolean netWorkIsAvailable() {
        return networkStates;
    }

}
