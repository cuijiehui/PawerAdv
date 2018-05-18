package com.coresun.powerbank.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.Constants;
import com.coresun.powerbank.base.RxBus;
import com.coresun.powerbank.data.network.entity.AdvResult;
import com.coresun.powerbank.di.component.DaggerServiceComponent;
import com.coresun.powerbank.di.component.ServiceComponent;
import com.coresun.powerbank.di.module.ServiceModule;
import com.coresun.powerbank.util.LogUtils;
import com.coresun.powerbank.util.ToastUtils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * 心跳服务，每5分钟访问一次后台是否有广告更新。
 * Created by Android on 2017/9/12.
 */

public class HeartBeatService extends Service implements IService {

    private static final int cycleTime = 1000 * 60 * 10; //10分钟更新一次广告
    boolean is9Update = false, is15Update = false, is20Update = false, is0Update = false;
    @Inject
    ServiceMvpPresenter<IService> presenter;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public void onCreate() {
        inject();
        presenter.attachView(this);
    }

    /**
     * 注解Dagger2框架
     */
    private void inject() {
        ServiceComponent serviceComponent = DaggerServiceComponent.builder()
                .appComponent(AdvApp.getAppComponent())
                .serviceModule(new ServiceModule())
                .build();
        serviceComponent.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("定时测试：启动定时服务：");
        sendHeartBeat();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendHeartBeat() {

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                LogUtils.i("定时测试：系统当前的时间：" + hour + ":" + minute);
                switch (hour) {
                    case 9:
                        if (!is9Update) {
                            getAdvData();
                            is9Update = true;
                            is15Update = false;
                            is20Update = false;
                            is0Update = false;
                        }
                        break;
                    case 15:
                        if (!is15Update) {
                            getAdvData();
                            is9Update = false;
                            is15Update = true;
                            is20Update = false;
                            is0Update = false;
                        }
                        break;
                    case 20:
                        if (!is20Update) {
                            getAdvData();
                            is9Update = false;
                            is15Update = false;
                            is20Update = true;
                            is0Update = false;
                        }
                        break;
                    case 0:
                        if (!is0Update) {
                            getAdvData();
                            is9Update = false;
                            is15Update = false;
                            is20Update = false;
                            is0Update = true;
                        }
                        break;
                }
//                getAdvData();
            }
        };

        timer.schedule(timerTask, cycleTime, cycleTime);

    }

    private void getAdvData() {
        presenter.updateAdvData(Constants.DEVICE_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerTask != null && timer != null) {
            timerTask.cancel();
            timerTask = null;
            timer.cancel();
            timer = null;
        }
        presenter.onDetach();
    }

    @Override
    public void showError() {
        ToastUtils.showToast("获取数据失败");
    }

    @Override
    public void getNewData(AdvResult advResult) {
        LogUtils.e("心跳服务 --->   获取数据成功");
        //strs=http://power.szshide.cn/zip/20180409/chdzip2018040962288.zip
        if (judgeData(advResult.getPics())) {
            RxBus.getInstance().post(advResult);
        }
    }

    public boolean judgeData(String data) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        String sMonth = String.valueOf(month);
        String sDay = String.valueOf(day);
        byte[] cache = sMonth.getBytes();
        if (cache.length == 1) {
            sMonth = "0" + sMonth;
        }
        cache = sDay.getBytes();
        if (cache.length == 1) {
            sDay = "0" + sDay;
        }
        if (data.contains(year + "" + sMonth + sDay)) {
            return true;
        } else {
            return false;
        }
    }
}
