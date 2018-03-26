package com.example.murodjonrahimov.wecare.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Post;
import java.util.List;

/**
 * Created by murodjon.rahimov on 3/25/18.
 */

public class NavigationViewHolder extends RecyclerView.ViewHolder {

  private TextView navigationPills;
  private List<String> categoryList;

  public NavigationViewHolder(View itemView) {
    super(itemView);

    navigationPills = itemView.findViewById(R.id.navigation_pills);

  }
  public void onBind(List<String> categoryList, int position) {
    this.categoryList = categoryList;
    navigationPills.setText(categoryList.get(position));

  }
}
