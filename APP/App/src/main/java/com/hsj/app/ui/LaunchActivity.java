package com.hsj.app.ui;

import android.os.Bundle;
import android.view.View;

import com.hsj.base.ui.BaseActivity;

/**
 * @Author:HSJ
 * @E-mail:mr.ajun@foxmail.com
 * @Date:2017/5/27/15:26
 * @Class:AppLaunchActivity
 * @Description:启动页
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        //进入欢迎页或者广告页
        //startActivity(new Intent(this, WelActivity.class));
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}