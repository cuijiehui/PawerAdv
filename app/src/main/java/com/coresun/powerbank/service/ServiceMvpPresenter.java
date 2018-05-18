package com.coresun.powerbank.service;

import com.coresun.powerbank.base.MvpPresenter;

/**
 * Created by Android on 2017/9/12.
 */

public interface ServiceMvpPresenter<V extends IService> extends MvpPresenter<V>{

    void updateAdvData(String param);

}
