package com.coresun.powerbank.di.component;

import com.coresun.powerbank.di.module.ServiceModule;
import com.coresun.powerbank.di.scope.PerService;
import com.coresun.powerbank.service.HeartBeatService;

import dagger.Component;

/**
 * Created by Android on 2017/9/12.
 */

@PerService
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(HeartBeatService heartBeatService);

}
