package com.coresun.powerbank.ui.main.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coresun.powerbank.R;
import com.coresun.powerbank.bean.AdvBean;
import com.coresun.powerbank.util.LogUtils;

import java.io.File;

public class ImageViewFragment extends AdvBaseFragment {

    ImageView imageView ;
    AdvBean mAdvBean;
    public ImageViewFragment() {
    }
    @SuppressLint("ValidFragment")
    public ImageViewFragment(AdvBean advBean) {
        this.mAdvBean = advBean;
        setTYPE(advBean.getType());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_view_fragment,null);
        imageView = (ImageView)view.findViewById(R.id.image_view);
        setImageView();
        return view;
    }

    private void setImageView(){
        File file = new File(mAdvBean.getUrl());
        LogUtils.i("接口测试：");
        Glide.with(getContext())
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.adv_test1)
                .crossFade()
                .into(imageView);
    }
}

