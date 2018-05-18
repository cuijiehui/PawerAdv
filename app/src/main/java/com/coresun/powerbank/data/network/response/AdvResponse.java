package com.coresun.powerbank.data.network.response;

import com.coresun.powerbank.data.network.entity.AdvResult;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class AdvResponse extends Response{


    @SerializedName("data")
    private AdvResult result;

    public static AdvResponse objectFromData(String str) {

        return new Gson().fromJson(str, AdvResponse.class);
    }

    public AdvResult getResult() {
        return result;
    }

    public void setResults(AdvResult result) {
        this.result = result;
    }


}
