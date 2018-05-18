package com.coresun.powerbank.ui.main;

import com.coresun.powerbank.base.MvpView;
import com.coresun.powerbank.data.network.entity.AdvResult;

/**
 * Created by Android on 2017/8/25.
 */

public interface MainView extends MvpView{

    void showView(Object result,int type);

    void updateView(String result);

}
