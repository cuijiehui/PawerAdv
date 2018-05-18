package com.coresun.powerbank.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.coresun.powerbank.R;
import com.coresun.powerbank.bean.AdvBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Android on 2017/8/25.
 */

public class AdvFragment extends Fragment {

    private static String URL = "url";
    @Bind(R.id.adv_image)
    ImageView advImage;


    public static AdvFragment newInstance(String imgUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, imgUrl);
        AdvFragment fragment = new AdvFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AdvFragment newInstance(AdvBean advBean) {
        AdvFragment fragment = new AdvFragment();
        Bundle bundle = new Bundle();
            bundle.putString(URL, advBean.getUrl());
            fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_fragment, container, false);
        ButterKnife.bind(this, view);
            String url = getArguments().getString(URL);
            Glide.with(getActivity())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.adv_test1)
                    .crossFade()
                    .into(advImage);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
