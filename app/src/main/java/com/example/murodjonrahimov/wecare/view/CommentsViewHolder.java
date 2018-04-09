package com.example.murodjonrahimov.wecare.view;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentsViewHolder extends RecyclerView.ViewHolder {

  private TextView commentTV;
  private TextView commentTimestamp;
  private TextView postedBy;
  private String uid;
  private DatabaseReference databaseReference;
  private CardView cardView;

  public CommentsViewHolder(View itemView) {
    super(itemView);

    commentTV = itemView.findViewById(R.id.comment_textview);
    commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
    postedBy = itemView.findViewById(R.id.posted_by_textview);
    uid = Database.getUserId();
    databaseReference = FirebaseDatabase.getInstance()
      .getReference()
      .child("comments");
    cardView = itemView.findViewById(R.id.cardviewcomments);
  }

  public void onBind(final Comment comment) {

    if (uid.equals(comment.getUid())) {
      cardView.setCardBackgroundColor(itemView.getResources()
        .getColor(R.color.oooooo));
      DrawableCompat.setTint(itemView.getResources()
        .getDrawable(R.drawable.shape), ContextCompat.getColor(itemView.getContext(), R.color.guide_color)

      );

      Drawable mDrawable = itemView.getResources()
        .getDrawable(R.drawable.shape);

      mDrawable.setColorFilter(new PorterDuffColorFilter(itemView.getResources()
        .getColor(R.color.guide_color), PorterDuff.Mode.MULTIPLY));

      commentTV.setText(comment.getComment());
      commentTimestamp.setText(comment.getTimeStamp());
      postedBy.setText("Posted by: " + comment.getCommentPostedByUserName());
    } else {

      cardView.setCardBackgroundColor(itemView.getResources()
        .getColor(R.color.colorPrimary));
      commentTV.setText(comment.getComment());
      commentTimestamp.setText(comment.getTimeStamp());
      postedBy.setText("Posted by: " + comment.getCommentPostedByUserName());

      Drawable mDrawable = itemView.getResources()
        .getDrawable(R.drawable.shape);
      mDrawable.setColorFilter(new PorterDuffColorFilter(0xffff00, PorterDuff.Mode.MULTIPLY));
    }
  }
}


