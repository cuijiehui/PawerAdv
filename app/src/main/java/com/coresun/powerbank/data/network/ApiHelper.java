package com.coresun.powerbank.data.network;


import com.coresun.powerbank.data.network.response.AdvResponse;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public interface ApiHelper {
//    Observable<AdvResponse> getThemeDataCall(String path);
//
//    Observable<AdvResponse> getSearchDataCall(String content, String type, String page);

    Observable<AdvResponse> getAdvDataCall(RequestBody body);
    Observable<Response> getInitDataCall(RequestBody body);
    Observable<Response> getCheckPower(RequestBody body);
    Observable<Response> getStartTime(RequestBody body);
    Observable<UpdateResponse> getCheckUpdate(RequestBody body);
}
