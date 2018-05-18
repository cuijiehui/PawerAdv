package com.coresun.powerbank.ui.main;

import com.coresun.powerbank.base.MvpPresenter;

/**
 * Created by Android on 2017/8/25.
 */

public interface MainMVPPresenter<V extends MainView> extends MvpPresenter<V> {
    /**
     * 获取广告信息
     *
     * @param mac mac地址
     */
    void loadData(String mac);

    /**
     * 将mac地址和socktip发给服务器
     *
     * @param mac mac地址
     * @param ip  socktip
     */
    void initData(String mac, String ip);

    /**
     * 给服务器发剩余电量
     *
     * @param mac         mac地址
     * @param electricity 电池电量
     */
    void checkPower(String mac, String electricity);

    /**
     * 给服务器发送充电开始时间
     *
     * @param out_trade_no 订单号
     * @param time         时间戳 如： 1523446369
     * @param type         开始充电是否成功 如： 1为成功 2为失败
     */
    void getStartTime(String out_trade_no, String time, int type);

    /**
     * 获取软件更新
     *
     * @param mac mac地址
     */
    void checkUpdate(String mac);

    void updateData();

}
