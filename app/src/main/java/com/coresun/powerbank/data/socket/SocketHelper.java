package com.coresun.powerbank.data.socket;

/**
 * 实时获取更新数据
 * Created by Android on 2017/9/11.
 */

public interface SocketHelper {

    String getUpdateData(String url, int port, SocketRespInterface resp);

}
