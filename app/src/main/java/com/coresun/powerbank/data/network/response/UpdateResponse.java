package com.coresun.powerbank.data.network.response;

import com.coresun.powerbank.data.network.entity.AdvResult;
import com.coresun.powerbank.data.network.entity.UpdateResult;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class UpdateResponse extends Response{


    @SerializedName("data")
    private UpdateResult result;

    public static UpdateResponse objectFromData(String str) {

        return new Gson().fromJson(str, UpdateResponse.class);
    }

    public UpdateResult getResult() {
        return result;
    }

    public void setResults(UpdateResult result) {
        this.result = result;
    }


}
