package com.niles.pandian_login.http.callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Niles
 * Date 2018/10/12 16:15
 * Email niulinguo@163.com
 */
public abstract class TestBaseUrlCallback extends StringCallback {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.code() == 200) {
            onSuccess();
        } else {
            onFailure();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        onFailure();
    }

    public abstract void onSuccess();

    public abstract void onFailure();
}
