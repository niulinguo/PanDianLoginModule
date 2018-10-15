package com.niles.pandian_login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.niles.pandian_login.R;
import com.niles.pandian_login.util.BitmapUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 加载页面
 * Created by:Liuhuacheng
 * Created time:15-5-25
 */
@Route(path = "/activity/splash")
public class SplashActivity extends BaseActivity {
    private Context context;
    private Timer mTimer;
    private Bitmap bitmap;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);
        init();
    }

    private void init() {
        context = this;
        try {
            ImageView iv_advert = findViewById(R.id.iv_advert);
            bitmap = BitmapUtils.readBitMap(getApplicationContext(), R.drawable.splash_bg);
            if (bitmap != null) {
                iv_advert.setImageBitmap(bitmap);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0);
        }
        startTimer();
    }

    private void startTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        mTimer = null;
        context = null;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
