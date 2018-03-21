package com.example.murodjonrahimov.wecare.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DoctorsForumFragment extends Fragment {
    private View view;
    FloatingActionButton floatingActionButton;
    DatabaseReference database;
    private RecyclerView recyclerView;
    onClickListenerDoc listenerDoc;
    FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listenerDoc = (onClickListenerDoc) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    public DoctorsForumFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference().child("DoctorPost");
        database.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.d_fragment_doctors, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.doctorForum);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton = view.findViewById(R.id.fab);

        FirebaseRecyclerOptions<DoctorPost> options =
                new FirebaseRecyclerOptions.Builder<DoctorPost>()
                        .setQuery(database, DoctorPost.class)
                        .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorPost, DoctorPosts>(options) {


            @Override
            public DoctorsForumFragment.DoctorPosts onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listdoctorpost_itemview, parent, false);

                return new DoctorsForumFragment.DoctorPosts(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DoctorsForumFragment.DoctorPosts holder, int position,
                                            @NonNull final DoctorPost doctor) {
                holder.message.setText(doctor.getMessage());
                holder.time.setText(doctor.getTimeStamp());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenerDoc.onclick(doctor.getKey());

                    }
                });
            }
        };
        recyclerView.setAdapter(fireBaseRecyclerAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add post");

                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.alertbox, (ViewGroup) getView(), false);
                final EditText input1 = viewInflated.findViewById(R.id.input1);

                builder.setView(viewInflated);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                final String format = simpleDateFormat.format(new Date());

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DoctorPost doctorPost = new DoctorPost(input1.getText().toString(),"Doctor", format);
                        Database.saveDoctorPost(doctorPost);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
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

    public static class DoctorPosts extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;
        public DoctorPosts(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message1);
            time = itemView.findViewById(R.id.time1);
        }
    }
    public interface onClickListenerDoc {
        void onclick(String key);
    }

}


