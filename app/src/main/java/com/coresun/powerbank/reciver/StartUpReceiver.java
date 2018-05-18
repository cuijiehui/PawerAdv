package com.coresun.powerbank.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.coresun.powerbank.ui.splash.SplashActivity;

/**
 * Created by Android on 2018/1/17.
 */

public class StartUpReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, SplashActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

}
