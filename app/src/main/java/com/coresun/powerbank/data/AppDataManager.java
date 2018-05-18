package com.coresun.powerbank.data;



import com.coresun.powerbank.data.network.ApiHelper;
import com.coresun.powerbank.data.network.response.AdvResponse;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;
import com.coresun.powerbank.data.preference.SharePreferenecesHelper;
import com.coresun.powerbank.data.socket.SocketHelper;
import com.coresun.powerbank.data.socket.SocketRespInterface;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 项目总管理。
 * 操控数据业务：网络请求，偏好设置，数据库，Socket长连接的管理类
 * Created by Administrator on 2017/3/29 0029.
 */
@Singleton
public class AppDataManager implements DataManager {
    SocketHelper socketHelper;
    ApiHelper apiHelper;
    SharePreferenecesHelper sharePreferenecesHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper,
                          SharePreferenecesHelper sharePreferenecesHelper,
                          SocketHelper socketHelper) {
        this.apiHelper=apiHelper;
        this.sharePreferenecesHelper=sharePreferenecesHelper;
        this.socketHelper = socketHelper;
    }

    @Override
    public Observable<AdvResponse> getAdvDataCall(RequestBody body) {
        return apiHelper.getAdvDataCall(body);
    }

    @Override
    public Observable<Response> getInitDataCall(RequestBody body) {
        return apiHelper.getInitDataCall(body);

    }

    @Override
    public Observable<Response> getCheckPower(RequestBody body) {
        return apiHelper.getCheckPower(body);

    }
    @Override
    public Observable<Response> getStartTime(RequestBody body) {
        return apiHelper.getStartTime(body);

    }

    @Override
    public Observable<UpdateResponse> getCheckUpdate(RequestBody body) {
        return apiHelper.getCheckUpdate(body);

    }

    @Override
    public String getUpdateData(String url, int port, SocketRespInterface respInterface) {
        return socketHelper.getUpdateData(url, port, respInterface);
    }

    @Override
    public void setExitCount(int count) {
        sharePreferenecesHelper.setExitCount(count);
    }

    @Override
    public int getEixtCount() {
        return sharePreferenecesHelper.getEixtCount();
    }
}
