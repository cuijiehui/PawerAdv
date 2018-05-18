package com.coresun.powerbank.di.component;


import com.coresun.powerbank.base.BaseActivity;
import com.coresun.powerbank.di.module.ActivityModule;
import com.coresun.powerbank.di.scope.PerActivity;
import com.coresun.powerbank.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@PerActivity
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);

}
