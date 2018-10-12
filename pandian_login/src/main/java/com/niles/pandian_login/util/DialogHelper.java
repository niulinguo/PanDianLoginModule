package com.niles.pandian_login.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Loading Dialog
 * Created by Liuhuacheng.
 * Created time 16/5/9.
 */

public class DialogHelper {
    private static Context mContext;
    private static DialogHelper instance;
    private ProgressDialog progressDialog;

    public static DialogHelper getInstance(Context context) {
        if (instance == null || !mContext.equals(context)) {
            synchronized (DialogHelper.class) {
                if (instance == null || !mContext.equals(context)) {
                    instance = new DialogHelper();
                }
            }
        }
        mContext = context;
        return instance;
    }

    public void show(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "数据加载中...";
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            setContent(content);
        } else
            progressDialog = ProgressDialog.show(mContext, null, content, true, true);
    }

    public void hide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setContent(String content) {
        if (progressDialog != null) {
            progressDialog.setMessage(content);
        }
    }

    public void setCancel(boolean b) {
        if (progressDialog != null) {
            progressDialog.setCancelable(b);
        }
    }

}
