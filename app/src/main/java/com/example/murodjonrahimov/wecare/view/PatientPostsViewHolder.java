package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.listeners.ViewHolderCallback;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientPostsViewHolder extends RecyclerView.ViewHolder {

  public final static String POST_KEY = "post";
  private TextView message;
  private TextView addedBy;
  private TextView timestamp;
  private TextView comments;
  private TextView needDocroeTextview;
  private ImageView statusOfPost;
  private View lineStatus;
  private Post post;
  private Button delete;
  ViewHolderCallback viewHolderCallback;
  private DatabaseReference databaseReference;

  public PatientPostsViewHolder(final View itemView) {
    super(itemView);

    message = itemView.findViewById(R.id.message_ed);
    addedBy = itemView.findViewById(R.id.posted_by_ed);
    timestamp = itemView.findViewById(R.id.timestamp_ed);
    comments = itemView.findViewById(R.id.comments);
    needDocroeTextview = itemView.findViewById(R.id.need_docroe_textview);
    statusOfPost = itemView.findViewById(R.id.resolve_unresolved_case);
    lineStatus = itemView.findViewById(R.id.line_status);
    delete = itemView.findViewById(R.id.del2);

    databaseReference = FirebaseDatabase.getInstance()
      .getReference()
      .child("posts");

    delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        viewHolderCallback.itemWasClicked(getAdapterPosition());
        databaseReference.child(post.getKey())
          .removeValue();
      }
    });
  }

  public void onBind(final Post post, ViewHolderCallback viewHolderCallback) {
    this.post = post;
    this.viewHolderCallback = viewHolderCallback;
    message.setText(post.getMessage());
    addedBy.setText("Posted by: " + post.getPostedByUserName());
    timestamp.setText(post.getTimeStamp());
    needDocroeTextview.setText(post.getDoctorINeed());
    int countOfComments = post.getCountOfComments();
    setStatusImage(post.isResolved());
    Database.updatePost(post.getKey(), countOfComments);
    String commentCount = String.valueOf(countOfComments);
    comments.setText(commentCount);

    statusOfPost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Database.updateStatusOfPost(post.getKey(), !post.isResolved());
      }
    });
  }

  private void setStatusImage(boolean isResolved) {
    if (isResolved) {
      statusOfPost.setImageResource(R.drawable.resolved_case);
      lineStatus.setBackgroundResource(R.color.color_green);
    } else {
      statusOfPost.setImageResource(R.drawable.unresolved_case);
      lineStatus.setBackgroundResource(R.color.color_white);
    }
  }
}


