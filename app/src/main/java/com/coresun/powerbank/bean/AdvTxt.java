package com.coresun.powerbank.bean;

public class AdvTxt {
    /**
     * {"id":"4","time":"10","filename":"1.jpg","url":"http:\/\/shidecommunity.oss-cn-shenzhen.aliyuncs.com\/newad\/201803\/09\/1.jpg","type":"1","size":"95.255"}
     */
    private int id;
    private int time;
    private String filename;
    private String url;
    private int type; //1为图片 2为视频
    private float size;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AdvTxt{" +
                "id=" + id +
                ", time=" + time +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}
