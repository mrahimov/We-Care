package com.example.murodjonrahimov.wecare.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.controller.AllPostsAdapter;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AllPatientsPostsFragment extends Fragment {
  private View rootView;
  private AllPostsAdapter adapter;
  private List<Post> postList = new ArrayList<>();
  private RecyclerView recyclerView;

  public AllPatientsPostsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.d_fragment_posts, container, false);

    recyclerView = rootView.findViewById(R.id.recyclerview_allposts);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new AllPostsAdapter();
    recyclerView.setAdapter(adapter);

    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref2 = ref1.child("posts");

    ref2.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
          if (dataSnapshot1 != null) {
            Post post = dataSnapshot1.getValue(Post.class);
            String postKey = dataSnapshot1.getKey();
            post.setKey(postKey);
            postList.add(post);
          }
        }

        adapter.setPostList(postList);
        adapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

    return rootView;
  }
}