package com.niles.pandian_login.http.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Niles
 * Date 2018/10/12 16:13
 * Email niulinguo@163.com
 */
@SuppressWarnings("NullableProblems")
public abstract class StringCallback implements Callback<String> {

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        t.printStackTrace();
    }
}
