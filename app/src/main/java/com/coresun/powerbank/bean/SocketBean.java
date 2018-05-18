package com.coresun.powerbank.bean;

public class SocketBean {
    private String HOST = "127.0.0.1";//服务器地址
    private int PORT = 6677;//连接端口号
    private String mContent ;
    private int mType ;

    public SocketBean(String HOST, int PORT, String mContent, int mType) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.mContent = mContent;
        this.mType = mType;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "SocketBean{" +
                "HOST='" + HOST + '\'' +
                ", PORT=" + PORT +
                ", mContent='" + mContent + '\'' +
                ", mType=" + mType +
                '}';
    }
}
