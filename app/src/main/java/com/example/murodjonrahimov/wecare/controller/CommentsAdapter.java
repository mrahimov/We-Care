package com.example.murodjonrahimov.wecare.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.view.CommentsViewHolder;
import com.example.murodjonrahimov.wecare.view.RViewHolder;
import com.example.murodjonrahimov.wecare.view.SViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter{

  private List<Comment> commentList = new ArrayList<>();
  private String user= Database.getUserId();
  private static final int VIEW_TYPE_MESSAGE_SENT = 1;
  private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

  public CommentsAdapter() {
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;

    if (viewType == VIEW_TYPE_MESSAGE_SENT) {
      view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.zsentmessage_itemview, parent, false);
      return new SViewHolder(view);
    } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
      view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.zrecievedmessage_itemview, parent, false);
      return new RViewHolder(view);
    }

    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Comment comment = commentList.get(position);
    switch (holder.getItemViewType()) {
      case VIEW_TYPE_MESSAGE_SENT:
        ((SViewHolder) holder).bind(comment);
        break;
      case VIEW_TYPE_MESSAGE_RECEIVED:
        ((RViewHolder) holder).bind(comment);
    }

  }

  @Override
  public int getItemCount() {
    return commentList.size();
  }

  public void setData(List<Comment> commentList) {
    this.commentList.clear();
    this.commentList.addAll(commentList);
  }

  @Override
  public int getItemViewType(int position) {
    Comment comment= commentList.get(position);
    String uid= comment.getUid();

    if (uid.equals(user)) {
      return VIEW_TYPE_MESSAGE_SENT;
    } else {
      return VIEW_TYPE_MESSAGE_RECEIVED;
    }
  }
}
