package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;


public class CommentsViewHolder extends RecyclerView.ViewHolder {

    private TextView commentTV;

    public CommentsViewHolder(View itemView) {
        super(itemView);

        commentTV = itemView.findViewById(R.id.comment_textview);
    }

    public void onBind(Comment comment) {
        commentTV.setText(comment.getComment());
    }
}
