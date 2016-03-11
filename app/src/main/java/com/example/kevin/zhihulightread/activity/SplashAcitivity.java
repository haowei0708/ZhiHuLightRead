package com.example.kevin.zhihulightread.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kevin.zhihulightread.R;
import com.example.kevin.zhihulightread.global.Constants;
import com.example.kevin.zhihulightread.model.StartImageBean;
import com.example.kevin.zhihulightread.utils.ACache;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


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


        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(Constants.TAG, "onError :" + e.getMessage());
            }

            @Override
            public void onResponse(String response) {

                //缓存文件
                mACache.put(url, response, ACache.TIME_HOUR);

                parseData(response);
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
