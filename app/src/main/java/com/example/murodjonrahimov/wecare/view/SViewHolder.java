package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;

/**
 * Created by mohammadnaz on 4/8/18.
 */

public class SViewHolder extends RecyclerView.ViewHolder {

    TextView messageText, timeText;

    public SViewHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
    }

    public void bind(Comment comment) {
        messageText.setText(comment.getComment());
        timeText.setText(comment.getTimeStamp());
    }


}
