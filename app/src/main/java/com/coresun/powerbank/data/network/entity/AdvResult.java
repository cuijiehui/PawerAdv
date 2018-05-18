package com.coresun.powerbank.data.network.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Android on 2017/8/25.
 */

public class AdvResult implements Parcelable {


    /**
     * pics : http://szpowerbank.oss-cn-beijing.aliyuncs.com/20180117/1.jpg,http://szpowerbank.oss-cn-beijing.aliyuncs.com/20180117/3.jpg,http://szpowerbank.oss-cn-beijing.aliyuncs.com/20180117/4.mp4,http://szpowerbank.oss-cn-beijing.aliyuncs.com/20180117/5.mp4
     */

    private String url;

    protected AdvResult(Parcel in) {
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdvResult> CREATOR = new Creator<AdvResult>() {
        @Override
        public AdvResult createFromParcel(Parcel in) {
            return new AdvResult(in);
        }

        @Override
        public AdvResult[] newArray(int size) {
            return new AdvResult[size];
        }
    };

    public static AdvResult objectFromData(String str) {

        return new Gson().fromJson(str, AdvResult.class);
    }

    public String getPics() {
        return url;
    }

    public void setPics(String pics) {
        this.url = pics;
    }
}
