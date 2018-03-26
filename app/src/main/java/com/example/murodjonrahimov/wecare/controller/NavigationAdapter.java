package com.example.murodjonrahimov.wecare.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.listeners.CategoryPills;
import com.example.murodjonrahimov.wecare.view.NavigationViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by murodjon.rahimov on 3/25/18.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewHolder> {

  private String currentCategory;
  private CategoryPills listener;
  private List<String> categoryList = new ArrayList<>();

  public NavigationAdapter() {
  }

  public NavigationAdapter(CategoryPills listener) {
    this.listener = listener;
  }

  public void setCategoryList(List<String> categoryList) {
    this.categoryList.clear();
    this.categoryList.addAll(categoryList);
  }

  @Override
  public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.item_view_navigation_pills, parent, false);

    return new NavigationViewHolder(view);
  }

  @Override
  public void onBindViewHolder(NavigationViewHolder holder, final int position) {
    holder.onBind(categoryList, position);

    holder.navigationPills.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        currentCategory = categoryList.get(position);

        listener.onCategoryListener(currentCategory);
      }
    });
  }

  @Override
  public int getItemCount() {
    return categoryList.size();
  }
}
