package com.snow.weevideo.module.video.adapter;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.snow.weevideo.R;
import com.snow.weevideo.module.video.entity.CommentEntity;

import java.util.List;

/**
 * @user steven
 * @createDate 2019/6/28 11:05
 * @description 自定义
 */
public class CommentListAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {
    private ShowReplyDialogListener showReplyDialogListener;

    public interface ShowReplyDialogListener {
        void onClick(long commentId);
    }

    public CommentListAdapter(int layoutResId, @Nullable List<CommentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentEntity commentEntity) {
        ImageView iv_commenter_head = holder.getView(R.id.iv_commenter_head);
        TextView tv_comment_user_name = holder.getView(R.id.tv_comment_user_name);
        TextView tv_comment_content = holder.getView(R.id.tv_comment_content);
        TextView tv_reply_one_user_name = holder.getView(R.id.tv_reply_one_user_name);
        TextView tv_reply_one_content = holder.getView(R.id.tv_reply_one_content);
        TextView tv_check_more_reply = holder.getView(R.id.tv_check_more_reply);
        TextView tv_author_praise_time = holder.getView(R.id.tv_author_praise_time);
        ImageView iv_praise = holder.getView(R.id.iv_praise);
        TextView tv_praise_count = holder.getView(R.id.tv_praise_count);
        LinearLayout ll_reply = holder.getView(R.id.ll_reply);
        tv_comment_user_name.setText(commentEntity.getUserName());
        tv_comment_content.setText(commentEntity.getCommentContent());
        if (commentEntity.getReplyLists() != null && commentEntity.getReplyLists().size() != 0) {
            ll_reply.setVisibility(View.VISIBLE);
            tv_reply_one_user_name.setText(commentEntity.getReplyLists().get(0).getCommentUserName() + "：");
            tv_reply_one_content.setText(commentEntity.getReplyLists().get(0).getCommentContent());
            if (commentEntity.getReplyLists().size() == 1) {
                tv_check_more_reply.setVisibility(View.GONE);
            } else {
                tv_check_more_reply.setVisibility(View.VISIBLE);
                tv_check_more_reply.setText("查看" + commentEntity.getReplyLists().size() + "条回复 >");
            }
        } else {
            ll_reply.setVisibility(View.GONE);
        }
        if (commentEntity.getPraiseCount() == 0) {
            tv_praise_count.setText("赞");
        } else {
            tv_praise_count.setText(commentEntity.getPraiseCount() + "");
        }
        iv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReplyDialogListener.onClick(commentEntity.getId());
            }
        });
    }

    public void setShowReplyDialogListener(ShowReplyDialogListener showReplyDialogListener) {
        this.showReplyDialogListener = showReplyDialogListener;
    }
}
