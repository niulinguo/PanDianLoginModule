package com.niles.pandian_login.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

import com.niles.pandian_login.R;

/**
 * Dialog工具类
 * Created by:Liuhuacheng
 * Created time:15-4-17
 */
public class DialogUtil {
    private static Bitmap bitmap = null;

    /**
     * Create Dialog with Layout and theme
     *
     * @param context  Context
     * @param layoutId Layout ID
     * @return Dialog
     */
    public static Dialog createDialog(Context context, int layoutId, int themeId) {
        Dialog ret;
        ret = new Dialog(context, themeId);
        ret.setContentView(layoutId);
        ret.setCancelable(true);
        ret.setTitle("title");
        return ret;
    }


    public static void setDialogParams(Activity context, Dialog dialog, int id) {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int margin = (int) context.getResources().getDimension(id);
        int height = (int) context.getResources().getDimension(R.dimen.confirm_dialog_height);
        lp.width = (int) (display.getWidth()) - margin; //设置宽度
        lp.height = height;
        dialog.getWindow().setAttributes(lp);
    }

    public static void setDialogWidthParams(Activity context, Dialog dialog, int id, int height) {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int margin = (int) context.getResources().getDimension(id);
        lp.width = (int) (display.getWidth()) - margin; //设置宽度
        if (height != -1) {
            lp.height = height;
        }
        dialog.getWindow().setAttributes(lp);
    }

    public static void setDialogHeight(Activity context, Dialog dialog, int height) {
        WindowManager windowManager = context.getWindowManager();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.height = height;
        dialog.getWindow().setAttributes(lp);
    }

}
