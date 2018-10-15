package com.niles.pandianloginmodule;

import android.app.Application;

import com.niles.appbase.AppManager;

/**
 * Created by Niles
 * Date 2018/10/12 15:58
 * Email niulinguo@163.com
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.init(this, BuildConfig.DEBUG);
    }
}
