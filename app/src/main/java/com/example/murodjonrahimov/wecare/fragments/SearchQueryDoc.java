package com.example.murodjonrahimov.wecare.fragments;

/**
 * Created by mohammadnaz on 4/5/18.
 */

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.Random;

import es.dmoral.toasty.Toasty;



public class SearchQueryDoc extends Fragment {
    private View view;
    private RecyclerView recyclerview;
    private FirebaseRecyclerAdapter<Doctor, SearchDoctorList> fireBaseRecyclerAdapter;
    private EditText search;
    private ImageButton searchbutton;
    private ListOfDoctorsFragment.SearchDoctorslistener searchDoctorslistener;
    private Query query;
    DatabaseReference database2;
    String user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            searchDoctorslistener = (ListOfDoctorsFragment.SearchDoctorslistener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         user= Database.getUserId();
         database2 = FirebaseDatabase.getInstance()
                .getReference()
                .child("doctors").child(user);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.p_fragment_doctors, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("doctors").orderByChild("firstName").equalTo(bundle.getString("search1"));
        }

        query.keepSynced(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.RVDoctors);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchbutton = view.findViewById(R.id.searchp);
        search = view.findViewById(R.id.searchTextP);


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().matches("")){
                    Toasty.normal(getActivity().getApplicationContext(),
                            "please enter search query",
                            500)
                            .show();
                }
                else {
                    Toasty.normal(getActivity().getApplicationContext(),
                            "searching for "+ search.getText().toString(),
                            500)
                            .show();
                    searchDoctorslistener.search(search.getText().toString());
                }
            }
        });

        FirebaseRecyclerOptions<Doctor> options = new
                FirebaseRecyclerOptions.Builder<Doctor>()
                .setQuery(query, Doctor.class)
                .build();

        fireBaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Doctor, SearchDoctorList>(options) {

                    @Override
                    public SearchDoctorList onCreateViewHolder(ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.listofdocs, parent, false);

                        return new SearchDoctorList(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull SearchQueryDoc.SearchDoctorList holder,
                                                    int position,
                                                    @NonNull final Doctor doctor) {

                        Picasso.get().load(doctor.getUri()).into(holder.imageView);
                        holder.setNumberOfComments(doctor.getFirstName(), doctor.getLastName());
                        holder.name.setText("Dr. " + doctor.getFirstName() + " " + doctor.getLastName());
                        holder.yearsOfExp.setText("Experience: " + doctor.getYearsOfExperience());
                        holder.country.setText("Country: " + doctor.getCountryOfPractice());
                        holder.major.setText(doctor.getMajor());
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

    public static class SearchDoctorList extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView name;
         TextView yearsOfExp;
         TextView country;
         TextView major;
         TextView numberOfComments;
         int count;
        private GraphView graph;
        private ImageView imageView;
        private DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("comments");


        private SearchDoctorList(View itemView) {
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
                    numberOfComments.setText("Comments: " + String.valueOf(count));
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateData());

                    series.setDrawBackground(true);

                    series.setColor(Color.argb(255, 255, 60, 60));
                    series.setBackgroundColor(Color.argb(100, 64, 224, 208));
                    series.setDrawDataPoints(true);

                    graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
                    graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
                    graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

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

        private DataPoint[] generateData() {
            int count1 = 5;
            Random mRand = new Random();
            DataPoint[] values = new DataPoint[count1];
            for (int i = 0; i < count1; i++) {
                double x = i;
                double f = mRand.nextDouble() * 0.15 + 0.3;
                double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
                DataPoint v = new DataPoint(x, y);
                values[i] = v;
            }
            values[4] = new DataPoint(4, count);
            return values;
        }


    }

}