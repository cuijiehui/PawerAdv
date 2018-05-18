package com.coresun.powerbank.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.coresun.powerbank.bean.SocketBean;
import com.coresun.powerbank.ui.main.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class WebSocketUtils {
    private static Handler mHandler;
    private static ScheduledExecutorService mExecutorService = Executors.newScheduledThreadPool(5);
    public static final int MSG_TYPE_INFO=300;
    public static final int MSG_TYPE_SWITCH_NO=301; //开
    public static final int MSG_TYPE_SWITCH_OFF=302; //关
    public static final int MSG_TYPE_POWER=303;
    public synchronized static void startSocket(String host ,int port ,Handler handler ,String content ,int type){
        mHandler = handler;
        SocketBean socketBean = new SocketBean(host,port,content,type);
        mExecutorService.execute(new connectService(socketBean));
    }

    private static class connectService implements Runnable {
        SocketBean socketBean;

        public connectService(SocketBean socketBean) {
            this.socketBean = socketBean;
        }

        @Override
        public void run() {
            Socket  socket=null;
            BufferedReader  in=null;
            PrintWriter  out=null;
            String getLine=null;
            try {
                    LogUtils.i( "connection");
                      socket = new Socket(socketBean.getHOST(), socketBean.getPORT());//连接服务器
                    socket.setSoTimeout(60 * 1000);
                      in = new BufferedReader(new InputStreamReader(socket
                            .getInputStream()));//接收消息的流对象
                      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);//发送消息的流对象
//                    String content = "{\"MsgType\":\"GetSystemInfo\"}";
                    out.println(socketBean.getmContent());//点击按钮发送消息
                    if ((getLine = in.readLine()) != null) {//读取接收的信息
                        LogUtils.i("getLine=" + getLine);
                        getLine += "\n";
                        Message message = new Message();
                        message.obj = getLine;
                        mHandler.sendMessage(message);//通知UI更新
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally {
                socket=null;
                in=null;
                out=null;
                Message message = new Message();
                message.what= MainActivity.MSG_WHAT_SOCKET_MSG;
                message.obj = getLine;
                message.arg1=socketBean.getmType();
                mHandler.sendMessage(message);//通知UI更新
                }
            }
    }
}
