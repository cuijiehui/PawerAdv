package com.coresun.powerbank.di.component;

import android.app.Application;
import android.content.Context;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.di.module.AppModule;
import com.coresun.powerbank.di.module.NetWorkModule;
import com.coresun.powerbank.di.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Singleton
@Component(modules = {AppModule.class, NetWorkModule.class})
public interface AppComponent {

    void inject(AdvApp advApp);

    Application getApplication();

    @ApplicationContext
    Context context();

    DataManager getDataManager();
}
