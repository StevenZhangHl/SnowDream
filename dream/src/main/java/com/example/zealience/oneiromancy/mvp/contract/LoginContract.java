package com.example.zealience.oneiromancy.mvp.contract;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.steven.base.mvp.BaseModel;
import com.steven.base.mvp.BasePresenter;
import com.steven.base.mvp.BaseView;

/**
 * @user steven
 * @createDate 2019/3/6 10:34
 * @description 自定义
 */
public interface LoginContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void inputSuccess(boolean isSuccess);

        void loginSuccess();

        void loginFailure();
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void doLogin(String phone, String password);

        public abstract void observerInput(EditText phone, EditText password);
    }
}
