package com.coresun.powerbank.bean;

public class SocketPowerBean {
    /**
     * {"ErrorCode":"0000"}
     */
    private int ErrorCode ;//0000成功 1003失败

    public SocketPowerBean(int errorCode) {
        ErrorCode = errorCode;
    }

    @Override
    public String toString() {
        return "SocketPowerBean{" +
                "ErrorCode=" + ErrorCode +
                '}';
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }
}
