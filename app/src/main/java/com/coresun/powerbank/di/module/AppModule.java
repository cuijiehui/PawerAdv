package com.coresun.powerbank.di.module;

import android.app.Application;
import android.content.Context;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.Constants;
import com.coresun.powerbank.data.AppDataManager;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.data.network.ApiHelper;
import com.coresun.powerbank.data.network.AppApiHelper;
import com.coresun.powerbank.data.preference.AppSharePreferences;
import com.coresun.powerbank.data.preference.SharePreferenecesHelper;
import com.coresun.powerbank.data.socket.AppSocketManager;
import com.coresun.powerbank.data.socket.SocketHelper;
import com.coresun.powerbank.di.qualifier.ApplicationContext;
import com.coresun.powerbank.di.qualifier.DbName;
import com.coresun.powerbank.di.qualifier.PreferenceName;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Module
public class AppModule {
    private final AdvApp mApplication;
    public AppModule(AdvApp application) {
        this.mApplication=application;
    }

    @ApplicationContext
    @Provides
    Context provideContext(){
        return mApplication;
    }


    @Singleton
    @Provides
    Application provideApplication(){return mApplication;}

    @Singleton
    @Provides
    DataManager provideDataManger(AppDataManager appDataManager){return appDataManager;}

    @Singleton
    @Provides
    ApiHelper privideAqiHelper(AppApiHelper appApiHelper){
        return appApiHelper;
    }



    @Singleton
    @Provides
    SharePreferenecesHelper prividePreferenecesHelper(AppSharePreferences appSharePreferences){
        return appSharePreferences;
    }

    @Singleton
    @Provides
    SocketHelper provideSocketHelper(AppSocketManager appSocketManager){
        return appSocketManager;
    }

    @DbName
    @Provides
    String privadeDbName(){
        return Constants.DB_NAME;
    }


    @PreferenceName
    @Provides
    String privadePreferenceName(){
        return Constants.PREFERENCE_NAME;
    }


}
