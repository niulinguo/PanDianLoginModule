package com.niles.pandian_login.activity;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.niles.appbase.AppManager;
import com.niles.http.HttpManager;
import com.niles.pandian_login.R;
import com.niles.pandian_login.http.LoginApi;
import com.niles.pandian_login.http.callback.StringCallback;
import com.niles.pandian_login.util.MyPreferences;
import com.niles.pandian_login.util.Utils;

import retrofit2.Call;
import retrofit2.Response;


/**
 * 注册FTP
 * Created by:Liuhuacheng
 * Created time:15-10-27
 */
public class RegisterActivity extends BaseAppCompatActivity {
    private EditText http_server, http_port;

    public static String getHttpPre(String ip, String port) {
        if (TextUtils.isEmpty(port)) {
            return ip;
        }
        return ip + ":" + port;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        init();
        initView();
    }

    @Override
    protected void init() {
        setTitle("注册");
    }

    public void initView() {
        EditText register_server = findViewById(R.id.register_server);
        EditText register_port = findViewById(R.id.register_port);
        EditText register_name = findViewById(R.id.register_name);
        EditText register_password = findViewById(R.id.register_password);

        String server = MyPreferences.getStringData(MyPreferences.RE_SERVER);
        String port = MyPreferences.getStringData(MyPreferences.RE_PORT);
        String name = MyPreferences.getStringData(MyPreferences.RE_NAME);
        String pwd = MyPreferences.getStringData(MyPreferences.RE_PWD);

        register_server.setText(server);
        register_port.setText(port);
        register_name.setText(name);
        register_password.setText(pwd);
        Button save = findViewById(R.id.save);
        save.setOnClickListener(this);
        Button test = findViewById(R.id.test);
        test.setOnClickListener(this);

        http_server = findViewById(R.id.http_server);
        http_port = findViewById(R.id.http_port);

        http_server.setText(MyPreferences.getStringData(MyPreferences.HTTP_SERVER));
        http_port.setText(MyPreferences.getStringData(MyPreferences.HTTP_PORT));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.test) {
            getFtpInfo();
        } else if (id == R.id.save) {
            getFtpInfo();
        }
    }

    @Keep
    public void login(View view) {
        finish();
    }

    //Http服务器 获取FTP信息
    public void getFtpInfo() {
        if (!Utils.isNetwork(mContext)) {
            Utils.showToastShort(mContext, "网络连接异常,请联网后重试");
            return;
        }

        // ip地址
        final String http = http_server.getText().toString();
        // port端口号
        final String port = http_port.getText().toString();

        if (TextUtils.isEmpty(http)) {
            Utils.showToastShort(mContext, "请先输入服务器地址");
            return;
        }

        // 网络请求
        HttpManager httpManager = AppManager.getInstance().getHttpManager();
        httpManager.setHttpConfig(httpManager.getHttpConfig().newBuilder().setBaseUrl(getHttpPre(http, port)).build());
        LoginApi.getInstance().testBaseUrl().enqueue(new StringCallback() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);

                if (response.code() == 200) {
                    MyPreferences.setStringData(MyPreferences.HTTP_SERVER, http);
                    MyPreferences.setStringData(MyPreferences.HTTP_PORT, port);

                    finishTest();
                } else {
                    ToastUtils.showShort("此服务器不可用");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);

                ToastUtils.showShort("此服务器不可用");
            }
        });
    }

    private void finishTest() {
        ToastUtils.showShort("保存成功");
        hideLoading();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            hideLoading();
        }
    }
}
