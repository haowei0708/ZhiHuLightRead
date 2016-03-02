package com.example.kevin.zhihulightread.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.bean.StartImageBean;
import com.example.kevin.zhihulightread.protocol.SplashProtocol;
import com.example.kevin.zhihulightread.utils.CacheUtil;
import com.example.kevin.zhihulightread.utils.LogUtils;
import com.example.kevin.zhihulightread.utils.UIUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


public class SplashAcitivity extends Activity {

    private ImageView ivStart;
    String url = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_acitivity);

        ivStart = (ImageView) findViewById(R.id.iv_start);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String jsonString = CacheUtil.getCache(SplashAcitivity.this, url);

        if (!TextUtils.isEmpty(jsonString)){
            parseData(jsonString);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {


        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonString = responseInfo.result;

                //设置缓存
                CacheUtil.setCache(SplashAcitivity.this,url,jsonString);

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
        String imgUrl = startImageBean.getImg();

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
