package com.niles.pandian_login.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.niles.pandian_base.model.UserModel;
import com.niles.pandian_base.service.LoginModuleService;

/**
 * Created by Niles
 * Date 2018/10/15 09:27
 * Email niulinguo@163.com
 */
@Route(path = "/pandian_login/service/api", name = "pandian login service")
public class LoginModuleServiceImpl implements LoginModuleService {
    @Override
    public UserModel getLoginInfo() {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}
