package com.coresun.powerbank.ui.main.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/3/28/028.
 */

public class CustomViewPager extends NoPreloadViewPager {

    private boolean isCanScroll = false;
    Timer timer = new Timer();
    MyTimerTask myTimerTask;

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            setCurrentItem(getCurrentItem()+1);

        }
    }

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);

    }
   public void timerChangePager(long time){
        if(myTimerTask!=null){
            myTimerTask.cancel();
        }
        myTimerTask=new MyTimerTask();
       timer.schedule(myTimerTask,time);
    }
    public void stopChangePager(){
        timer.cancel();
    }

}