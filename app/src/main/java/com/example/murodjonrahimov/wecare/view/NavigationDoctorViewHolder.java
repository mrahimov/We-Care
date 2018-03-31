package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.R;
import java.util.ArrayList;
import java.util.List;

public class NavigationDoctorViewHolder extends RecyclerView.ViewHolder {

  public TextView navigationPills;
  private List<String> categoryList = new ArrayList<>();

  public NavigationDoctorViewHolder(View itemView) {
    super(itemView);

    navigationPills = itemView.findViewById(R.id.navigation_pills);
  }

  public void onBind(List<String> categoryList, int position) {
    this.categoryList.clear();
    this.categoryList.addAll(categoryList);
    navigationPills.setText(categoryList.get(position));
  }
}
