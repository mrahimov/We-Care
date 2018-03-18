package com.example.murodjonrahimov.wecare.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;
import com.example.murodjonrahimov.wecare.view.PatientPostsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PatientsPostsAdapter extends RecyclerView.Adapter<PatientPostsViewHolder> {

    private List<Post> postsList = new ArrayList<>();

    public PatientsPostsAdapter() {
    }

    @Override
    public PatientPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_itemview, parent, false);
        return new PatientPostsViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(PatientPostsViewHolder holder, int position) {
        Post post = postsList.get(position);
        holder.onBind(post);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public void setData(List<Post> postsList) {
        this.postsList.clear();
        this.postsList.addAll(postsList);
    }
}
