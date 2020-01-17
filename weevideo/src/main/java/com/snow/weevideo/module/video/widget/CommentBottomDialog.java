package com.snow.weevideo.module.video.widget;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.snow.weevideo.R;
import com.snow.weevideo.module.video.adapter.CommentListAdapter;
import com.snow.weevideo.module.video.entity.CommentEntity;
import com.snow.weevideo.module.video.entity.ReplyCommentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/6/28 10:27
 * @description 自定义
 */
public class CommentBottomDialog extends BottomBaseDialog<CommentBottomDialog> implements CommentListAdapter.ShowReplyDialogListener {
    private Context mContext;
    private View view;
    private RecyclerView recyclerview_video_comments;
    private TextView tv_comment_num;
    private ImageView iv_close_dialog;
    private CommentListAdapter commentListAdapter;
    private BottomSheetBehavior behavior;
    private LinearLayout ll_reply_hide;

    public CommentBottomDialog(Context context, View animateView) {
        super(context, animateView);
        mContext = context;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMaxHeight = mDisplayMetrics.heightPixels;
    }

    private void init() {
        widthScale(1f);
        heightScale(1f);
    }

    @Override
    public View onCreateView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_video_detail_comment, null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        ll_reply_hide = (LinearLayout) findViewById(R.id.ll_reply_hide);
        recyclerview_video_comments = (RecyclerView) view.findViewById(R.id.recyclerview_video_comments);
        tv_comment_num = (TextView) view.findViewById(R.id.tv_comment_num);
        iv_close_dialog = (ImageView) view.findViewById(R.id.iv_close_dialog);
        behavior = BottomSheetBehavior.from(ll_reply_hide);
        behavior.setPeekHeight(0);
        iv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        commentListAdapter = new CommentListAdapter(R.layout.item_video_detail_comment, new ArrayList<>());
        recyclerview_video_comments.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_video_comments.setAdapter(commentListAdapter);
        commentListAdapter.setShowReplyDialogListener(this);
        getCommentData();
    }

    public void getCommentData() {
        List<CommentEntity> commentEntities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setId(i);
            commentEntity.setUserId(i * 2);
            commentEntity.setUserName("张三");
            commentEntity.setCommentContent("再借啦据了解了第三方阿地方聚聚阿道夫");
            commentEntity.setPraiseCount(i);
            List<ReplyCommentEntity> replyCommentEntities = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                ReplyCommentEntity replyCommentEntity = new ReplyCommentEntity();
                replyCommentEntity.setId(j);
                replyCommentEntity.setCommentUserName("李四");
                replyCommentEntity.setCommentContent("大剪指甲啊");
                replyCommentEntity.setReplyContent("而我如");
                replyCommentEntity.setCommentUserId(j * 2);
                replyCommentEntities.add(replyCommentEntity);
            }
            commentEntity.setReplyLists(replyCommentEntities);
            commentEntities.add(commentEntity);
        }
        commentListAdapter.setNewData(commentEntities);
    }

    @Override
    public void onClick(long id) {
        ll_reply_hide.setVisibility(View.VISIBLE);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
