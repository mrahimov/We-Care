package com.example.murodjonrahimov.wecare.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorsForumFragment extends Fragment {

    private View view;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference database;
    private DatabaseReference database2;
    private String user;
    private String name;

    private RecyclerView recyclerView;
    private onClickListenerDoctor listenerDoc;
    private FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listenerDoc = (onClickListenerDoctor) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    public DoctorsForumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance()
                .getReference()
                .child("DoctorPost");
        user = Database.getUserId();




        database.keepSynced(true);
        FirebaseMessaging.getInstance()
                .subscribeToTopic("notifications");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                new FirebaseRecyclerOptions.Builder<DoctorPost>().setQuery(database, DoctorPost.class)
                        .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorPost, DoctorPosts>(options) {

            @Override
            public DoctorsForumFragment.DoctorPosts onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listdoctorpost_itemview, parent, false);

                return new DoctorsForumFragment.DoctorPosts(view, fireBaseRecyclerAdapter);
            }

            @Override
            protected void onBindViewHolder(@NonNull final DoctorsForumFragment.DoctorPosts holder, int position,
                                            @NonNull final DoctorPost doctor) {
                final String[] name2 = {null};
                database2 = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("doctors")
                        .child(doctor.getAddedBy());

                database2.getRef()
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Doctor doctors = dataSnapshot.getValue(Doctor.class);
                                name2[0] = doctors.getFirstName() + " " + doctors.getLastName();
                                holder.doctorName.setText(name2[0]);
                                holder.message.setText(doctor.getMessage());
                                holder.time.setText(doctor.getTimeStamp());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                final String key = fireBaseRecyclerAdapter.getRef(position)
                        .getKey();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listenerDoc.onclick(key, doctor.getMessage(), doctor.getTimeStamp(), doctor.getAddedBy(), name2[0]);
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

                View viewInflated = LayoutInflater.from(getContext())
                        .inflate(R.layout.alertbox, (ViewGroup) getView(), false);
                final EditText input1 = viewInflated.findViewById(R.id.input1);

                builder.setView(viewInflated);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                final String format = simpleDateFormat.format(new Date());

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DoctorPost doctorPost = new DoctorPost(input1.getText()
                                .toString(), user, format);
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

    public static class DoctorPosts extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView message;
        TextView time;
        TextView doctorName;
        Button button;
        DatabaseReference databaseReference;
        FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;
        String name;
        Boolean vis=true;

        public DoctorPosts(View itemView, FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter) {
            super(itemView);
            message = itemView.findViewById(R.id.message1);
            time = itemView.findViewById(R.id.time1);
            doctorName = itemView.findViewById(R.id.posted_by);
            button = itemView.findViewById(R.id.Del1);
            button.setVisibility(View.GONE);
            this.fireBaseRecyclerAdapter = fireBaseRecyclerAdapter;
            button.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            String user = Database.getUserId();
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("doctors").child(user);

            databaseReference.getRef()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Doctor doctor2 = dataSnapshot.getValue(Doctor.class);
                            name = doctor2.getFirstName() + " " + doctor2.getLastName();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == button.getId()) {
                if (name.equals(doctorName.getText().toString())) {
                    fireBaseRecyclerAdapter.getRef(getAdapterPosition()).removeValue();
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == itemView.getId() && vis) {
                if (name.equals(doctorName.getText().toString())) {
                    button.setVisibility(View.VISIBLE);
                    vis=false;
                    return true;
                }
            } else{
                button.setVisibility(View.GONE);
                vis=true;
                return true;
            }
            return false;
        }
    }

    public interface onClickListenerDoctor {
        void onclick(String key, String message, String timestamp, String addedBy, String name);
    }
}
