package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;


public class CommentsViewHolder extends RecyclerView.ViewHolder {

    private TextView commentTV;
    private TextView commentTimestamp;

    public CommentsViewHolder(View itemView) {
        super(itemView);

        commentTV = itemView.findViewById(R.id.comment_textview);
        commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
    }

    public void onBind(Comment comment) {
        commentTV.setText(comment.getComment());
        commentTimestamp.setText(comment.getTimeStamp());
    }
}
