package com.example.murodjonrahimov.wecare.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murodjon.rahimov on 3/17/18.
 */

public class AllPostsAdapter extends RecyclerView.Adapter<AllPostsAdapter.AllPostsViewHolder> {

  List<Post> postList = new ArrayList<>();

  public AllPostsAdapter(List<Post> postList) {
    this.postList = postList;
  }

  @Override
  public AllPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_allpasts, parent, false);
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

  public class AllPostsViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewAddedBy;
    private TextView textViewMessage;
    private TextView textViewTimeStamp;

    public AllPostsViewHolder(View itemView) {
      super(itemView);
      textViewAddedBy = itemView.findViewById(R.id.textview_addaedy);
      textViewMessage = itemView.findViewById(R.id.textview_message);
      textViewTimeStamp = itemView.findViewById(R.id.textview_timeStamp);
    }

    public void onBind(Post post) {
      textViewAddedBy.setText(post.getAddedBy());
      textViewMessage.setText(post.getMessage());
      textViewTimeStamp.setText(post.getTimeStamp());

    }
  }
}