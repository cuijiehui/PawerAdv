//package com.coresun.powerbank.zmt;
//
///**
// * Created by Android on 2018/1/31.
// */
//
//public class ZMTData {
//
//    public static ZMTEntity getZMTData(){
//
////        {
////            "request_id": "b8ffe828cadf40be995b4b5188cbfb49",
////                "screen_id": "10302002",
////                "channel_id": "test",
////                "token": "8UQ8JSTzaE35M5TWsawnJWWfDfjo5ZN2V5JlZAR2CCMzV30lVzOWmwd-RDJMUxVX",
////                "device": {
////            "device_type": "PHONE",
////                    "os_type": "ANDROID",
////                    "os_version": {
////                "major": 4,
////                        "minor": 4,
////                        "micro": 4
////            },
////            "vendor": "rockchip",
////                    "model": "rk3188",
////                    "udid": {
////                "imei": "357789049271270",
////                        "mac": "00:25:E1:05:00:A4",
////                        "android_id": "3a717c76d46db29c"
////            },
////            "screen_size": {
////                "width": 1080,
////                        "height": 1920
////            }
////        },
////            "network": {
////            "ipv4": "192.168.149.1",
////                    "connection_type": "CELL_4G",
////                    "operator_type": "CHINA_MOBILE"
////        },
////            "adslot": {
////            "type": 1,
////                    "adslot_size": {
////                "width": 1080,
////                        "height": 1920
////            }
////        }
////        }
//
//        ZMTEntity zmt = new ZMTEntity();
//
//        zmt.setRequest_id("b8ffe828cadf40be995b4b5188cbfb49");
//        zmt.setScreen_id("10302002");
//        zmt.setChannel_id("test");
//        zmt.setToken("8UQ8JSTzaE35M5TWsawnJWWfDfjo5ZN2V5JlZAR2CCMzV30lVzOWmwd-RDJMUxVX");
//
//        //Device
//        ZMTEntity.DeviceBean device = new ZMTEntity.DeviceBean();
//        device.setDevice_type("PHONE");
//        device.setOs_type("ANDROID");
//        device.setVendor("rockchip");
//        device.setModel("rk3188");
//
//            //Device-->osVersion
//            ZMTEntity.DeviceBean.OsVersionBean osVersion = new ZMTEntity.DeviceBean.OsVersionBean();
//            osVersion.setMajor(4);
//            osVersion.setMicro(4);
//            osVersion.setMinor(4);
//            device.setOs_version(osVersion);
//
//            //Device-->Udid
//            ZMTEntity.DeviceBean.UdidBean udid = new ZMTEntity.DeviceBean.UdidBean();
//            udid.setAndroid_id("3a717c76d46db29c");
//            udid.setImei("357789049271270");
//            udid.setMac("00:25:E1:05:00:A4");
//            device.setUdid(udid);
//
//            //Device-->ScreenSize
//            ZMTEntity.DeviceBean.ScreenSizeBean screenSize = new ZMTEntity.DeviceBean.ScreenSizeBean();
//            screenSize.setWidth(1080);
//            screenSize.setHeight(1920);
//            device.setScreen_size(screenSize);
//
//        zmt.setDevice(device);//device
//
//            ZMTEntity.NetworkBean network = new ZMTEntity.NetworkBean();
//            network.setConnection_type("CELL_4G");
//            network.setIpv4("192.168.149.1");
//            network.setOperator_type("CHINA_MOBILE");
//
//        zmt.setNetwork(network);//network
//
//            ZMTEntity.AdslotBean adslot = new ZMTEntity.AdslotBean();
//            adslot.setType(1);
//                ZMTEntity.AdslotBean.AdslotSizeBean adslotSize = new ZMTEntity.AdslotBean.AdslotSizeBean();
//                adslotSize.setWidth(1080);
//                adslotSize.setHeight(1920);
//            adslot.setAdslot_size(adslotSize);
//
//        zmt.setAdslot(adslot);//adslot
//
//        return zmt;
//    }
//}
