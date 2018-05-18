//package com.coresun.powerbank.bean;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.SerializedName;
//
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.Generated;
//
///**
// * Created by Android on 2017/9/8.
// */
//
//@Entity
//public class CachePswBean implements Parcelable{
//
//    @Id
//    @SerializedName("id")
//    private String id;
//    @SerializedName("deadTime")
//    private String deadTime;
//    @SerializedName("cachePsw")
//    private String cachePsw;
//
//    public CachePswBean() {
//    }
//
//    protected CachePswBean(Parcel in) {
//        deadTime = in.readString();
//        cachePsw = in.readString();
//    }
//
//    @Generated(hash = 82153781)
//    public CachePswBean(String id, String deadTime, String cachePsw) {
//        this.id = id;
//        this.deadTime = deadTime;
//        this.cachePsw = cachePsw;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(deadTime);
//        dest.writeString(cachePsw);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<CachePswBean> CREATOR = new Creator<CachePswBean>() {
//        @Override
//        public CachePswBean createFromParcel(Parcel in) {
//            return new CachePswBean(in);
//        }
//
//        @Override
//        public CachePswBean[] newArray(int size) {
//            return new CachePswBean[size];
//        }
//    };
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getDeadTime() {
//        return deadTime;
//    }
//
//    public void setDeadTime(String deadTime) {
//        this.deadTime = deadTime;
//    }
//
//    public String getCachePsw() {
//        return cachePsw;
//    }
//
//    public void setCachePsw(String cachePsw) {
//        this.cachePsw = cachePsw;
//    }
//
//    @Override
//    public String toString() {
//        return "CachePswBean{" +
//                "id=" + id +
//                ", deadTime='" + deadTime + '\'' +
//                ", cachePsw='" + cachePsw + '\'' +
//                '}';
//    }
//}
