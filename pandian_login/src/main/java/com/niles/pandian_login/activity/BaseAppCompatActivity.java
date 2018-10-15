package com.niles.pandian_login.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.niles.pandian_login.R;
import com.niles.pandian_login.util.DialogHelper;
import com.niles.pandian_login.util.Utils;

/**
 * 所有Activity需集成的基类
 * Created by:Liuhuacheng
 * Created time:14-9-6
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements View.OnClickListener {
    public boolean isBack = false;
    public int version;
    public Context mContext;
    private long mExitTime = 0;
    private Toolbar toolbar;
    private boolean displayHome = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void hideToolbar() {
        initToolbar();
        if (toolbar != null) {
            findViewById(R.id.toolbar_parent).setVisibility(View.GONE);
        }
    }

    public void showToolbar() {
        if (toolbar == null) {
            initToolbar();
        }
        findViewById(R.id.toolbar_parent).setVisibility(View.VISIBLE);
    }

    protected void setDisplayHome(boolean displayHome) {
        this.displayHome = displayHome;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
        }
    }

    public void setTitle(CharSequence title) {
        if (toolbar == null) {
            initToolbar();
        }
        if (toolbar != null) {
            //noinspection ConstantConditions
            getSupportActionBar().setTitle("");
            TextView textView = toolbar.findViewById(R.id.toolbar_title);
            if (textView != null)
                textView.setText(title);
        }
        setDisplayHome(displayHome);
    }

    public void showLoading() {
        DialogHelper.getInstance(mContext).show(null);
    }

    public void hideLoading() {
        DialogHelper.getInstance(mContext).hide();
    }

    public void showLoading(String content) {
        DialogHelper.getInstance(mContext).show(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化View控件
     */
    protected abstract void init();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isBack && (System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                logout();
                finish();
                Utils.showLog("onKeyDown finish();");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void logout() {
//        LoginApi.getInstance().logout().enqueue(new LogoutCallback());
//        RestClient.builder()
//                .url("pandian/login/logout")
//                .params("username", MyPreferences.getStringData(MyPreferences.USER_NAME))
//                .params("deviceId", Installation.id(this))
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        onLogoutResult(JSON.parseObject(response));
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//                        ToastUtils.showShort("网络异常");
//                    }
//                })
//                .error(new IError() {
//                    @Override
//                    public void onError(int code, String msg) {
//                        ToastUtils.showShort(msg + "(" + code + ")");
//                    }
//                })
//                .build()
//                .post();
    }
}
