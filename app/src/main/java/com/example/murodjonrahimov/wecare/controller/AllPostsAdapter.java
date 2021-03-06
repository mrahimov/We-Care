package com.example.murodjonrahimov.wecare.controller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.PostWithComments;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murodjon.rahimov on 3/17/18.
 */

public class AllPostsAdapter extends RecyclerView.Adapter<AllPostsAdapter.AllPostsViewHolder> {

  private List<Post> postList = new ArrayList<>();

  public AllPostsAdapter() {
  }

  @Override
  public AllPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.post_itemview, parent, false);
    return new AllPostsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AllPostsViewHolder holder, int position) {
    holder.onBind(postList.get(position));
  }

  @Override
  public int getItemCount() {
    return postList.size();
  }

  public void setPostList(List<Post> postList) {
    this.postList.clear();
    this.postList.addAll(postList);
  }

  public static class AllPostsViewHolder extends RecyclerView.ViewHolder {

    public final static String POST_KEY = "post";
    private Post post;

    private TextView textViewAddedBy;
    private TextView textViewMessage;
    private TextView textViewTimeStamp;
    private TextView textViewComments;
    private TextView textViewNeedDoceor;
    private View lineStatus;
    private LinearLayout linearLayoutComits;
    private ImageView statusOfPost;

    private Button button;

    public AllPostsViewHolder(final View itemView) {
      super(itemView);
      textViewAddedBy = itemView.findViewById(R.id.posted_by_ed);
      textViewMessage = itemView.findViewById(R.id.message_ed);
      textViewTimeStamp = itemView.findViewById(R.id.timestamp_ed);
      textViewComments = itemView.findViewById(R.id.comments);
      textViewNeedDoceor = itemView.findViewById(R.id.need_docroe_textview);
      linearLayoutComits = itemView.findViewById(R.id.linear_layout_comments);
      lineStatus = itemView.findViewById(R.id.line_status);

      statusOfPost = itemView.findViewById(R.id.resolve_unresolved_case);

      button = itemView.findViewById(R.id.del2);
      button.setVisibility(View.GONE);

      linearLayoutComits.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(itemView.getContext(), PostWithComments.class);
          intent.putExtra(POST_KEY, post);
          itemView.getContext()
            .startActivity(intent);
        }
      });
    }

    public void onBind(Post post) {
      this.post = post;
      textViewNeedDoceor.setText(post.getDoctorINeed());
      textViewMessage.setText(post.getMessage());
      textViewAddedBy.setText("Posted By: " + post.getPostedByUserName());
      textViewTimeStamp.setText(post.getTimeStamp());
      int countOfComments = post.getCountOfComments();
      String commentCount = String.valueOf(countOfComments);
      textViewComments.setText(commentCount);
      setStatusImage(post.isResolved());
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
}





