package com.myproject.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.myproject.R;
import com.myproject.ui.AtyMain;

/**
 * Created by Administrator on 2015/8/25.
 * Used for
 */
public class AtyLanuncher extends Activity {
    private RelativeLayout mLaunchLayout;
    private Animation mFadeIn;
    private Animation mFadeInScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_launcher);
        mLaunchLayout = (RelativeLayout) findViewById(R.id.launch);
        init();
        setListener();
    }

    private void init() {
        initAnim();
        mLaunchLayout.startAnimation(mFadeIn);
    }

    /**
     * 首页动画初始化
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(AtyLanuncher.this, R.anim.welcome_fade_in);
        mFadeIn.setDuration(500);
        //是因为有动画链的原因，假定你有一个移动的动画紧跟一个淡出的动画，
        //如果你不把移动的动画的setFillAfter置为true，那么移动动画结束后，
        //View会回到原来的位置淡出，如果setFillAfter置为true，
        //就会在移动动画结束的位置淡出
        mFadeIn.setFillAfter(true);

        mFadeInScale = AnimationUtils.loadAnimation(AtyLanuncher.this, R.anim.welcome_fade_in_scale);
        mFadeInScale.setDuration(3000);
        mFadeInScale.setFillAfter(true);
    }

    private void setListener() {
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLaunchLayout.startAnimation(mFadeInScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(AtyLanuncher.this, AtyMain.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
