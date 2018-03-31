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
import com.example.murodjonrahimov.wecare.controller.NavigationAdapter;
import com.example.murodjonrahimov.wecare.listeners.CategoryPills;
import com.example.murodjonrahimov.wecare.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AllPatientsPostsFragment extends Fragment implements CategoryPills {

  private View rootView;
  private AllPostsAdapter adapter;
  private NavigationAdapter navigationAdapter;
  private RecyclerView navigationRecyclerview;
  private RecyclerView recyclerView;
  private List<Post> postList;
  private List<String> catigoryList = new ArrayList<>();
  private String category;

  public AllPatientsPostsFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.d_fragment_posts, container, false);

    recyclerView = rootView.findViewById(R.id.recyclerview_allposts);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new AllPostsAdapter();
    recyclerView.setAdapter(adapter);

    navigationRecyclerview = rootView.findViewById(R.id.recyclerview_navigation_pills);
    navigationRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    navigationAdapter = new NavigationAdapter(this);
    navigationRecyclerview.setAdapter(navigationAdapter);

    DatabaseReference ref1 = FirebaseDatabase.getInstance()
      .getReference();
    DatabaseReference ref2 = ref1.child("posts");

    ref2.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        postList = new ArrayList<>();

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

    catigoryList.add("All Post's");
    catigoryList.add("Allergist");
    catigoryList.add("Cardiologist");
    catigoryList.add("Dermatologist");
    catigoryList.add("GP");
    catigoryList.add("Gastroenterologist");
    catigoryList.add("Nephrologist");
    catigoryList.add("Neurologist");
    catigoryList.add("Obstetrician");
    catigoryList.add("Ophthalmologist");
    catigoryList.add("Other");
    catigoryList.add("Otolaryngologist");
    catigoryList.add("Pediatrician");
    catigoryList.add("Psychiatrist");
    catigoryList.add("Rheumatologist");
    catigoryList.add("Urologist");

    navigationAdapter.setCategoryList(catigoryList);

    return rootView;
  }

  @Override
  public void onCategoryListener(final String category) {
    this.category = category;

    DatabaseReference ref1 = FirebaseDatabase.getInstance()
      .getReference();
    DatabaseReference ref2 = ref1.child("posts");

    ref2.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        postList = new ArrayList<>();

        List<Post> newPostList = new ArrayList<>();

        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
          if (dataSnapshot1 != null) {
            Post post = dataSnapshot1.getValue(Post.class);
            String postKey = dataSnapshot1.getKey();
            post.setKey(postKey);
            postList.add(post);
          }
        }

        if (category.equals("All Post's")) {
          adapter.setPostList(postList);
          adapter.notifyDataSetChanged();
        } else {

          for (int i = 0; i < postList.size(); i++) {
            String doctorNeed = postList.get(i)
              .getDoctorINeed();
            if (doctorNeed.equals(category)) {
              newPostList.add(postList.get(i));
            }
          }

          adapter.setPostList(newPostList);
          adapter.notifyDataSetChanged();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}