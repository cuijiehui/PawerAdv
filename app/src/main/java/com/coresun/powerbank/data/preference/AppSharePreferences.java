package com.coresun.powerbank.data.preference;

import android.content.Context;
import android.content.SharedPreferences;


import com.coresun.powerbank.di.qualifier.ApplicationContext;
import com.coresun.powerbank.di.qualifier.PreferenceName;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/4/18 0018.
 */


public class AppSharePreferences implements  SharePreferenecesHelper{

    private  String PREF_KEY_ORDER="PREF_KEY_ORDER";
    private  String MODE_NIGHT_OR_DAY="MODE_NIGHT_OR_DAY";

    private final SharedPreferences mPrefs;
    @Inject
    public AppSharePreferences(@ApplicationContext Context context, @PreferenceName String preferencesname) {
        mPrefs = context.getSharedPreferences(preferencesname, Context.MODE_PRIVATE);
    }

    @Override
    public void setExitCount(int count) {
        int c = getEixtCount();
        mPrefs.edit().putInt("exitCount", count + c).commit();
    }

    @Override
    public int getEixtCount() {
        return mPrefs.getInt("exitCount", 0);
    }
}
