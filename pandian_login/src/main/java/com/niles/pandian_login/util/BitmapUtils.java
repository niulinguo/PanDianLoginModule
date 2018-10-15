package com.niles.pandian_login.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by:Liuhuacheng
 * Created time:15-12-7
 */
public class BitmapUtils {

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
