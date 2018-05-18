package com.coresun.powerbank.data.socket;

/**
 * Created by Android on 2017/9/11.
 */

public interface SocketRespInterface {

    void onRealResponse(String resp);

    void onFailResponse(String errorCode);

    void onConectFail(Throwable throwable);

}
