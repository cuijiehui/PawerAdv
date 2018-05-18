package com.coresun.powerbank.di.module;

import com.coresun.powerbank.di.scope.PerActivity;
import com.coresun.powerbank.ui.main.MainMVPPresenter;
import com.coresun.powerbank.ui.main.MainPresenter;
import com.coresun.powerbank.ui.main.MainView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Module
public class ActivityModule  {

    @Provides
    @PerActivity
    MainMVPPresenter<MainView> provideMainPresenter(MainPresenter<MainView> presenter) {
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


}
