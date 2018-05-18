package com.coresun.powerbank.di.module;

import com.coresun.powerbank.service.IService;
import com.coresun.powerbank.service.ServiceMvpPresenter;
import com.coresun.powerbank.service.ServicePresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Android on 2017/9/12.
 */

@Module
public class ServiceModule {

    @Provides
    ServiceMvpPresenter<IService> provideServicePresenter(ServicePresenter<IService> presenter){
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
