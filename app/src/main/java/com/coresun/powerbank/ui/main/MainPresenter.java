package com.coresun.powerbank.ui.main;

import android.annotation.SuppressLint;

import com.coresun.powerbank.Constants;
import com.coresun.powerbank.base.BasePresenter;
import com.coresun.powerbank.data.DataManager;
import com.coresun.powerbank.data.network.response.AdvResponse;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;
import com.coresun.powerbank.data.socket.SocketRespInterface;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by Android on 2017/8/25.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements MainMVPPresenter<V> {

    @Inject
    public MainPresenter(DataManager DataManager, CompositeDisposable CompositeDisposable) {
        super(DataManager, CompositeDisposable);
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
    }

    @Override
    public void loadData(String num) {
        RequestBody body = null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("number",num);
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        try {
            getCompositeDisposable().add(
                getDataManager().getAdvDataCall(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AdvResponse>() {
                        @Override
                        public void accept(AdvResponse advResponse) throws Exception {
                            getMvpview().showView(advResponse.getResult(),Constants.SHOW_VIEW_TYPE_ADV_DATA);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpview().showError();
                        }
                    })
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(String mac , String ip) {
        RequestBody body = null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("number",mac);
        paramsMap.put("ip",ip);
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        try {
            getCompositeDisposable().add(
                    getDataManager().getInitDataCall(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Response>() {
                                @Override
                                public void accept(Response response) throws Exception {

                                    getMvpview().showView(response,Constants.SHOW_VIEW_TYPE_INIT_DATA);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getMvpview().showError();
                                }
                            })
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkPower(String mac, String electricity) {
        RequestBody body = null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("number",mac);
        paramsMap.put("electricity",electricity);
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        try {
            getCompositeDisposable().add(
                    getDataManager().getCheckPower(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Response>() {
                                @Override
                                public void accept(Response response) throws Exception {
                                    getMvpview().showView(response,Constants.SHOW_VIEW_TYPE_GET_POWER);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getMvpview().showError();
                                }
                            })
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getStartTime(String out_trade_no, String time,int type) {
        RequestBody body = null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("out_trade_no",out_trade_no);
        paramsMap.put("time",time);
        paramsMap.put("type",type+"");
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        try {
            getCompositeDisposable().add(
                    getDataManager().getStartTime(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Response>() {
                                @Override
                                public void accept(Response response) throws Exception {
                                    getMvpview().showView(response,Constants.SHOW_VIEW_TYPE_GET_START_TIME);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getMvpview().showError();
                                }
                            })
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkUpdate(String mac) {
        RequestBody body = null;
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("number",mac);
        String strEntity = gson.toJson(paramsMap);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        try {
            getCompositeDisposable().add(
                    getDataManager().getCheckUpdate(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<UpdateResponse>() {
                                @Override
                                public void accept(UpdateResponse response) throws Exception {
                                    getMvpview().showView(response,Constants.SHOW_VIEW_TYPE_CHECK_UPDATE);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getMvpview().showError();
                                }
                            })
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateData() {
        getDataManager().getUpdateData(Constants.UPDATE_URL, Constants.UPDATE_PORT, new SocketRespInterface() {
            @SuppressLint("CheckResult")
            @Override
            public void onRealResponse(String resp) {
                if (resp!=null){
                    Observable.just(resp)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    getMvpview().updateView(s);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getMvpview().showError();
                                }
                            });
                }
            }

            @Override
            public void onFailResponse(String errorCode) {
                getMvpview().updateView(errorCode);
            }

            @SuppressLint("CheckResult")
            @Override
            public void onConectFail(Throwable throwable) {
                Observable.just(throwable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpview().showError();
                            }
                        });

            }
        });


    }
}
