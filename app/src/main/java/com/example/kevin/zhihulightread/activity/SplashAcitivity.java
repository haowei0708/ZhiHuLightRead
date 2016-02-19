package com.example.kevin.zhihulightread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kevin.zhihulightread.R;
import com.lidroid.xutils.BitmapUtils;

public class SplashAcitivity extends AppCompatActivity {

    private ImageView ivStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitivity);

        ivStart = (ImageView) findViewById(R.id.iv_start);
        initView();
    }

    private void initView() {
        String url = "https://pic1.zhimg.com/86417480c1af008ddd456eb73b09ca4c.jpg";
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(ivStart,url);

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
