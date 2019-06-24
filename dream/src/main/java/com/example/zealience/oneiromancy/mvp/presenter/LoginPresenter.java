package com.example.zealience.oneiromancy.mvp.presenter;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.steven.base.bean.UserInfo;
import com.example.zealience.oneiromancy.mvp.contract.LoginContract;
import com.steven.base.util.UserHelper;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.LoadingDialog;

/**
 * @user steven
 * @createDate 2019/3/6 10:34
 * @description 自定义
 */
public class LoginPresenter extends LoginContract.Presenter {
    private boolean isPhoneCorrect = false;
    private boolean isPasswrodCorrect = false;
    private LoadingDialog loadingDialog;
    private Handler mHandler;

    @Override
    public void doLogin(String phone, String password) {
        if (!"520".equals(phone)) {
            ToastUitl.showShort("账号不正确");
            return;
        }
        if (!"1314".equals(password)) {
            ToastUitl.showShort("密码不正确");
            return;
        }
        loadingDialog = new LoadingDialog();
        loadingDialog.showDialogForLoading(mContext, "登录中...", false);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.cancelDialogForLoading();
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(phone);
                UserHelper.saveUserInfo(mContext, userInfo);
                mView.loginSuccess();
            }
        }, 1000);
    }

    @Override
    public void observerInput(EditText phone, EditText password) {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    isPhoneCorrect = true;
                } else {
                    isPhoneCorrect = false;
                }
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4) {
                    isPasswrodCorrect = true;
                } else {
                    isPasswrodCorrect = false;
                }
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateView() {
        if (isPhoneCorrect && isPasswrodCorrect) {
            mView.inputSuccess(true);
        } else {
            mView.inputSuccess(false);
        }
    }
}
