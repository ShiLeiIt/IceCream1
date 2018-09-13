package com.samrt.qiushi.icecream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.samrt.qiushi.icecream.R;
import com.samrt.qiushi.icecream.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shilei on 2018/9/10
 * 广告Banner图轮播
 */
public class SplashActivity extends AppCompatActivity implements OnBannerListener {
    @Bind(R.id.banner)
    Banner banner;
    public static List<?> images = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        String[] urls = getResources().getStringArray(R.array.url);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
    }

    private void initData() {
        //默认是CIRCLE_INDICATOR
        banner.setImages(images).setDelayTime(2000).
                setImageLoader(new GlideImageLoader())
                .start();
        banner.updateBannerStyle(BannerConfig.NOT_INDICATOR);//不显示指示器与标题
        banner.setOnBannerListener(this);
    }

    @Override
    public void OnBannerClick(int position) {
        //跳转到购买页面
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        Toast.makeText(this, "第" + position + "页", Toast.LENGTH_SHORT).show();
    }
}
