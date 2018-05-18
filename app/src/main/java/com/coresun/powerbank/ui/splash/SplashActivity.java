package com.coresun.powerbank.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.coresun.powerbank.R;
import com.coresun.powerbank.base.BaseActivity;
import com.coresun.powerbank.ui.main.MainActivity;

/**
 * Created by Android on 2017/10/12.
 */

public class SplashActivity extends BaseActivity{

    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();

    }

    @Override
    protected void refreshUI() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null) timer.cancel();
    }


}
