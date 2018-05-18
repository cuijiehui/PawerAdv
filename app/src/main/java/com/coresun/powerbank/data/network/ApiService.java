package com.coresun.powerbank.data.network;

import com.coresun.powerbank.data.network.response.AdvResponse;
import com.coresun.powerbank.data.network.response.Response;
import com.coresun.powerbank.data.network.response.UpdateResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public interface ApiService {
//    @GET("data/{path}")
//    Observable<AdvResponse> getThemeDataCall(@Path("path") String path);
//
//    @GET("search/query/{content}/category/{type}/count/")
//    Observable<AdvResponse> getSearchDataCall(@Path("content") String content, @Path("type") String type, @Path("page") String page);

//    @POST("api/Ladder/getChargeAd")
    @POST("api/Ladder/getSowing")
    Observable<AdvResponse> getAdvDataCall(@Body RequestBody body);

    @POST("api/Equip/setEquipIp")
    Observable<Response> getInitDataCall(@Body RequestBody body);

    @POST("api/Socket/checkPower")
    Observable<Response> getCheckPowerCall(@Body RequestBody body);

    @POST("api/Socket/startTime")
    Observable<Response> getStartTime(@Body RequestBody body);
    @POST("api/Version/getVersion")
    Observable<UpdateResponse> getCheckUpdate(@Body RequestBody body);
//
//    @POST("/zmt/api/ad/getAd.do")
//    Observable<AdvResponse> getZMTDataCall();

}
