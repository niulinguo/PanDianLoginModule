package com.niles.pandian_login.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kejiwen.securitykeyboardlibrary.SecKeyboardView;
import com.niles.pandian_base.model.UserModel;
import com.niles.pandian_login.R;
import com.niles.pandian_login.data.FileSaveHelper;
import com.niles.pandian_login.http.LoginApi;
import com.niles.pandian_login.http.callback.StringCallback;
import com.niles.pandian_login.util.MyPreferences;
import com.niles.pandian_login.util.Utils;

import java.lang.reflect.Method;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 登录
 */
public class LoginActivity extends BaseAppCompatActivity {
    private static final int WRITE_EXTERNAL_STORAGE = 0;
    private Context context;
    private String userName = ""; // 用户名
    private String pwd = ""; // 密码
    private EditText userNameEdit, pwdEdit;
    private CheckBox remember_pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        init();
        initKeyboard();
    }

    private void initKeyboard() {
        KeyboardView mKeyboardView;
        mKeyboardView = findViewById(R.id.keyboard_view);
        new SecKeyboardView(this, pwdEdit, mKeyboardView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(pwdEdit, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {
        context = this;
        setTitle("登录");
        setDisplayHome(false);
        userNameEdit = findViewById(R.id.userName);
        pwdEdit = findViewById(R.id.pwd);
        userName = MyPreferences.getStringData(MyPreferences.USER_NO);
        userNameEdit.setText(userName);
        remember_pwd = findViewById(R.id.remember_pwd);

        boolean b = MyPreferences.getUserAuto();
        remember_pwd.setChecked(b);
        if (b) {
            pwd = MyPreferences.getStringData(MyPreferences.USER_PASSWORD);
            pwdEdit.setText(pwd);
        } else {
            pwdEdit.setText("");
        }

        cameraTask();
    }

    @Keep
    public void login(View view) {
        userName = userNameEdit.getText().toString().trim();
        pwd = pwdEdit.getText().toString().trim();
        String prompt = "";

        final String http = MyPreferences.getStringData(MyPreferences.HTTP_SERVER);

        if (TextUtils.isEmpty(http)) {
            prompt = getString(R.string.state_rigster);
        } else if (TextUtils.isEmpty(userName)) {
            prompt = getString(R.string.register_phone);
        } else if (TextUtils.isEmpty(pwd)) {
            prompt = getString(R.string.login_pwd_empty_error);
        } else {
            // 登录
            loginFinish();
        }

        if (!TextUtils.isEmpty(prompt)) {
            Utils.showMessage(context, prompt);
        }
    }

    @Keep
    public void register(View view) {
        final Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.topBar_left_layout) {
            System.exit(0);
        }
    }


    // 登录完成数据处理
    public void loginFinish() {
        // 修改为http请求
        LoginApi.getInstance().login(userName, pwd)
                .enqueue(new StringCallback() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        super.onResponse(call, response);
                        onLoginResult(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        super.onFailure(call, t);
                        ToastUtils.showLong(t.getMessage());
                    }
                });
    }

    private void onLoginResult(String result) {
        final JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject.getIntValue("code") == 200) {
            final JSONObject object = jsonObject.getJSONObject("result");
            final String token = object.getString("token");
            final JSONObject userObj = object.getJSONObject("userInfo");
            final String userName = userObj.getString("userName");
            final String userCode = userObj.getString("userCode");
            final String orgCode = userObj.getString("orgCode");
            final String deptCode = userObj.getString("deptCode");
            final String orgCodeRange = userObj.getString("orgCodeRange");
            final String deptCodeRange = userObj.getString("deptCodeRange");
            UserModel userModel = new UserModel.Builder()
                    .setUsername(userName)
                    .setPassword(pwd)
                    .setUserNo(userCode)
                    .setUserUnit(orgCode)
                    .setUserDept(deptCode)
                    .setUnitRange(orgCodeRange)
                    .setDeptRange(deptCodeRange)
                    .build();
            MyPreferences.setStringData(MyPreferences.TOKEN, token);
            MyPreferences.setStringData(MyPreferences.USER_NAME, userModel.getUsername());
            MyPreferences.setStringData(MyPreferences.USER_UNIT, userModel.getUserUnit());
            MyPreferences.setStringData(MyPreferences.USER_NO, userModel.getUserNo());
            MyPreferences.setStringData(MyPreferences.USER_PASSWORD, userModel.getPassword());
            MyPreferences.setUserAuto(remember_pwd.isChecked());
            saveUserData(userModel);
            loginSuccess();
        } else {
            ToastUtils.showShort(jsonObject.getString("message"));
        }
    }

    public void loginSuccess() {
        ARouter.getInstance().build("/activity/main").navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {
                super.onLost(postcard);
                ToastUtils.showLong("没有找到 /activity/main");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtils.finishAllActivities();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    public void cameraTask() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_file), WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void saveUserData(UserModel userModel) {
        final FileSaveHelper helper = new FileSaveHelper(mContext);
        helper.saveUser(userModel);
    }
}