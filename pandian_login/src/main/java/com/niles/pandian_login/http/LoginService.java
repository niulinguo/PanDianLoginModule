package com.niles.pandian_login.http;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Niles
 * Date 2018/10/12 16:03
 * Email niulinguo@163.com
 */
public interface LoginService {

    @GET("/")
    Call<String> testBaseUrl();

    @FormUrlEncoded
    @POST("/pandian/login/logout")
    Call<String> logout(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("/pandian/login/signIn")
    Call<String> login(@FieldMap HashMap<String, String> params);
}
