package com.coresun.powerbank.data.socket;

import com.coresun.powerbank.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * 实时接收数据，例如用户的临时密码。
 * 使用Socket实现与后台进行长连接，实时接收后台接收过来的数据。
 * Created by Android on 2017/9/11.
 */
public class AppSocketManager implements SocketHelper {

    private ExecutorService mThreadPool;
    private Socket mSocket;
    private String response;
    private static final String CONNECT_FAILED = "400";

    @Inject
    public AppSocketManager() {
        mThreadPool = Executors.newCachedThreadPool();
    }

    @Override
    public String getUpdateData(final String url, final int port, final SocketRespInterface respInterface) {

        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("socket Thread" + Thread.currentThread().getName());
                BufferedReader reader = null;
                try {
                    mSocket = new Socket("192.168.1.71", 8989);
                    if (mSocket.isConnected()){//如果已经与后台进行了长连接
                        OutputStream out = mSocket.getOutputStream();
                        String s = "wake up\n";
                        out.write(s.getBytes("utf-8"));
                        out.flush();
                        reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                        while ((response = reader.readLine())!=null){
                            response = reader.readLine();
                            respInterface.onRealResponse(response);
                        }
                    }
                    else{
                        response = CONNECT_FAILED;//与后台进行长连接失败
                        respInterface.onFailResponse(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response = CONNECT_FAILED;
                    respInterface.onConectFail(e);
                }
                finally {
                    try {
                        if (mSocket!=null){
                            mSocket.close();
                        }
                        if (reader!=null){
                            reader.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return null;
    }
}
