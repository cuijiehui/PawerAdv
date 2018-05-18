package com.coresun.powerbank;

/**
 * 常量
 * Created by Android on 2017/8/25.
 */

public class Constants {

    public static final String DB_NAME = "adv.db";

    //    public static final String baseUrl = "http://gank.io/api/";
//    public static final String baseUrl = "http://community.coresun.net/index.php/";
    public static final String baseUrl = "http://power.szshide.cn/";

    public static final String ZMT_BASE_URL = "http://ssp.zmeng123.com/";

    public static final String PREFERENCE_NAME = "adv";

    public static final String SN_CODE = "10000547";

    public static final int PAGE_COUNT = 100;

    public static final String UPDATE_URL = "http://community.coresun.net/index.php/";

    public static final int UPDATE_PORT = 8080;

    /**
     * sharedpreferences key值
     */
    public static final String SHARE_KEY_ADV_CATALOG_NAME = "adv_catalog_name";
    public static final String SHARE_KEY_ADV_CACHE_PATH = "adv_cache_path";
    /**
     * 唯一编码
     */
    public static String DEVICE_ID = "";

    /**
     * showView的类型(type)
     */
    public static final int SHOW_VIEW_TYPE_INIT_DATA = 1; //设置mac地址和ip地址
    public static final int SHOW_VIEW_TYPE_ADV_DATA = 2;  //获取广告
    public static final int SHOW_VIEW_TYPE_GET_POWER = 3;  //给服务器电量
    public static final int SHOW_VIEW_TYPE_GET_START_TIME = 4;  //给服务器充电开始时间
    public static final int SHOW_VIEW_TYPE_CHECK_UPDATE = 5;  //获取版本升级

    public static class Config {
        public static final int HTTP_CACHE_SIZE = 20 * 1024 * 1024;
        public static final int HTTP_CONNECT_TIMEOUT = 15 * 1000;
        public static final int HTTP_READ_TIMEOUT = 20 * 1000;
    }
}
