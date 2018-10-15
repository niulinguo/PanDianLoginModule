package com.niles.pandian_login.interceptor;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.niles.appbase.AppManager;
import com.niles.http.HttpManager;
import com.niles.pandian_login.activity.RegisterActivity;
import com.niles.pandian_login.util.MyPreferences;

/**
 * Created by Niles
 * Date 2018/10/15 10:48
 * Email niulinguo@163.com
 */
@Interceptor(priority = 100, name = "登录模块初始化拦截器")
public class LoginModuleInitInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

    }

    @Override
    public void init(Context context) {
        Application app = (Application) context.getApplicationContext();
        MyPreferences.init(app);

        String server = MyPreferences.getStringData(MyPreferences.HTTP_SERVER);
        String port = MyPreferences.getStringData(MyPreferences.HTTP_PORT);
        if (!TextUtils.isEmpty(server)) {
            HttpManager httpManager = AppManager.getInstance().getHttpManager();
            httpManager.setHttpConfig(httpManager.getHttpConfig().newBuilder().setBaseUrl(RegisterActivity.getHttpPre(server, port)).build());
        }
    }
}
