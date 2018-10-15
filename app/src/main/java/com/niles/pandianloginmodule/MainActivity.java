package com.niles.pandianloginmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by Niles
 * Date 2018/10/15 10:43
 * Email niulinguo@163.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().build("/pandian_login/activity/splash").navigation();
        finish();
    }
}
