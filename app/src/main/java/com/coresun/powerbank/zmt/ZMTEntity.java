//package com.coresun.powerbank.zmt;
//
//import com.google.gson.Gson;
//
///**
// * Created by Android on 2018/1/31.
// */
//
//public class ZMTEntity {
//
//
//    /**
//     * request_id : b8ffe828cadf40be995b4b5188cbfb49
//     * screen_id : 10302002
//     * channel_id : test
//     * token : 8UQ8JSTzaE35M5TWsawnJWWfDfjo5ZN2V5JlZAR2CCMzV30lVzOWmwd-RDJMUxVX
//     * device : {"device_type":"PHONE","os_type":"ANDROID","os_version":{"major":4,"minor":4,"micro":4},"vendor":"rockchip","model":"rk3188","udid":{"imei":"357789049271270","mac":"00:25:E1:05:00:A4","android_id":"3a717c76d46db29c"},"screen_size":{"width":1080,"height":1920}}
//     * network : {"ipv4":"192.168.149.1","connection_type":"CELL_4G","operator_type":"CHINA_MOBILE"}
//     * adslot : {"type":1,"adslot_size":{"width":1080,"height":1920}}
//     */
//
//    private String request_id;
//    private String screen_id;
//    private String channel_id;
//    private String token;
//    private DeviceBean device;
//    private NetworkBean network;
//    private AdslotBean adslot;
//
//    public static ZMTEntity objectFromData(String str) {
//
//        return new Gson().fromJson(str, ZMTEntity.class);
//    }
//
//    public String getRequest_id() {
//        return request_id;
//    }
//
//    public void setRequest_id(String request_id) {
//        this.request_id = request_id;
//    }
//
//    public String getScreen_id() {
//        return screen_id;
//    }
//
//    public void setScreen_id(String screen_id) {
//        this.screen_id = screen_id;
//    }
//
//    public String getChannel_id() {
//        return channel_id;
//    }
//
//    public void setChannel_id(String channel_id) {
//        this.channel_id = channel_id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public DeviceBean getDevice() {
//        return device;
//    }
//
//    public void setDevice(DeviceBean device) {
//        this.device = device;
//    }
//
//    public NetworkBean getNetwork() {
//        return network;
//    }
//
//    public void setNetwork(NetworkBean network) {
//        this.network = network;
//    }
//
//    public AdslotBean getAdslot() {
//        return adslot;
//    }
//
//    public void setAdslot(AdslotBean adslot) {
//        this.adslot = adslot;
//    }
//
//    public static class DeviceBean {
//        /**
//         * device_type : PHONE
//         * os_type : ANDROID
//         * os_version : {"major":4,"minor":4,"micro":4}
//         * vendor : rockchip
//         * model : rk3188
//         * udid : {"imei":"357789049271270","mac":"00:25:E1:05:00:A4","android_id":"3a717c76d46db29c"}
//         * screen_size : {"width":1080,"height":1920}
//         */
//
//        private String device_type;
//        private String os_type;
//        private OsVersionBean os_version;
//        private String vendor;
//        private String model;
//        private UdidBean udid;
//        private ScreenSizeBean screen_size;
//
//        public static DeviceBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, DeviceBean.class);
//        }
//
//        public String getDevice_type() {
//            return device_type;
//        }
//
//        public void setDevice_type(String device_type) {
//            this.device_type = device_type;
//        }
//
//        public String getOs_type() {
//            return os_type;
//        }
//
//        public void setOs_type(String os_type) {
//            this.os_type = os_type;
//        }
//
//        public OsVersionBean getOs_version() {
//            return os_version;
//        }
//
//        public void setOs_version(OsVersionBean os_version) {
//            this.os_version = os_version;
//        }
//
//        public String getVendor() {
//            return vendor;
//        }
//
//        public void setVendor(String vendor) {
//            this.vendor = vendor;
//        }
//
//        public String getModel() {
//            return model;
//        }
//
//        public void setModel(String model) {
//            this.model = model;
//        }
//
//        public UdidBean getUdid() {
//            return udid;
//        }
//
//        public void setUdid(UdidBean udid) {
//            this.udid = udid;
//        }
//
//        public ScreenSizeBean getScreen_size() {
//            return screen_size;
//        }
//
//        public void setScreen_size(ScreenSizeBean screen_size) {
//            this.screen_size = screen_size;
//        }
//
//        public static class OsVersionBean {
//            /**
//             * major : 4
//             * minor : 4
//             * micro : 4
//             */
//
//            private int major;
//            private int minor;
//            private int micro;
//
//            public static OsVersionBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, OsVersionBean.class);
//            }
//
//            public int getMajor() {
//                return major;
//            }
//
//            public void setMajor(int major) {
//                this.major = major;
//            }
//
//            public int getMinor() {
//                return minor;
//            }
//
//            public void setMinor(int minor) {
//                this.minor = minor;
//            }
//
//            public int getMicro() {
//                return micro;
//            }
//
//            public void setMicro(int micro) {
//                this.micro = micro;
//            }
//        }
//
//        public static class UdidBean {
//            /**
//             * imei : 357789049271270
//             * mac : 00:25:E1:05:00:A4
//             * android_id : 3a717c76d46db29c
//             */
//
//            private String imei;
//            private String mac;
//            private String android_id;
//
//            public static UdidBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, UdidBean.class);
//            }
//
//            public String getImei() {
//                return imei;
//            }
//
//            public void setImei(String imei) {
//                this.imei = imei;
//            }
//
//            public String getMac() {
//                return mac;
//            }
//
//            public void setMac(String mac) {
//                this.mac = mac;
//            }
//
//            public String getAndroid_id() {
//                return android_id;
//            }
//
//            public void setAndroid_id(String android_id) {
//                this.android_id = android_id;
//            }
//        }
//
//        public static class ScreenSizeBean {
//            /**
//             * width : 1080
//             * height : 1920
//             */
//
//            private int width;
//            private int height;
//
//            public static ScreenSizeBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, ScreenSizeBean.class);
//            }
//
//            public int getWidth() {
//                return width;
//            }
//
//            public void setWidth(int width) {
//                this.width = width;
//            }
//
//            public int getHeight() {
//                return height;
//            }
//
//            public void setHeight(int height) {
//                this.height = height;
//            }
//        }
//    }
//
//    public static class NetworkBean {
//        /**
//         * ipv4 : 192.168.149.1
//         * connection_type : CELL_4G
//         * operator_type : CHINA_MOBILE
//         */
//
//        private String ipv4;
//        private String connection_type;
//        private String operator_type;
//
//        public static NetworkBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, NetworkBean.class);
//        }
//
//        public String getIpv4() {
//            return ipv4;
//        }
//
//        public void setIpv4(String ipv4) {
//            this.ipv4 = ipv4;
//        }
//
//        public String getConnection_type() {
//            return connection_type;
//        }
//
//        public void setConnection_type(String connection_type) {
//            this.connection_type = connection_type;
//        }
//
//        public String getOperator_type() {
//            return operator_type;
//        }
//
//        public void setOperator_type(String operator_type) {
//            this.operator_type = operator_type;
//        }
//    }
//
//    public static class AdslotBean {
//        /**
//         * type : 1
//         * adslot_size : {"width":1080,"height":1920}
//         */
//
//        private int type;
//        private AdslotSizeBean adslot_size;
//
//        public static AdslotBean objectFromData(String str) {
//
//            return new Gson().fromJson(str, AdslotBean.class);
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public AdslotSizeBean getAdslot_size() {
//            return adslot_size;
//        }
//
//        public void setAdslot_size(AdslotSizeBean adslot_size) {
//            this.adslot_size = adslot_size;
//        }
//
//        public static class AdslotSizeBean {
//            /**
//             * width : 1080
//             * height : 1920
//             */
//
//            private int width;
//            private int height;
//
//            public static AdslotSizeBean objectFromData(String str) {
//
//                return new Gson().fromJson(str, AdslotSizeBean.class);
//            }
//
//            public int getWidth() {
//                return width;
//            }
//
//            public void setWidth(int width) {
//                this.width = width;
//            }
//
//            public int getHeight() {
//                return height;
//            }
//
//            public void setHeight(int height) {
//                this.height = height;
//            }
//        }
//    }
//}
