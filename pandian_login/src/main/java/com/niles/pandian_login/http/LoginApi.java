package com.niles.pandian_login.http;

import com.niles.http.HttpManager;
import com.niles.pandian_base.PanDianAppManager;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by Niles
 * Date 2018/10/12 16:08
 * Email niulinguo@163.com
 */
public class LoginApi {

    private static final LoginApi sInstance = new LoginApi();
    private final HttpManager mHttpManager;

    private LoginApi() {
        mHttpManager = PanDianAppManager.getInstance().getHttpManager();
    }

    public static LoginApi getInstance() {
        return sInstance;
    }

    public Call<String> testBaseUrl() {
        return mHttpManager.getService(LoginService.class).testBaseUrl();
    }

    public Call<String> logout() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "");
        params.put("deviceId", "");
        return mHttpManager.getService(LoginService.class).logout(params);
    }
}
