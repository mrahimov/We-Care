package com.example.murodjonrahimov.wecare.view;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;

public class PatientPostsViewHolder extends RecyclerView.ViewHolder {

    private TextView message;
    private TextView addedBy;
    private TextView timestamp;

    public PatientPostsViewHolder(View itemView) {
        super(itemView);

        message = itemView.findViewById(R.id.message_ed);
        addedBy = itemView.findViewById(R.id.added_by_ed);
        timestamp = itemView.findViewById(R.id.timestamp_ed);
    }

    public void onBind(Post post) {
        message.setText("Message: " + post.getMessage());
        addedBy.setText("Added by: " + post.getAddedBy());
        timestamp.setText("Date: " + post.getTimeStamp());
    }


}
