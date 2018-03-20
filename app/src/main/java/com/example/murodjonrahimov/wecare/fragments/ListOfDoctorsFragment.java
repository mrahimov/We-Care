package com.example.murodjonrahimov.wecare.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


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
        Database = FirebaseDatabase.getInstance().getReference().child("doctors");
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

        FirebaseRecyclerOptions<Doctor> options =
                new FirebaseRecyclerOptions.Builder<Doctor>()
                        .setQuery(Database, Doctor.class)
                        .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doctor, ListOfDoctorsFragment.DoctorsListViewHolder>(options) {


            @Override
            public DoctorsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listofdocs, parent, false);

                return new DoctorsListViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ListOfDoctorsFragment.DoctorsListViewHolder holder, int position, @NonNull Doctor doctor) {

                holder.name.setText(doctor.getFirstName() + " " + doctor.getLastName());
                holder.yearsOfExp.setText(doctor.getYearsOfExperience());
                holder.country.setText(doctor.getCountryOfPractice());
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

    public static class DoctorsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView yearsOfExp;
        TextView country;
        TextView major;

        public DoctorsListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.DocName1);
            yearsOfExp = itemView.findViewById(R.id.years_of_experience);
            country = itemView.findViewById(R.id.country);
            major = itemView.findViewById(R.id.major);
        }

        @Override
        public void onClick(View v) {
            
        }
    }

}
