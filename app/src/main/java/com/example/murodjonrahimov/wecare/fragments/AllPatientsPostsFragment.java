package com.example.murodjonrahimov.wecare.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Map;

public class AllPatientsPostsFragment extends Fragment {
  private View rootView;
  private AllPostsAdapter adapter;
  private List<Post> postList = new ArrayList<>();
  private RecyclerView recyclerView;

  public AllPatientsPostsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.d_fragment_posts, container, false);


    recyclerView = rootView.findViewById(R.id.recyclerview_allposts);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    DatabaseReference ref1 = FirebaseDatabase.getInstance()
      .getReference();
    DatabaseReference ref2;
    ref2 = ref1.child("posts");

    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        collectPosts((Map<String,Object>) dataSnapshot.getValue());

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });


    return rootView;
  }

  private void collectPosts(Map<String, Object> posts) {
    Log.d("TED", posts.size() + "");
    for (Map.Entry<String, Object> entry : posts.entrySet()) {
      //Get user map
      Map singlePost = (Map) entry.getValue();
      //Get phone field and append to list
      Post post = new Post();
      post.setMessage((String) singlePost.get("message"));
      post.setAddedBy((String) singlePost.get("addedBy"));
      post.setTimeStamp((String) singlePost.get("timeStamp"));

      postList.add(post);

    }
    //if (postList != null) {
    Log.d("TEG", "" + postList.size());
    adapter = new AllPostsAdapter(postList);
    recyclerView.setAdapter(adapter);

    //}

  }
}
