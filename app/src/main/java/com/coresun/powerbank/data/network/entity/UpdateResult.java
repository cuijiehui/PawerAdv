package com.coresun.powerbank.data.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Android on 2017/8/25.
 */

public class UpdateResult implements Parcelable {


    /**
     * "data": {
     "url": "https://shidecommunity.oss-cn-shenzhen.aliyuncs.com/apk/app_release_1.apk",
     "code": 1
     }
     */

    private String url;
    private int code =1;

    protected UpdateResult(Parcel in) {
        url = in.readString();
        code = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateResult> CREATOR = new Creator<UpdateResult>() {
        @Override
        public UpdateResult createFromParcel(Parcel in) {
            return new UpdateResult(in);
        }

        @Override
        public UpdateResult[] newArray(int size) {
            return new UpdateResult[size];
        }
    };

    public static UpdateResult objectFromData(String str) {

        return new Gson().fromJson(str, UpdateResult.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPics() {
        return url;
    }

    public void setPics(String pics) {
        this.url = pics;
    }
}
