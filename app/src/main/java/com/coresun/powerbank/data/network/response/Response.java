package com.coresun.powerbank.data.network.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class Response {

    /**
     * code : 200
     * message ： 获取梯口机首页数据
     */

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public static Response objectFromData(String str) {

        return new Gson().fromJson(str, Response.class);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
