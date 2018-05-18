package com.coresun.powerbank.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.di.component.ActivityComponent;
import com.coresun.powerbank.di.component.DaggerActivityComponent;
import com.coresun.powerbank.di.module.ActivityModule;
import com.coresun.powerbank.util.ToastUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2017/3/27 0027.
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Inject
    DataManager dataManager;
    ActivityComponent mActivityComponent;
    Observable<Boolean> observable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SampleCaughtExceptionHandler.getInstance().init(this);
        inject();
        mActivityComponent.inject(this);
        setContentView(getLayoutId());
        ToastUtils.init(this);
        ButterKnife.bind(this);

        observable=RxBus.getInstance().register(Boolean.class);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                refreshUI();
            }
        });

    }


    protected abstract void refreshUI();


    private void inject() {
        mActivityComponent= DaggerActivityComponent.builder().
                appComponent(AdvApp.getAppComponent()).
                activityModule(new ActivityModule()).build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    protected abstract int  getLayoutId();

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        RxBus.getInstance().unregister(Boolean.class,observable);
        super.onDestroy();
    }

}
