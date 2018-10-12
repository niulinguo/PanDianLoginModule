package com.niles.pandian_login;

import android.text.TextUtils;

import com.niles.http.HttpConfig;
import com.niles.http.HttpManager;
import com.niles.pandian_base.PanDianAppManager;
import com.niles.pandian_login.http.LoginApi;
import com.niles.pandian_login.http.callback.TestBaseUrlCallback;

/**
 * Created by Niles
 * Date 2018/10/12 16:04
 * Email niulinguo@163.com
 */
public class PanDianLoginManager {

    public static void setBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("BaseUrl Is Null");
        }
        HttpManager httpManager = PanDianAppManager.getInstance().getHttpManager();
        HttpConfig httpConfig = httpManager.getHttpConfig();
        if (!baseUrl.equals(httpConfig.getBaseUrl())) {
            httpConfig = httpConfig.newBuilder().setBaseUrl(baseUrl).build();
            httpManager.setHttpConfig(httpConfig);
        }
    }

    public static void testBaseUrl() {
        LoginApi.getInstance().testBaseUrl().enqueue(new TestBaseUrlCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
