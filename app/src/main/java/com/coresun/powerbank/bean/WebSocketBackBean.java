package com.coresun.powerbank.bean;

public class WebSocketBackBean {
    //{"id":"784c199a0b5500000002","type":1,"calback":"Equip\/setEquipIp"}
    //{"type":3,"calback":"Socket\/startTime","out_trade_no":"PO201805111346098342","time":"60"}
    private String id;//socket id
    private int type;//类型
    private String calback;//回调接口
    private String out_trade_no;//订单号
    private long time = 0;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCalback() {
        return calback;
    }

    public void setCalback(String calback) {
        this.calback = calback;
    }

    @Override
    public String toString() {
        return "WebSocketBackBean{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", calback='" + calback + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", time=" + time +
                '}';
    }
}
