package com.coresun.powerbank.ui.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.Constants;
import com.coresun.powerbank.R;
import com.coresun.powerbank.base.BaseActivity;
import com.coresun.powerbank.base.RxBus;
import com.coresun.powerbank.bean.AdvBean;
import com.coresun.powerbank.bean.AdvTxt;
import com.coresun.powerbank.bean.SocketInfoBean;
import com.coresun.powerbank.bean.SocketPowerBean;
import com.coresun.powerbank.bean.WebSocketBackBean;
import com.coresun.powerbank.data.network.entity.AdvResult;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;
import com.coresun.powerbank.service.HeartBeatService;
import com.coresun.powerbank.ui.main.fragment.AdvBaseFragment;
import com.coresun.powerbank.ui.main.fragment.ImageViewFragment;
import com.coresun.powerbank.ui.main.fragment.VideoPlayFragment;
import com.coresun.powerbank.ui.main.viewpager.CustomViewPager;
import com.coresun.powerbank.ui.main.viewpager.NoPreloadViewPager;
import com.coresun.powerbank.util.FileUtils;
import com.coresun.powerbank.util.LogUtils;
import com.coresun.powerbank.util.SharedPreferencesUtils;
import com.coresun.powerbank.util.ToastUtils;
import com.coresun.powerbank.util.WebSocketUtils;
import com.coresun.powerbank.webSocket.MyWebSocket;
import com.coresun.powerbank.webSocket.MyWebSocketListener;
import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 梯口机主页
 */
public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.adv_viewpager)   //广告轮播
            CustomViewPager advViewpager;


    @Inject
    MainMVPPresenter<MainView> mainPresenter;
    List<AdvFragment> fragments = new ArrayList<>();
    private boolean isScroll = true;
    private MyPageAdapter advVPAdapter;

    private TimerTask timerTask;
    private Timer timer = new Timer();
    //    private String[] items;
    private Intent heartBeatService;
    private Observable<AdvResult> observable;
    /**
     * 请求CAMERA权限码
     */
    private static final int REQUEST_CAMERA_PERM = 101;
    private boolean isLoaded;

    private List<AdvBaseFragment> advDataList = new ArrayList<>();
    private String caChePath = "";
    private String advCatalogName = "";
    private String oldAdvCatalogName = "";
    private int viewPagerIndex; //ViewPager的下标值
    private boolean isDownload;
    private String localFile;
    MyBaseAdapter myBaseAdapter;
    ArrayList<AdvTxt> mAdvTxts = new ArrayList<>();
    ArrayList<AdvTxt> mShowAdvTxts = new ArrayList<>();
    private GSYSampleCallBack gsySampleCallBack = new GSYSampleCallBack() {
        @Override
        public void onAutoComplete(String url, Object... objects) {
            scrollAdvViewPager();
        }
    };
    public Handler mHandler;
    public static final int MSG_WHAT_DOW_CATALOG_OK = 562;
    public static final int MSG_WHAT_DOW_CATALOG_NO = -562;
    public static final int MSG_WHAT_DOW_ADV_OK = 200;
    public static final int MSG_WHAT_DOW_ADV_NO = -200;
    public static final int MSG_WHAT_DOW_FILE_OK = 233;
    public static final int MSG_WHAT_DOW_FILE_NO = -233;
    public static final int MSG_WHAT_SOCKET_MSG = 100;
    public static final int MSG_WHAT_POWER = 101;

    public int advToDowNum = 0;
    public long mTime = -1;//充电时间
    public SharedPreferencesUtils mSharedPreferencesUtils;
    public SocketInfoBean mSocketInfoBean;
    private WebSocketBackBean mWebSocketBackBean;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case MSG_WHAT_DOW_ADV_OK:
                        String path = (String) msg.obj;
                        int ID = msg.arg1;
                        advToDowNum = advToDowNum - 1;
                        for (AdvTxt advTxt : mAdvTxts) {
                            LogUtils.i("接口测试：获取广告文件：" + advTxt.toString());
                            if (advTxt.getId() == ID) {
                                advTxt.setPath(path);
                            }
                        }
                        if (advToDowNum == 0) {
                            mShowAdvTxts.clear();
                            for (AdvTxt advTxt : mAdvTxts) {
                                LogUtils.i("接口测试：获取配置广告文件：" + advTxt.toString());
                                if (!TextUtils.isEmpty(advTxt.getPath())) {
                                    mShowAdvTxts.add(advTxt);
                                }
                            }
                            showData();
                        }
                        break;
                    case MSG_WHAT_DOW_CATALOG_OK:
                        String catalogPath = (String) msg.obj;
                        if (!TextUtils.isEmpty(catalogPath)) {
                            File file = new File(catalogPath);
                            if (file.exists()) {
                                LogUtils.i("接口测试：获取配置文件：" + catalogPath);
                                mAdvTxts = FileUtils.refreshData(file);
                                LogUtils.i("接口测试：获取配置文件：" + mAdvTxts.size());
                                advToDowNum = advToDowNum + mAdvTxts.size();
                                FileUtils.dowAdvFile(mAdvTxts, mHandler, caChePath);
                            }
                        }
                        break;
                    case MSG_WHAT_DOW_ADV_NO:
                        advToDowNum = advToDowNum - 1;
                        if (advToDowNum == 0) {
                            showData();
                        }
                        break;
                    case MSG_WHAT_DOW_FILE_OK:
                        String filePath = (String) msg.obj;
                        FileUtils.installSlient(filePath, getPackageName(), MainActivity.this);
                        break;
                    case MSG_WHAT_SOCKET_MSG:
                        String socketMsg = (String) msg.obj;
                        int msgType = msg.arg1;
                        Gson gson = new Gson();
                        if (!TextUtils.isEmpty(socketMsg)) {
                            switch (msgType) {
                                case WebSocketUtils.MSG_TYPE_INFO:
                                    LogUtils.i("socket测试" + socketMsg);
                                    mSocketInfoBean = gson.fromJson(socketMsg, SocketInfoBean.class);
                                    Constants.DEVICE_ID = mSocketInfoBean.getIMEI1();
                                    LogUtils.i("socket测试 --socketInfoBean"
                                            + mSocketInfoBean.toString());
                                    startWebSocket();
                                    break;
                                case WebSocketUtils.MSG_TYPE_POWER:
                                    mSocketInfoBean = gson.fromJson(socketMsg, SocketInfoBean.class);
                                    LogUtils.i("socket测试 --socketInfoBean"
                                            + mSocketInfoBean.toString());
                                    mainPresenter.checkPower(mSocketInfoBean.getIMEI1(), mSocketInfoBean.getBatCapacity() + "");
                                    break;
                                case WebSocketUtils.MSG_TYPE_SWITCH_NO:
                                    LogUtils.i("socket测试" + socketMsg);
                                    SocketPowerBean socketPowerBean = gson.fromJson(socketMsg, SocketPowerBean.class);
                                    if (socketPowerBean.getErrorCode() == 0000) {
                                        Message message = new Message();
                                        message.what = MSG_WHAT_POWER;
                                        mHandler.sendMessageDelayed(message, mTime * 1000);
                                        String time = System.currentTimeMillis() + "";
                                        String sTime = time.toString().substring(0, time.toCharArray().length - 3);
                                        LogUtils.i("socket测试：" + sTime + "=time...." + "time=" + time);
                                        mainPresenter.getStartTime(mWebSocketBackBean.getOut_trade_no(), sTime, 1);
                                    } else {
                                        String time = System.currentTimeMillis() + "";
                                        String sTime = time.toString().substring(0, time.toCharArray().length - 3);
                                        LogUtils.i("socket测试：" + sTime + "=time...." + "time=" + time);
                                        mainPresenter.getStartTime(mWebSocketBackBean.getOut_trade_no(), sTime, 2);
                                    }
                                    break;
                                case WebSocketUtils.MSG_TYPE_SWITCH_OFF:
                                    LogUtils.i("socket测试" + socketMsg);
                                    SocketPowerBean socketPowerBeanO = gson.fromJson(socketMsg, SocketPowerBean.class);
                                    if (socketPowerBeanO.getErrorCode() == 0000) {
                                        LogUtils.i("socket测试" + "--OK-----");

                                    } else {

                                    }
                                    break;
                            }
                        } else {
                            ToastUtils.showToast("连接充电宝失败！");
                        }
                        break;
                    case MSG_WHAT_POWER:
                        mTime = -1;
                        startPowerSwitch(WebSocketUtils.MSG_TYPE_SWITCH_OFF);
                        break;
                }

            }
        };
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        caChePath = this.getExternalCacheDir() + File.separator + "adv";
        mSharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        mSharedPreferencesUtils.put(Constants.SHARE_KEY_ADV_CACHE_PATH, caChePath);
        hideBottomUIMenu();

//        initPermission();                           //初始化应用权限
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        setNetworkListener();
        mainPresenter.attachView(this);             //初始化MainPresenter
        registerRxBus();                            //接收RxBus发送的事件
        if (AdvApp.netWorkIsAvailable()) {
            initData();
            isLoaded = true;
        } else {
            ToastUtils.showToast("网络不可用");
        }
        LogUtils.e("RandomID:" + getRandom32Id());

    }

    /**
     * 初始化数据 首先去获取到唯一码
     */
    private void initData() {
////        mainPresenter.updateData();                 //实时更新数据
////        mainPresenter.loadData(mac);  //初入页面拉取数据
//        mainPresenter.initData(mac,ip);
//        LogUtils.i("idmac测试=--ip="+ip+"----mac="+mac);
//        startWebSocket();
        getDeviceInfo(WebSocketUtils.MSG_TYPE_INFO);
    }

    /**
     * 获取充电宝的信息
     *
     * @param type 是获取唯一码还是获取电量 操作不一样所以分开
     */
    private void getDeviceInfo(int type) {
        String content = "{\"MsgType\":\"GetSystemInfo\"}";
        WebSocketUtils.startSocket("127.0.0.1", 6677, mHandler, content, type);
    }

    /**
     * 打开电源开关
     *
     * @param ctlType 1 = 开 2 =关
     */
    private void startPowerSwitch(int ctlType) {
        //"{\"MsgType\":\"ChargerCtl\",\"CtlType\":\"Enable\"}"
        String content = "";
        if (ctlType == WebSocketUtils.MSG_TYPE_SWITCH_NO) {
            content = "{\"MsgType\":\"ChargerCtl\",\"CtlType\":\"Enable\"}";
        } else if (ctlType == WebSocketUtils.MSG_TYPE_SWITCH_OFF) {
            content = "{\"MsgType\":\"ChargerCtl\",\"CtlType\":\"Disable\"}";
        }
        LogUtils.i("socket测试=" + content);
        WebSocketUtils.startSocket("127.0.0.1", 6677, mHandler, content, ctlType);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 初始化viewpage
     */
    private void initViewPager() {
        myBaseAdapter = new MyBaseAdapter(getSupportFragmentManager(), advDataList);
        advViewpager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AdvBaseFragment fragment1 = advDataList.get(position);
                int time = mAdvTxts.get(position).getTime();
                LogUtils.i("接口测试：图片时间=" + time);
                if (fragment1.getTYPE() == 1) {
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            scrollAdvViewPager();
//                        }
//                    }, time * 1000);
                    delayScroll(time);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        advViewpager.setAdapter(myBaseAdapter);
    }

    private void setNetworkListener() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReciver, filter);
    }

    @SuppressLint("CheckResult")
    private void registerRxBus() {
        observable = RxBus.getInstance().register(AdvResult.class);
        observable.subscribe(new Consumer<AdvResult>() {
            @Override
            public void accept(AdvResult advResult) throws Exception {
                LogUtils.e("从心跳传递过来的 -->" + advResult);
                fragments.clear();
                setData(advResult);
            }
        });
    }

    private MyWebSocket myWebSocket;

    private void sendHeartBeat() {
        heartBeatService = new Intent(this, HeartBeatService.class);
        startService(heartBeatService);
    }

    private void startWebSocket() {
        myWebSocket = MyWebSocket.getInstance();
        myWebSocket.init("120.76.25.154", 2345, this, myWebSocketListener);
        myWebSocket.connect();
    }

    private MyWebSocketListener myWebSocketListener = new MyWebSocketListener() {
        @Override
        public void onOpen(String response) {
            LogUtils.i("socket测试：" + response);

        }

        @Override
        public void onMessage(String message) {
            Gson gson = new Gson();
            mWebSocketBackBean = gson.fromJson(message, WebSocketBackBean.class);
            LogUtils.i("socket测试：" + mWebSocketBackBean.toString());
            switch (mWebSocketBackBean.getType()) {
                case 1:
                    LogUtils.i("socket测试：" + mSocketInfoBean.getIMEI1());
                    LogUtils.i("socket测试：" + mWebSocketBackBean.getId());
                    mainPresenter.initData(mSocketInfoBean.getIMEI1() + "", mWebSocketBackBean.getId());
                    break;
                case 2:
                    getDeviceInfo(WebSocketUtils.MSG_TYPE_POWER);
                    break;
                case 3:
                    mTime = mWebSocketBackBean.getTime();
                    startPowerSwitch(WebSocketUtils.MSG_TYPE_SWITCH_NO);
                    break;
            }

        }

        @Override
        public void onFailure(String response) {
            LogUtils.i("socket测试：" + response);

        }
    };

    @Override
    public void updateView(String result) {
        if (!result.equals("400")) {
            //TODO 接收到服务器推送过来的消息，进行实时更新
            ToastUtils.showToast("接收到: " + result);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            scrollAdvViewPager();
        }
    };

    private void delayScroll(int time) {
        mHandler.postDelayed(runnable, time * 1000);
    }

    private void scrollAdvViewPager() {
        if (isScroll) {
            viewPagerIndex = advViewpager.getCurrentItem();
            if (viewPagerIndex == advDataList.size() - 1) {
                viewPagerIndex = 0;
            } else {
                viewPagerIndex += 1;
            }
            advViewpager.setCurrentItem(viewPagerIndex);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void refreshUI() {
        //无用则无需编写代码
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showError() {
        Toast.makeText(this, "加载数据失败，请检查网络！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showView(Object result, int type) {
        switch (type) {
            case Constants.SHOW_VIEW_TYPE_INIT_DATA:
                LogUtils.i("接口测试：" + Constants.SHOW_VIEW_TYPE_INIT_DATA);
                Response response = (Response) result;
                if (response.getCode().equals("200")) {
                    mainPresenter.loadData(Constants.DEVICE_ID);
                    mainPresenter.checkUpdate(Constants.DEVICE_ID);
                    sendHeartBeat();                            //开始定时获取广告服务
                } else {
                    String msg = "";
                    try {
                        msg = new String(response.getMessage().getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    LogUtils.i("接口测试= getMessage=" + msg);
                }
                break;
            case Constants.SHOW_VIEW_TYPE_ADV_DATA:
                setData((AdvResult) result);

                break;
            case Constants.SHOW_VIEW_TYPE_GET_POWER:

                break;
            case Constants.SHOW_VIEW_TYPE_GET_START_TIME:

                break;
            case Constants.SHOW_VIEW_TYPE_CHECK_UPDATE:
                UpdateResponse update = (UpdateResponse) result;
                PackageManager manager = getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (info != null) {
                    int oldCode = info.versionCode;
                    LogUtils.i("接口测试= updategetCode=" + update.getResult().getCode());
                    LogUtils.i("接口测试= updategetCode=" + oldCode);
                    if (oldCode < update.getResult().getCode()) {
                        FileUtils.dowFile(update.getResult().getPics(), caChePath, mHandler);
                    }
                }
                break;

        }
    }

    /**
     * 配置list 加载给viewpager展示
     */
    private void showData() {
        LogUtils.i("接口测试：showData");
        if (mShowAdvTxts.size() > 0) {
            advDataList.clear();
            for (AdvTxt advTxt : mShowAdvTxts) {
                AdvBean advBean = new AdvBean();
                advBean.setId(advTxt.getId());
                advBean.setAdvName(advTxt.getFilename());
                advBean.setType(advTxt.getType());
                advBean.setUrl(advTxt.getPath());
                if (advTxt.getType() == 1) {
                    //图片
                    LogUtils.i("接口测试：展示测试：图片" + advTxt.toString());
                    ImageViewFragment imageViewFragment = new ImageViewFragment(advBean);
                    advDataList.add(imageViewFragment);
                } else if (advTxt.getType() == 2) {
                    //视频
                    LogUtils.i("接口测试：展示测试：视频" + advTxt.toString());
                    VideoPlayFragment fragment =
                            new VideoPlayFragment(advBean, gsySampleCallBack);
                    advDataList.add(fragment);
                }
            }
            mHandler.removeCallbacks(runnable);//防止多个计时器在运行
            if (myBaseAdapter == null) {
                initViewPager();
            } else {
                myBaseAdapter.notifyDataSetChanged();
            }

            if (advDataList.get(0).getTYPE() == 1 && advDataList.size() > 1) {
                int time = mAdvTxts.get(0).getTime();
                delayScroll(time);
            }
        }
        String newCatalogName = (String) mSharedPreferencesUtils.getSharedPreference(Constants.SHARE_KEY_ADV_CATALOG_NAME, SharedPreferencesUtils.SHARE_STRING);
        FileUtils.deleteAdvFile(oldAdvCatalogName, newCatalogName, caChePath);
        deleteOldFile();
    }

    private void deleteOldFile() {
        String newCatalogName = (String) mSharedPreferencesUtils.getSharedPreference(Constants.SHARE_KEY_ADV_CATALOG_NAME, SharedPreferencesUtils.SHARE_STRING);
        if (!newCatalogName.equals(oldAdvCatalogName)) {
            FileUtils.deleteFile(caChePath + File.separator + oldAdvCatalogName);
        }
    }


    private void setData(AdvResult result) {
        String strs = result.getPics();
        LogUtils.i("接口测试：strs=" + strs);
        if (strs != null) {
            dowAdvData(strs);//去下载播放列表
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void dowAdvData(final String url) {

        //文件夹
        File folder = new File(caChePath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdir();
        }
        //文件名
        advCatalogName = url.substring(url.lastIndexOf("/") + 1, url.length());
        localFile = caChePath + File.separator + advCatalogName;
        oldAdvCatalogName = (String) mSharedPreferencesUtils.getSharedPreference(Constants.SHARE_KEY_ADV_CATALOG_NAME, SharedPreferencesUtils.SHARE_STRING);
        LogUtils.e("接口测试：localFile" + oldAdvCatalogName + "-----" + advCatalogName);
        File file = new File(localFile);
        LogUtils.e("接口测试：localFile" + !file.exists());
        if (!oldAdvCatalogName.equals(advCatalogName) || !file.exists()) {
            LogUtils.e("接口测试：localFile" + localFile);
            OkHttpUtils.get()
                    .url(url)
                    .build()
                    .execute(new FileCallBack(caChePath, advCatalogName) {
                        @Override
                        public void inProgress(float progress) {
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            LogUtils.e("接口测试：下载失败！\n" + e.toString());
                        }

                        @Override
                        public void onResponse(File response) {
//                        isDownload = true;
                            LogUtils.i("接口测试：下载完成！" + localFile);
                            mSharedPreferencesUtils.put(Constants.SHARE_KEY_ADV_CATALOG_NAME, advCatalogName);
                            Message msg = new Message();
                            msg.what = MSG_WHAT_DOW_CATALOG_OK;
                            msg.obj = localFile;
                            mHandler.sendMessage(msg);
                        }
                    });
        } else {
            mSharedPreferencesUtils.put(Constants.SHARE_KEY_ADV_CATALOG_NAME, advCatalogName);
            Message msg = new Message();
            msg.what = MSG_WHAT_DOW_CATALOG_OK;
            msg.obj = localFile;
            mHandler.sendMessage(msg);
        }
    }


//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        ToastUtils.showToast("已同意" + perms + "权限");
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        LogUtils.e("已拒绝" + perms + "权限");
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            for (int i = 0; i < perms.size() - 1; i++) {
//                if (perms.get(i).equals(Manifest.permission.CAMERA)) {
//                    new AppSettingsDialog.Builder(this)
//                            .setTitle("权限申请")
//                            .setRationale("当前app需要调用相机权限，需要打开设置页面吗?")
//                            .setNegativeButton("拒绝")
//                            .setPositiveButton("同意")
//                            .setRequestCode(REQUEST_CAMERA_PERM)
//                            .build()
//                            .show();
//                }
//            }
//        }
//    }

//    /**
//     * 调用相机时验证权限
//     * 例如，扫二维码，照相，录制视频等功能时需要验证此权限。
//     */
//    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
//    private void cameraTask() {
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
//            //拥有相机权限。
//            ToastUtils.showToast("已开启相机权限");
//        } else {
//            EasyPermissions.requestPermissions(this, "当前操作需要相机权限喔~", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
//        }
//    }

    class MyPageAdapter extends FragmentStatePagerAdapter {
        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private BroadcastReceiver networkReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("网络状态改变");
            //获得网络连接服务
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            // 获取当前的网络连接是否可用
            boolean available = networkInfo.isAvailable();
            if (available) {
                Log.i("通知", "当前的网络连接可用");
                NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
                if (NetworkInfo.State.CONNECTED == state) { // 如果正在使用WIFI网络
                    if (isLoaded) return;
//                    initData();
                    return;
                }
                state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
                if (NetworkInfo.State.CONNECTED == state) { // 如果正在使用GPRS网络
                    if (isLoaded) return;
//                    initData();
                }
                myWebSocket.reStartSockt();
            } else {
                Log.i("通知", "当前的网络连接不可用");
                myWebSocket.disconnect();
            }


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        mainPresenter.onDetach();
        ButterKnife.unbind(this);
        RxBus.getInstance().unregister(this, observable);
        if (heartBeatService != null) {
            stopService(heartBeatService);
        }

        unregisterReceiver(networkReciver);
        isLoaded = false;
        fragments.clear();
    }

    public String getRandom32Id() {
        String[] randoms = UUID.randomUUID().toString().split("-");
        StringBuilder builder = new StringBuilder();
        for (String s : randoms) {
            builder.append(s);
        }
        LogUtils.e("字节长度：" + builder.toString().length());
        return builder.toString();
    }

    class MyBaseAdapter extends FragmentPagerAdapter {
        List<AdvBaseFragment> mDataList = new ArrayList<>();

        public MyBaseAdapter(FragmentManager fm, List<AdvBaseFragment> dataList) {
            super(fm);
            mDataList = dataList;
        }

        @Override
        public Fragment getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }
    }
}
