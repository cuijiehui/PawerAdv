package com.coresun.powerbank.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coresun.powerbank.AdvApp;
import com.coresun.powerbank.data.network.entity.AdvResult;
import com.coresun.powerbank.data.network.entity.Params;
import com.coresun.powerbank.ui.main.MainView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Volley网络请求框架
 * 暂时弃用，当前使用RxJava+Rerofit+Okhttp3模式
 * Created by Android on 2017/8/29.
 */

public class VolleyHttpUtil {

    private static RequestQueue queue;

//    public static VolleyHttpUtil newInstance(){
//        VolleyHttpUtil v = new VolleyHttpUtil();
//        if (queue == null){
//            queue = Volley.newRequestQueue(AdvApp.getAppContext());
//        }
//        return v;
//    }
//
//    public static void sendPost(String url, List<Params> params, final MainView mainview) throws JSONException {
//        JSONObject json = new JSONObject();
//        Log.i("www", "请求url=" + url);
//        if (params.size() != 0) {
//            for (int i = 0; i < params.size(); i++) {
//                Params obj = params.get(i);
//                String name = obj.getName();
//                String value = obj.getValue();
//                Log.i("www", "数据请求参数" + "name=" + name + ";value=" + value);
//                json.put(name, value);
//            }
//        }
//
//        LogUtils.i("json-----------" + json.toString());
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    Log.i("www", "post请求成功:" + jsonObject.toString());
//
//                    if (jsonObject != null){
//                        String json = jsonObject.get("data").toString();
//                        Gson gson = new Gson();
//                        AdvResult result = gson.fromJson(json, AdvResult.class);
//                        mainview.showView(result);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.i("www", "post请求失败:" + volleyError.getMessage());
//                mainview.showError();
//            }
//        });
//        queue.add(request);
//    }

}
