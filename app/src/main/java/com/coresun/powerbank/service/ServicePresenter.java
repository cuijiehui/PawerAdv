package com.coresun.powerbank.service;

import android.annotation.SuppressLint;

import com.coresun.powerbank.base.BasePresenter;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.data.network.response.AdvResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by Android on 2017/9/12.
 */

public class ServicePresenter<V extends IService> extends BasePresenter<V> implements ServiceMvpPresenter<V>{

    @Inject
    public ServicePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateAdvData(String param) {
        RequestBody body=null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("number",param);
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        getDataManager().getAdvDataCall(body)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<AdvResponse>() {
            @Override
            public void accept(AdvResponse advResponse) throws Exception {
                getMvpview().getNewData(advResponse.getResult());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getMvpview().showError();
            }
        });


    }


}
