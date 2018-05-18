package com.coresun.powerbank.bean;

/**
 * 广告的数据
 * Created by Android on 2018/3/26.
 */

public class AdvBean {
    private  int id; //顺序
    private  String advName;//广告名字
    private  String path;//本地缓存地址
    private String url;//网络地址
    private boolean isCache = false;//是否缓存 默认为F
    private int type;//广告类型 1：图片 2：视频

    public AdvBean(int id, String advName, String path, String url, boolean isCache, int type) {
        this.id = id;
        this.advName = advName;
        this.path = path;
        this.url = url;
        this.isCache = isCache;
        this.type = type;
    }

    public AdvBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdvName() {
        return advName;
    }

    public void setAdvName(String advName) {
        this.advName = advName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AdvBean{" +
                "id=" + id +
                ", advName='" + advName + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", isCache=" + isCache +
                ", type=" + type +
                '}';
    }
}
