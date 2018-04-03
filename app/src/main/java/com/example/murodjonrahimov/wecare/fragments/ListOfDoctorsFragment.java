package com.example.murodjonrahimov.wecare.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

public class ListOfDoctorsFragment extends Fragment {
  private View view;
  private DatabaseReference Database;
  private RecyclerView recyclerview;
  private FirebaseRecyclerAdapter<Doctor, ListOfDoctorsFragment.DoctorsListViewHolder> fireBaseRecyclerAdapter;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  public ListOfDoctorsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Database = FirebaseDatabase.getInstance()
      .getReference()
      .child("doctors");
    Database.keepSynced(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.p_fragment_doctors, container, false);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    recyclerview = view.findViewById(R.id.RVDoctors);
    recyclerview.setHasFixedSize(true);
    recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

    FirebaseRecyclerOptions<Doctor> options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(Database, Doctor.class)
      .build();

    fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doctor, ListOfDoctorsFragment.DoctorsListViewHolder>(options) {

      @Override
      public DoctorsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.listofdocs, parent, false);

        return new DoctorsListViewHolder(view);
      }

      @Override
      protected void onBindViewHolder(@NonNull ListOfDoctorsFragment.DoctorsListViewHolder holder, int position,
                                      @NonNull final Doctor doctor) {

        Picasso.get()
          .load(doctor.getUri())
          .into(holder.imageView);
        holder.setNumberOfComments(doctor.getFirstName(), doctor.getLastName());
        holder.name.setText("Name: " + doctor.getFirstName() + " " + doctor.getLastName());
        holder.yearsOfExp.setText("years of exp: " + doctor.getYearsOfExperience());
        holder.country.setText("Country: " + doctor.getCountryOfPractice());
        holder.major.setText("Major: " + doctor.getMajor());
      }
    };
    recyclerview.setAdapter(fireBaseRecyclerAdapter);
  }

  @Override
  public void onStart() {
    super.onStart();
    fireBaseRecyclerAdapter.startListening();
  }

  @Override
  public void onStop() {
    super.onStop();
    fireBaseRecyclerAdapter.stopListening();
  }

  public static class DoctorsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name;
    TextView yearsOfExp;
    TextView country;
    TextView major;
    TextView numberOfComments;
    private int count;
    GraphView graph;
    ImageView imageView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
      .getReference()
      .child("comments");

    private DoctorsListViewHolder(View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.DocName1);
      yearsOfExp = itemView.findViewById(R.id.years_of_experience);
      country = itemView.findViewById(R.id.country);
      major = itemView.findViewById(R.id.major);
      numberOfComments = itemView.findViewById(R.id.number_of_doctors_comments1);
      graph = itemView.findViewById(R.id.graph);
      imageView = itemView.findViewById(R.id.image1);
    }

    private void setNumberOfComments(final String firstName, final String lastName) {

      databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          for (DataSnapshot comment : dataSnapshot.getChildren()) {
            Comment comment1 = comment.getValue(Comment.class);
            String commentPostedBy = comment1.getCommentPostedByUserName();
            if (commentPostedBy != null && commentPostedBy.equals(firstName + " " + lastName)) {
              count++;
            }
          }
          numberOfComments.setText("Number of comments: " + String.valueOf(count));
          LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1), new DataPoint(1, 5), new DataPoint(2, 3), new DataPoint(3, 2), new DataPoint(4, count)
          });
          series.setDrawBackground(true);

          series.setColor(Color.argb(255, 255, 60, 60));
          series.setBackgroundColor(Color.argb(100, 64, 224, 208));
          series.setDrawDataPoints(true);

          graph.setTitle("Doctor Activity");
          graph.getGridLabelRenderer()
            .setGridStyle(GridLabelRenderer.GridStyle.NONE);
          graph.getGridLabelRenderer()
            .setVerticalLabelsVisible(false);
          graph.getGridLabelRenderer()
            .setHorizontalLabelsVisible(false);

          graph.addSeries(series);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }

    @Override
    public void onClick(View v) {

    }
  }
}