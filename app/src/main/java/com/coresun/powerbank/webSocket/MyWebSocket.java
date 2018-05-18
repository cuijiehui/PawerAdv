package com.coresun.powerbank.webSocket;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyWebSocket {
    private String HOST = "120.76.25.154";//120.76.25.154:2345
    private int PORT = 2345;
    private static final int MSG_TYPE_ONMEESSAGE = 1;
    private static final int MSG_TYPE_ONFAILURE = 2;
    private static final int MSG_TYPE_SEN_HEART_FAILURE = 3;
    private PrintWriter printWriter;
    private static final String TAG = "socket测试";
    private BufferedReader in;
    private ScheduledExecutorService mExecutorService = null;
    private String receiveMsg;
    private Activity mActivity;
    private MyWebSocketListener myWebSocketListener;
    private int reconnectionNum = 0;
    private Socket socket = null;
    private boolean isOpenSocket = true;
    private connectService mConnectService;
    private static MyWebSocket instance;

    public static synchronized MyWebSocket getInstance() {
        if (instance == null) {
            instance = new MyWebSocket();
        }
        return instance;
    }

    public void setListener(MyWebSocketListener myWebSocketListener) {
        this.myWebSocketListener = myWebSocketListener;
    }

    public void init(String HOST, int PORT, Activity mActivity) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.mActivity = mActivity;
        mExecutorService = Executors.newScheduledThreadPool(5);
    }

    public void init(String HOST, int PORT, Activity mActivity, MyWebSocketListener myWebSocketListener) {
        if (myWebSocketListener != null) {
            setListener(myWebSocketListener);
        }
        init(HOST, PORT, mActivity);
    }

    public void connect() {
        isOpenSocket = true;
        if (mConnectService == null) {
            mConnectService = new connectService();
            mExecutorService.scheduleWithFixedDelay(mConnectService, 0, 10 * 1000, TimeUnit.MILLISECONDS);
        }
    }

    public boolean send(String sendMsg) {
        Log.d(TAG, "sendMsg:" + sendMsg);
        if (null == socket) {
            return false;
        }
        boolean isConnected = socket.isConnected() && !socket.isClosed();
        if (isConnected) {
        }
        if (!socket.isClosed() && !socket.isOutputShutdown()) {
            mExecutorService.execute(new sendService(sendMsg));
        } else {
            return false;
        }
        return true;
    }

    public void disconnect() {
        socket = null;
        printWriter = null;
        in = null;
        isOpenSocket = false;
    }

    public void reStartSockt() {
        if (socket == null) {
            connect();
        }
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                OutputStream os = socket.getOutputStream();
                String message = msg + "\r\n";
                os.write(message.getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
                setMessageToUI(MSG_TYPE_SEN_HEART_FAILURE, e.getMessage());

            }
        }
    }

    private class connectService implements Runnable {
        @Override
        public void run() {
            Log.e(TAG, ("connectService:" + reconnectionNum));
            if (!isOpenSocket) {
                return;
            }
            if (socket != null) {
                reconnectionNum = 0;
             boolean isSend = send("Heartbeat");//发送心跳
                if(!isSend){
                    disconnect();
                }
                return;
            }
            reconnectionNum += 1;
            try {
                socket = new Socket(HOST, PORT);
//                socket.setSoTimeout(60000);
//                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
//                        socket.getOutputStream(), "UTF-8")), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                mExecutorService.execute(new startReceiveMsg());
            } catch (Exception e) {
                socket = null;
                printWriter = null;
                in = null;
                setMessageToUI(MSG_TYPE_ONFAILURE, e.getMessage());
                Log.e(TAG, ("connectService:" + e.getMessage()));
            }
        }
    }

    private class startReceiveMsg implements Runnable {
        @Override
        public void run() {
            receiveMsg();
        }
    }

    private void receiveMsg() {
        try {
            while (true) {
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);
                    setMessageToUI(MSG_TYPE_ONMEESSAGE, receiveMsg);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            setMessageToUI(MSG_TYPE_ONFAILURE, e.getMessage());
            e.printStackTrace();
        }
    }

    private void setMessageToUI(final int type, final Object message) {
        if (mActivity == null || myWebSocketListener == null) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case MSG_TYPE_ONMEESSAGE:
                        myWebSocketListener.onMessage((String) message);
                        break;
                    case MSG_TYPE_ONFAILURE:
                        myWebSocketListener.onFailure((String) message);
                        break;
                }
            }
        });

    }
}
