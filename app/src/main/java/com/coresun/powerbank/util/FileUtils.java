package com.coresun.powerbank.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.coresun.powerbank.bean.AdvTxt;
import com.coresun.powerbank.ui.main.MainActivity;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created cui on 17/7/30.
 */
public class FileUtils {

    private static final String HTTP_CACHE_DIR = "http";

    public static final File getHttpCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return new File(context.getExternalCacheDir(), HTTP_CACHE_DIR);
        }
        return new File(context.getCacheDir(), HTTP_CACHE_DIR);
    }

    public static boolean unzip(String zipFilePath, String targetPath)
            throws IOException {
        boolean isUnzip=false;
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFilePath, "gbk");
            String directoryPath = "";
            if (null == targetPath || "".equals(targetPath)) {
                directoryPath = zipFilePath.substring(0, zipFilePath
                        .lastIndexOf("."));
            } else {
                directoryPath = targetPath + getFileName(zipFilePath);
            }

            Enumeration entryEnum = zipFile.getEntries();
            if (null != entryEnum) {
                ZipArchiveEntry zipEntry = null;
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipArchiveEntry) entryEnum.nextElement();
                    if (zipEntry.getSize() > 0) {
                            // 文件
                            LogUtils.i("接口测试：解压测试zipFilePathgetname="+zipEntry.getName());
                            File targetFile = buildFile(directoryPath
                                    + File.separator + zipEntry.getName(), false);
                            if(targetFile.exists()){
                                LogUtils.i("接口测试：解压测试zipFilePath="+targetFile.getAbsolutePath());
                                isUnzip=true;
                            }else{
                             os = new BufferedOutputStream(new FileOutputStream(
                                    targetFile));
                            is = zipFile.getInputStream(zipEntry);
                            byte[] buffer = new byte[4096];
                            int readLen = 0;
                            while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
                                os.write(buffer, 0, readLen);
                            }
                            os.flush();
                            os.close();
                            isUnzip=true;
                            }

                    } else {
                        // 空目录
                        buildFile(directoryPath + File.separator
                                + zipEntry.getName(), true);
                        isUnzip=true;
                    }
                }
            }
        } catch (IOException ex) {
            Log.d("hck", "IOExceptionIOException: " + ex.toString());
            isUnzip=false;
            throw ex;
        } finally {
            if (null != zipFile) {
                zipFile = null;
            }
            if (null != is) {
                is.close();
            }
            if (null != os) {
                os.close();
            }
            return isUnzip;
        }
    }

    //创建目录or文件
    public static File buildFile(String fileName, boolean isDirectory) {
        File target = new File(fileName);
        if (isDirectory) {
            target.mkdirs();
        } else {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
                target = new File(target.getAbsolutePath());
            }
        }
        return target;
    }

    /**
     * 获取文件名字
     * @param pathandname  路径
     * @return
     */
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }
    }

    /**
     * 根据条件返回符合条件的文件
     * @param dirPath 文件夹地址
     * @param reg 正则
     * @return 需要找的文件
     */
    public static File refreshFileList(String dirPath ,String reg){
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        File findFile=null;
        if(files==null){
            return findFile;
        }
        for (File file:files){
            if(!file.isDirectory()){
               String fileName = file.getName();
               if (fileName.contains(reg)){
                   findFile=file;
               }
            }
        }
        dir=null;
        files=null;
        return findFile;
    }

    /**
     * 将文件里面的json解析成bean
     * @param file 需要解析的文件
     * @return
     */
    public static ArrayList<AdvTxt> refreshData(File file){
        ArrayList<AdvTxt>  advTxts = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int length=inputStream.available();
            byte [] bytes = new byte[length];
            inputStream.read(bytes);

           String res = EncodingUtils.getString(bytes, "UTF-8");
            Gson gson = new Gson();
            AdvTxt[] ress= gson.fromJson(res,AdvTxt[].class);
            for (AdvTxt re : ress){
                advTxts.add(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return advTxts;

    }
    public static void deleteAdvFile(String oldPath,String newPath,String cachePath){
        if(!oldPath.equals(newPath)){
            File oldFile = new File (cachePath+ File.separator +oldPath);
            File newFile = new File (cachePath+ File.separator+newPath);
            if(!oldFile.exists()){
                return;
            }
            ArrayList<AdvTxt>  oldAdvTxts = FileUtils.refreshData(oldFile);
            ArrayList<AdvTxt>   newAdvTxts = FileUtils.refreshData(newFile);
            for (AdvTxt oldAdvTxt:oldAdvTxts ){
                for (AdvTxt newAdvTxt:newAdvTxts){
                    if(!oldAdvTxt.getFilename().equals(newAdvTxt.getFilename())){
                        deleteFile(cachePath+File.separator+ oldAdvTxt.getFilename());
                    }
                }
            }
        }
    }
    /**
     * 删除文件
     * @param path 文件根目录
     *
     */
    public static void deleteFile(String path){
        LogUtils.i("接口测试：删除文件"+path);
        File file = new File(path);
        if(file.exists()){
            if(file.isDirectory()){
                deleteAllFiles(file);
            }else{
                file.delete();
            }
        }
    }

    /**
     * 删除文件夹里面的全部文件
     * @param root 文件夹
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    private static boolean fileIsEquals(File file,float size){
        String fileSize="";
         fileSize = formatFileSize(file.length());
        float fSize = new Float(fileSize);
        float qSize=size-fSize;
        return qSize<1;
    }
    /**

     * 将文件大小转换成字节

     */

    public static String formatFileSize( long fileS){
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) ;
        } else  {
            fileSizeString = df.format((double) fileS / 1024) ;
        }
        return fileSizeString;

    }
    public static void dowFile(String url,final String path,final Handler handler){
        final String fileName = getFileName(url);
        LogUtils.i("接口测试: dowFile : fileName"+fileName);
        OkHttpUtils.get()
                .url(url)
                .build()
                .connTimeOut(10*1000)
                .readTimeOut(30*1000)
                .execute(new FileCallBack(path, fileName) {
                    @Override
                    public void inProgress(float progress) {
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtils.e("接口测试 dowFile下载失败！\n" + e.toString());
                        Message msg = new Message();
                        msg.what = MainActivity.MSG_WHAT_DOW_FILE_NO;
                        msg.obj = "";
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(File response) {
                        LogUtils.d("接口测试 dowFile下载完成！" +path+File.separator+fileName);
                        Message msg = new Message();
                        msg.what = MainActivity.MSG_WHAT_DOW_FILE_OK;
                        msg.obj = path+File.separator+fileName;
                        handler.sendMessage(msg);
                    }
                });
    }
    public static void dowAdvFile(ArrayList<AdvTxt> dataList, final Handler handler, final String caChePath){
        for(AdvTxt advTxt:dataList){
            final String advName =  advTxt.getUrl().substring(advTxt.getUrl().lastIndexOf("/") + 1, advTxt.getUrl().length());
            final int ID = advTxt.getId();
            final String path = caChePath+ File.separator +advName;
            File file = new File(path);
            if(file.exists()&&fileIsEquals(file,advTxt.getSize())){
                Message msg = new Message();
                msg.what = MainActivity.MSG_WHAT_DOW_ADV_OK;
                msg.arg1 = ID;
                msg.obj = path;
                handler.sendMessage(msg);
            }else{
                OkHttpUtils.get()
                        .url(advTxt.getUrl())
                        .build()
                        .connTimeOut(10*1000)
                        .readTimeOut(30*1000)
                        .execute(new FileCallBack(caChePath, advName) {
                            @Override
                            public void inProgress(float progress) {
                            }

                            @Override
                            public void onError(Request request, Exception e) {
                                LogUtils.e("接口测试 dowAdvFile下载失败！\n" + e.toString());
                                Message msg = new Message();
                                msg.what = MainActivity.MSG_WHAT_DOW_ADV_NO;
                                msg.arg1 = ID;
                                msg.obj = "";
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(File response) {
                                LogUtils.d("接口测试 dowAdvFile下载完成！" +path);
                                Message msg = new Message();
                                msg.what = MainActivity.MSG_WHAT_DOW_ADV_OK;
                                msg.arg1 = ID;
                                msg.obj = path;
                                handler.sendMessage(msg);
                            }
                        });
            }

        }

    }
    //静默安装

    /**
     * 板特有的升级方式
     * @param path
     * @param packageName
     */
    public static void installSlient(String path, String packageName, Activity activity) {

        Intent intent = new Intent("com.hilan.updater");
        intent.putExtra("from",packageName);
        intent.putExtra("path",path);
        intent.putExtra("calss_from","com.coresun.powerbank.ui.splash.SplashActivity");
        activity.sendBroadcast(intent);
//        String cmd = "pm install -r "+path;
//        Process process = null;
//        DataOutputStream os = null;
//        BufferedReader successResult = null;
//        BufferedReader errorResult = null;
//        StringBuilder successMsg = null;
//        StringBuilder errorMsg = null;
//        try {
//            //静默安装需要root权限
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.write(cmd.getBytes());
//            os.writeBytes("\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            //执行命令
//            process.waitFor();
//            //获取返回结果
//            successMsg = new StringBuilder();
//            errorMsg = new StringBuilder();
//            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String s;
//            while ((s = successResult.readLine()) != null) {
//                successMsg.append(s);
//            }
//            while ((s = errorResult.readLine()) != null) {
//                errorMsg.append(s);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (process != null) {
//                    process.destroy();
//                }
//                if (successResult != null) {
//                    successResult.close();
//                }
//                if (errorResult != null) {
//                    errorResult.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        //显示结果
////        tvTest.setText("成功消息：" + successMsg.toString() + "\n" + "错误消息: " + errorMsg.toString());


    }
    public static String getIp(String ipString) {
        String ip = "";
        int start = ipString.indexOf("{");
        int end = ipString.indexOf("}");
        String json = ipString.substring(start, end+1);
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                ip = jsonObject.optString("cip");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

}

