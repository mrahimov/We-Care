package com.example.murodjonrahimov.wecare.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.murodjonrahimov.wecare.PatientPostForm;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.controller.PatientsPostsAdapter;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientMyPostFragment extends Fragment {

  private PatientsPostsAdapter patientsPostsAdapter;
  private List<Post> myPosts;

  public PatientMyPostFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.p_fragment_posts, container, false);

    RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);

    patientsPostsAdapter = new PatientsPostsAdapter();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setAdapter(patientsPostsAdapter);
    recyclerView.setLayoutManager(linearLayoutManager);

    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Button fab = getActivity().findViewById(R.id.add_fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PatientPostForm.class);
        startActivity(intent);
      }
    });

    DatabaseReference db = Database.getDatabase();

    final String userID = Database.getUserId();

    db.child("posts")
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          myPosts = new ArrayList<>();

          if (dataSnapshot == null) {
            return;
          } else {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
              if (dataSnapshot1.child("addedBy")
                .getValue() == null) {
                continue;
              }

              String retrievedAddedBydataSnapshot1 = dataSnapshot1.child("addedBy")
                .getValue()
                .toString();
              if (retrievedAddedBydataSnapshot1.equals(userID)) {
                Post post = dataSnapshot1.getValue(Post.class);
                String postKey = dataSnapshot1.getKey();
                post.setKey(postKey);
                myPosts.add(post);
              }
            }
          }

          patientsPostsAdapter.setData(myPosts);
          patientsPostsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
  }
}
