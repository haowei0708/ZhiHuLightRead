package com.example.kevin.zhihulightread.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.model.StartImageBean;
import com.example.kevin.zhihulightread.utils.ACache;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SplashAcitivity extends Activity {


    @Bind(R.id.iv_start)
    ImageView mIvStart;

    private boolean isCache = false;
    String url = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
    private ACache mACache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_acitivity);
        ButterKnife.bind(this);


        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        mACache = ACache.get(this);

        //如果没有网络
        String jsonString = mACache.getAsString(url);
        if (jsonString != null) {
            parseData(jsonString);
        } else {
            //如果有网络
            getDataFromServer();
        }



    }

    private void getDataFromServer() {


        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                //缓存文件
                mACache.put(url, jsonString, ACache.TIME_HOUR);

                parseData(jsonString);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("error:" + e);
            }
        });
    }

    /**
     * 解析数据
     *
     * @param jsonString json字符串
     */
    private void parseData(String jsonString) {
        Gson gson = new Gson();
        StartImageBean startImageBean = gson.fromJson(jsonString, StartImageBean.class);
        String imgUrl = startImageBean.getImg();

        initView(imgUrl);
    }

    private void initView(String imgUrl) {

        //加载图片
        Picasso.with(this).load(imgUrl).into(mIvStart);

        initAnimation();
    }

    private void initAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        //对动画进行监听，结束后进入主界面
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashAcitivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mIvStart.startAnimation(scaleAnimation);

    }

}
