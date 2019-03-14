package com.example.zealience.oneiromancy.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zealience.oneiromancy.entity.SignInEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/14 17:57
 * @description 自定义
 */
public class SignInNoteAdapter extends BaseQuickAdapter<SignInEntity,BaseViewHolder> {
    public SignInNoteAdapter(int layoutResId, @Nullable List<SignInEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignInEntity item) {

    }
}
