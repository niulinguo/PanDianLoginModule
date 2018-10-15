package com.niles.pandian_login.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niles.pandian_login.R;
import com.niles.pandian_login.util.DialogHelper;
import com.niles.pandian_login.util.DialogUtil;
import com.niles.pandian_login.util.Utils;

/**
 * 所有Activity需集成的基类
 * Created by:Liuhuacheng
 * Created time:14-9-6
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public boolean isBack = false;
    public TextView topBar_title, topBar_left_text, topBar_right_text;
    public LinearLayout topBar_right_layout, topBar_left_layout;
    public int version;
    private long mExitTime = 0;
    private ImageView topBar_right_image;
    private RelativeLayout topBar_right_image_parent;
    private Dialog commonDialog;
    private TextView confirm_content;
    private TextView confirm_ok;
    private TextView confirm_cancel;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initData() {
        topBar_title = (TextView) findViewById(R.id.topBar_title);
        topBar_left_text = (TextView) findViewById(R.id.topBar_left_text);
        topBar_right_text = (TextView) findViewById(R.id.topBar_right_text);
        topBar_right_layout = (LinearLayout) findViewById(R.id.topBar_right_layout);
        topBar_left_layout = (LinearLayout) findViewById(R.id.topBar_left_layout);
        if (topBar_left_layout != null) {
            topBar_left_layout.setOnClickListener(this);
        }
        topBar_right_image = (ImageView) findViewById(R.id.topBar_right_image);
        topBar_right_image_parent = (RelativeLayout) findViewById(R.id.topBar_right_image_parent);
    }

    public void setTitle(String title) {
        initData();
        if (topBar_title != null)
            topBar_title.setText(title);
    }

    public void setNotShowLeft() {
        topBar_left_layout.setVisibility(View.GONE);
    }

    public void setNotLeftText() {
        topBar_left_text.setVisibility(View.GONE);
    }

    public void setRightView(String rightText, int imageID) {
        if (topBar_right_layout != null) {
            topBar_right_layout.setOnClickListener(this);
            if (!TextUtils.isEmpty(rightText)) {
                topBar_right_text.setVisibility(View.VISIBLE);
                topBar_right_text.setText(rightText);
                int padding = (int) getResources().getDimension(R.dimen.common_padding_middle);
                topBar_right_text.setPadding(padding, 0, padding, 0);
            } else {
                topBar_right_text.setVisibility(View.GONE);
            }
            if (imageID != -1) {
                topBar_right_image_parent.setVisibility(View.VISIBLE);
                topBar_right_image.setImageResource(imageID);
            } else {
                topBar_right_image_parent.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.topBar_left_layout) {
            finish();
        } else if (id == R.id.confirm_ok) {
            dismissDialog();
        } else if (id == R.id.confirm_cancel) {
            dismissDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isBack && (System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                Utils.showLog("onKeyDown finish();");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initDialog() {
        commonDialog = DialogUtil.createDialog(this, R.layout.common_dialog, R.style.CustomDialog);
        commonDialog.setCancelable(true);
        confirm_content = commonDialog.findViewById(R.id.confirm_content);
        confirm_ok = commonDialog.findViewById(R.id.confirm_ok);
        confirm_cancel = commonDialog.findViewById(R.id.confirm_cancel);

        confirm_ok.setOnClickListener(this);
        confirm_cancel.setOnClickListener(this);
    }

    public void showDialog(String content, boolean isShowCancel) {
        confirm_content.setText(content);
        confirm_ok.setText(getString(R.string.confirm_ok));
        if (!isShowCancel) {
            confirm_cancel.setVisibility(View.GONE);
            confirm_ok.setBackgroundResource(R.drawable.dialog_btn_bottom_selector);
        } else {
            confirm_cancel.setVisibility(View.VISIBLE);
            confirm_ok.setBackgroundResource(R.drawable.common_dialog_bg_selector);
        }
        DialogUtil.setDialogParams(this, commonDialog, R.dimen.confirm_dialog_width_margin);
        if (!commonDialog.isShowing())
            commonDialog.show();
    }

    public void dismissDialog() {
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
    }

    public void showLoading() {
        DialogHelper.getInstance(mContext).show(null);
    }

    public void hideLoading() {
        DialogHelper.getInstance(mContext).hide();
    }

    public void showLoading(String content) {
        DialogHelper.getInstance(mContext).show(content);
    }


    /**
     * 关闭软键盘
     */
    public void closeInputMethod(View view) {
        //关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
