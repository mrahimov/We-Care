package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mohammadnaz on 4/8/18.
 */

public class RViewHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;

    public RViewHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
    }

    public void bind(Comment comment) {
        messageText.setText(comment.getComment());
        timeText.setText(comment.getTimeStamp());
        nameText.setText(comment.getCommentPostedByUserName());

    }


}
