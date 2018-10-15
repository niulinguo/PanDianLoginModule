package com.niles.pandian_login.http;

import com.niles.appbase.AppManager;
import com.niles.appbase.utils.Installation;
import com.niles.http.HttpManager;

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
        mHttpManager = AppManager.getInstance().getHttpManager();
    }

    public static LoginApi getInstance() {
        return sInstance;
    }

    public Call<String> testBaseUrl() {
        return mHttpManager.getService(LoginService.class).testBaseUrl();
    }

    public Call<String> logout(String username) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("deviceId", Installation.id(AppManager.getInstance().app()));
        return mHttpManager.getService(LoginService.class).logout(params);
    }

    public Call<String> login(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("deviceId", Installation.id(AppManager.getInstance().app()));
        return mHttpManager.getService(LoginService.class).login(params);
    }
}
