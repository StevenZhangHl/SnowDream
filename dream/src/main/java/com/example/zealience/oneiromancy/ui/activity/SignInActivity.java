package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.steven.base.app.SharePConstant;
import com.example.zealience.oneiromancy.entity.SignInEntity;
import com.example.zealience.oneiromancy.ui.SignInNoteAdapter;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.AnimationUtils;
import com.steven.base.util.DateTimeHelper;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.util.ToastUitl;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends BaseActivity {
    private TextView tv_jifen;
    private RecyclerView recyclerview_sign_note;
    private SignInNoteAdapter signInNoteAdapter;
    private List<SignInEntity> signs = new ArrayList<>();
    private int currentPoints = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("签到");
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        recyclerview_sign_note = (RecyclerView) findViewById(R.id.recyclerview_sign_note);
        recyclerview_sign_note.setLayoutManager(new LinearLayoutManager(this));
        signInNoteAdapter = new SignInNoteAdapter(R.layout.item_sign_in_note, new ArrayList<>());
        recyclerview_sign_note.setAdapter(signInNoteAdapter);
        getSignInNotes();
        autoSignIn();
    }

    private void getSignInNotes() {
        signs = GsonUtil.GsonToList(SPUtils.getSharedStringData(this, SharePConstant.KEY_SIGN_IN_NOTES), SignInEntity.class);
        signInNoteAdapter.setNewData(signs);
        if (signs != null && signs.size() != 0) {
            for (int i = 0; i < signs.size(); i++) {
                currentPoints += signs.get(i).getPoints();
            }
        }
        AnimationUtils.raiseNumberByInt(0, currentPoints, 1500, tv_jifen);
    }

    /**
     * 自动签到
     */
    private void autoSignIn() {
        if (checkPermission()) {
            String createTime = DateTimeHelper.getCurrentSystemTime();
            int point = 10;
            SignInEntity entity = new SignInEntity(createTime, point);
            signs.add(0, entity);
            signInNoteAdapter.setNewData(signs);
            SPUtils.setSharedStringData(this, SharePConstant.KEY_SIGN_IN_NOTES, GsonUtil.GsonString(signs));
            ToastUitl.show("自动签到成功,积分+1", 1500);
            AnimationUtils.raiseNumberByInt(currentPoints, currentPoints + point, 1500, tv_jifen);
        } else {
            ToastUitl.show("您今天已经签到过了", 1500);
        }
    }

    /**
     * 检查是否可以签到
     *
     * @return
     */
    private boolean checkPermission() {
        String date = DateTimeHelper.getTimeFromLong(System.currentTimeMillis());
        String signInTime = "";
        if (signs != null && signs.size() != 0) {
            for (int i = 0; i < signs.size(); i++) {
                signInTime = signs.get(i).getCreateTime();
                if (signInTime.contains(date)) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
}
