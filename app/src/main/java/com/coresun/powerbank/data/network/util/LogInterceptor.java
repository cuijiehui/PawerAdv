package com.coresun.powerbank.data.network.util;

import com.coresun.powerbank.util.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Android on 2017/8/28.
 */

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.i("request:\n" + request.toString());
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        LogUtils.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        MediaType type = response.body().contentType();
        String content = response.body().string();
        LogUtils.i("response body:\n" + content);
        return response.newBuilder().body(ResponseBody.create(type, content)).build();
    }

}
