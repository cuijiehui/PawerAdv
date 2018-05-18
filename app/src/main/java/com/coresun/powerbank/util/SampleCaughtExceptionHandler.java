package com.coresun.powerbank.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.ui.main.MainActivity;

import javax.inject.Inject;

/**
 * 当程序抛异常后自动重启应用程序
 * author by Mqz
 * Created by Android on 2017/9/22.
 */

public class SampleCaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

    private Context mContext;

    @Inject
    DataManager dataManager;

    private static SampleCaughtExceptionHandler instance = new SampleCaughtExceptionHandler();

    public static SampleCaughtExceptionHandler getInstance(){
        return instance;
    }

    public void init(Context context){
        this.mContext = context;

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("111");
                int count = AdvApp.getAppComponent().getDataManager().getEixtCount();
                if (count == 1) return;
                Intent i1 = new Intent(mContext, MainActivity.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, i1, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, restartIntent);
                Process.killProcess(Process.myPid());
            }
        }).start();
    }
}
