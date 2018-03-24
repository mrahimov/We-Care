package com.example.murodjonrahimov.wecare.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.view.CommentsViewHolder;
import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

  private List<Comment> commentList = new ArrayList<>();

  public CommentsAdapter() {
  }

  @Override
  public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View childView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.comment_itemview, parent, false);
    return new CommentsViewHolder(childView);
  }

  @Override
  public void onBindViewHolder(CommentsViewHolder holder, int position) {
    Comment comment = commentList.get(position);
    holder.onBind(comment);
  }

  @Override
  public int getItemCount() {
    return commentList.size();
  }

  public void setData(List<Comment> commentList) {
    this.commentList.clear();
    this.commentList.addAll(commentList);
  }
}
