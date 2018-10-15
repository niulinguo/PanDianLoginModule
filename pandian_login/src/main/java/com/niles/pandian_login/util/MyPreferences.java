package com.niles.pandian_login.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.securepreferences.SecurePreferences;

/**
 * Created by Negro
 * Date 2018/5/29
 * Email niulinguo@163.com
 */
public final class MyPreferences {

    public static final String USER_PASSWORD = "password";
    public static final String TOKEN = "token";
    public static final String USER_NAME = "userName";//用户姓名
    public static final String USER_UNIT = "userUnit";//用户机构
    public static final String USER_NO = "userNO";//用户编码
    public static final String USER_DATE = "time";//盘点日期
    public static final String SELECT_UNIT = "unit";//组织机构名称
    public static final String SELECT_DEPT = "dept";//部门名称
    public static final String UNIT_NO = "unitNo";//组织机构no  当为全部的时候值为-1
    public static final String DEPT_NO = "deptNo";//部门no 当为全部的时候值为-1
    public static final String RE_SERVER = "re_server";
    public static final String RE_PORT = "re_port";
    public static final String RE_NAME = "re_name";
    public static final String RE_PWD = "re_pwd";
    public static final String HTTP_SERVER = "http_server";
    public static final String HTTP_PORT = "http_port";
    public static final String STATE = "state";
    public static final String MAIN_DATA = "main";
    private static final String AUTO_USER = "auto_login_flag";//自动登录标示
    private static SharedPreferences sp;

    public static void init(Application application) {
        sp = new SecurePreferences(application, "你看啥", "asset.xml");
    }

    //清空数据
    public static void clearStringData(String key) {
        sp.edit().remove(key).apply();
    }

    //设置用户数据
    public static void setStringData(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            sp.edit().putString(key, value).apply();
        }
    }

    public static String getStringData(String key) {
        return sp.getString(key, "").trim();
    }

    //设置用户数据
    public static void setIntData(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static int getIntData(String key) {
        return sp.getInt(key, 0);
    }

    /*
     * 获取自动登录标识
     * return state 0不自动登录，1自动登录，-1系统默认（默认为记住登录的状态）
     * */
    public static boolean getUserAuto() {
        return sp.getBoolean(AUTO_USER, false);
    }

    /*
     * 设置自动登录
     * params value =true自动登录，false不自动登录
     * */
    public static void setUserAuto(boolean value) {
        sp.edit().putBoolean(AUTO_USER, value).apply();
    }

    public static String getUnit() {
        return sp.getString(SELECT_UNIT, "全部").trim();
    }

    public static void setUnit(String data) {
        sp.edit().putString(SELECT_UNIT, data).apply();
    }

    public static String getDept() {
        return sp.getString(SELECT_DEPT, "全部").trim();
    }

    public static void setDept(String data) {
        sp.edit().putString(SELECT_DEPT, data).apply();
    }

    public static String getUnitNo() {
        return sp.getString(UNIT_NO, "-1").trim();
    }

    public static void setUnitNo(String data) {
        sp.edit().putString(UNIT_NO, data).apply();
    }

    public static String getDeptNo() {
        return sp.getString(DEPT_NO, "-1").trim();
    }

    public static void setDeptNo(String data) {
        sp.edit().putString(DEPT_NO, data).apply();
    }
}
