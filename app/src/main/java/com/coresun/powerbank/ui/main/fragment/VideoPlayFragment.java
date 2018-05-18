package com.coresun.powerbank.ui.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coresun.powerbank.R;
import com.coresun.powerbank.bean.AdvBean;
import com.coresun.powerbank.ui.main.EmptyControlVideo;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;

/**
 * Created by Administrator on 2018/3/28/028.
 */

public class VideoPlayFragment extends AdvBaseFragment {

    EmptyControlVideo empty_control_video;
   private String URL="";
    private VideoAllCallBack mCallBack;
    AdvBean mAdvBean;

    public VideoPlayFragment() {
    }

    @SuppressLint("ValidFragment")
    public VideoPlayFragment(AdvBean advBean, VideoAllCallBack mCallBack) {
        this.mAdvBean = advBean;
        this.mCallBack = mCallBack;
        setTYPE(advBean.getType());
        Log.d("广告测试","setVideoPlay="+mAdvBean.toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("广告测试","onCreateView");
        View view = inflater.inflate(R.layout.video_play_fragment,null);
        empty_control_video=(EmptyControlVideo) view.findViewById(R.id.empty_control_video);
        setVideoPlay();
        return view;
    }
    private void setVideoPlay(){
        Log.d("广告测试","setVideoPlay="+mAdvBean.getUrl());
        empty_control_video.setUp(mAdvBean.getUrl(),true,null);
        empty_control_video.setVideoAllCallBack(mCallBack);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("广告测试","广告视频播开始播放");
        if(empty_control_video!=null){
            empty_control_video.startPlayLogic();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("广告测试","广告视频播开始播放");
        if(empty_control_video!=null){
            empty_control_video.onVideoPause();
        }
    }
}
