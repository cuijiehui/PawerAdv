package com.coresun.powerbank.data.network;


import com.coresun.powerbank.data.network.response.AdvResponse;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@Singleton
public class AppApiHelper implements ApiHelper{
    ApiService mApiSerVice;

    @Inject
    public AppApiHelper(Retrofit retrofit) {
        this.mApiSerVice = retrofit.create(ApiService.class);
    }

    @Override
    public Observable<AdvResponse> getAdvDataCall(RequestBody body) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), param.toString());
        return mApiSerVice.getAdvDataCall(body);
    }

    @Override
    public Observable<Response> getInitDataCall(RequestBody body) {
        return mApiSerVice.getInitDataCall(body);

    }

    @Override
    public Observable<Response> getCheckPower(RequestBody body) {
        return mApiSerVice.getCheckPowerCall(body);
    }
    @Override
    public Observable<Response> getStartTime(RequestBody body) {
        return mApiSerVice.getStartTime(body);
    }

    @Override
    public Observable<UpdateResponse> getCheckUpdate(RequestBody body) {
        return mApiSerVice.getCheckUpdate(body);

    }

//    @Override
//    public Observable<AdvResponse> getThemeDataCall(String path) {
//        return mApiSerVice.getThemeDataCall(path);
//    }
//
//    @Override
//    public Observable<AdvResponse> getSearchDataCall(String content, String type, String page) {
//        return mApiSerVice.getSearchDataCall(content,type,page);
//    }


}
