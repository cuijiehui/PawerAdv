package com.coresun.powerbank.bean;

public class SocketInfoBean {
    /**
     * {
     * "SignalLevel": "4",
     * "WLanMac": "00:e0:4c:81:96:c1",
     * "MsgType": "GetSystemInfo",
     * "BatCharging": "True",
     * "MCCMNC": "46006",
     * "IMSI": "460060093038250",
     * "WLanStaCount": "0",
     * "NetworkTypeName": "LTE",
     * "BatCapacity": "6",
     * "IMEI1": "861179030353627",
     * "ICCID": "89860617030008882501",
     * "DataNetworkTypeClass": "3",
     * "SimSlotIndex": "0",
     * "VersionCode": "1550",
     * "IMSI2": "",
     * "WLanSSID": "",
     * "NetType": "Connected",
     * "WLanStaList": "",
     * "WLanKey": "",
     * "Version": "V1.1",
     * "ICCID2": "",
     * "DeviceVersion": "4G_LTE_5M_H32_C27_MV1.407",
     * "IMEI2": "861179030353627",
     * "WLanStaMaxCount": ""
     * }
     */
    private int BatCapacity;
    private String IMEI1;
    private String WLanMac;//系统厂家说不一定有

    public SocketInfoBean(int batCapacity, String IMEI1, String WLanMac) {
        BatCapacity = batCapacity;
        this.IMEI1 = IMEI1;
        this.WLanMac = WLanMac;
    }

    @Override
    public String toString() {
        return "SocketInfoBean{" +
                "BatCapacity=" + BatCapacity +
                ", IMEI1='" + IMEI1 + '\'' +
                ", WLanMac='" + WLanMac + '\'' +
                '}';
    }

    public int getBatCapacity() {
        return BatCapacity;
    }

    public void setBatCapacity(int batCapacity) {
        BatCapacity = batCapacity;
    }

    public String getIMEI1() {
        return IMEI1;
    }

    public void setIMEI1(String IMEI1) {
        this.IMEI1 = IMEI1;
    }

    public String getWLanMac() {
        return WLanMac;
    }

    public void setWLanMac(String WLanMac) {
        this.WLanMac = WLanMac;
    }
}
