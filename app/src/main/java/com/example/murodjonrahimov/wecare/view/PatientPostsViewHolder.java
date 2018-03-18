package com.example.murodjonrahimov.wecare.view;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.PostWithComments;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;

public class PatientPostsViewHolder extends RecyclerView.ViewHolder {

    public final static String POST_KEY = "post";

    private TextView message;
    private TextView addedBy;
    private TextView timestamp;
    private TextView comments;
    private Post post;

    public PatientPostsViewHolder(final View itemView) {
        super(itemView);

        message = itemView.findViewById(R.id.message_ed);
        addedBy = itemView.findViewById(R.id.added_by_ed);
        timestamp = itemView.findViewById(R.id.timestamp_ed);
        comments = itemView.findViewById(R.id.comments);

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), PostWithComments.class);
                intent.putExtra(POST_KEY, post);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    public void onBind(Post post) {
        this.post = post;
        message.setText("Message: " + post.getMessage());
        addedBy.setText("Added by: " + post.getAddedBy());
        timestamp.setText("Date: " + post.getTimeStamp());
    }

}
