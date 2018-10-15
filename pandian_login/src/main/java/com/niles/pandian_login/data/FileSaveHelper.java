package com.niles.pandian_login.data;

import android.content.Context;

import com.niles.pandian_base.model.UserModel;
import com.niles.pandian_login.util.Utils;
import com.orhanobut.hawk.Hawk;

/**
 * csv文件保存到数据中
 * Created by Liuhuacheng.
 * Created time 16/10/30.
 */

public class FileSaveHelper {
    private static final String TAG = "FileSaveHelper";

    private Context context;

    public FileSaveHelper(Context context) {
        this.context = context;
    }

    public void saveUser(UserModel userModel) {
        if (userModel != null) {
            Hawk.put("UserInfo", userModel);
            Utils.showLog(TAG, "users save success");
        } else {
            Utils.showLog(TAG, "users save fail");
        }
    }

}
