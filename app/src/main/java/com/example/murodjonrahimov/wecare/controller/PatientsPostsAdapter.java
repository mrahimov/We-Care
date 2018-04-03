package com.example.murodjonrahimov.wecare.controller;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.murodjonrahimov.wecare.PostWithComments;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.listeners.ViewHolderCallback;
import com.example.murodjonrahimov.wecare.model.Post;
import com.example.murodjonrahimov.wecare.view.PatientPostsViewHolder;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class PatientsPostsAdapter extends RecyclerView.Adapter<PatientPostsViewHolder> {
    public final static String POST_KEY = "post";
    private List<Post> postsList = new ArrayList<>();

    public PatientsPostsAdapter() {
    }

    @Override
    public PatientPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_itemview, parent, false);
        return new PatientPostsViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(final PatientPostsViewHolder holder, final int position) {
        Post post = postsList.get(position);
        holder.onBind(post, new ViewHolderCallback() {
            @Override
            public void itemWasClicked(int position) {
                postsList.remove(position);
                notifyItemRemoved(position);
                Toasty.custom(holder.itemView.getContext(), "Post Deleted",
                        ContextCompat.getDrawable(holder.itemView.getContext(),
                                R.drawable.ic_rubbish_bin),
                        1000, true).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PostWithComments.class);
                intent.putExtra(POST_KEY, postsList.get(position));
                holder.itemView.getContext()
                        .startActivity(intent);
            }
        });
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
