package com.example.kevin.zhihulightread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.bean.StartImageBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class SplashAcitivity extends AppCompatActivity {

    private ImageView ivStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivity);

        ivStart = (ImageView) findViewById(R.id.iv_start);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        String url = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;
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
     * @param jsonString json字符串
     */
    private void parseData(String jsonString) {
        Gson gson = new Gson();
        StartImageBean startImageBean = gson.fromJson(jsonString, StartImageBean.class);
        String imgUrl = startImageBean.img;

        initView(imgUrl);
    }

    private void initView(String imgUrl) {

        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(ivStart,imgUrl);

        initAnimation();
    }

    private void initAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.1f,1.0f,1.1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        //对动画进行监听，结束后进入主界面
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashAcitivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivStart.startAnimation(scaleAnimation);

    }

}
