package com.niles.pandian_login.http;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Niles
 * Date 2018/10/12 16:03
 * Email niulinguo@163.com
 */
public interface LoginService {

    @GET("/")
    Call<String> testBaseUrl();

    @Headers("Content-Type: application/json")
    @POST("/pandian/login/logout")
    Call<String> logout(@Body HashMap<String, Object> params);
}
