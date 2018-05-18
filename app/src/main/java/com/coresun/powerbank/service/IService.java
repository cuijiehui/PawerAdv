package com.coresun.powerbank.service;

import com.coresun.powerbank.base.MvpView;
import com.coresun.powerbank.data.network.entity.AdvResult;

/**
 * Created by Android on 2017/9/12.
 */

public interface IService extends MvpView{

    void getNewData(AdvResult advResult);

}
