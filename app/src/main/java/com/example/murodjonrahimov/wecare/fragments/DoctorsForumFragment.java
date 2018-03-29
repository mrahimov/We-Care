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
import android.widget.EditText;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorsForumFragment extends Fragment {

    private View view;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference database;
    private RecyclerView recyclerView;
    private onClickListenerDoctor listenerDoc;
    private FirebaseRecyclerAdapter<DoctorPost, DoctorPostsViewHolder> fireBaseRecyclerAdapter;

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
        database = FirebaseDatabase.getInstance().getReference().child("DoctorPost");
        database.keepSynced(true);
        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
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

        FirebaseRecyclerOptions<DoctorPost> options = new FirebaseRecyclerOptions.Builder<DoctorPost>().setQuery(database, DoctorPost.class)
                        .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorPost, DoctorPostsViewHolder>(options) {

            @Override
            public DoctorPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdoctorpost_itemview, parent, false);
                return new DoctorPostsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DoctorPostsViewHolder holder, int position,
                                            @NonNull final DoctorPost doctor) {
                holder.message.setText(doctor.getMessage());
                holder.time.setText(doctor.getTimeStamp());
//                holder.doctorName.setText(doctor.);

                final String key = fireBaseRecyclerAdapter.getRef(position).getKey();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listenerDoc.onclick(key, doctor.getMessage(), doctor.getTimeStamp(), doctor.getAddedBy());
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
                final EditText docPost = viewInflated.findViewById(R.id.doctor_post);

                builder.setView(viewInflated);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                final String format = simpleDateFormat.format(new Date());

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DoctorPost doctorPost = new DoctorPost(docPost.getText().toString(), "Doctor", format);
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

    public static class DoctorPostsViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView time;
        private TextView doctorName;

        public DoctorPostsViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message1);
            time = itemView.findViewById(R.id.time1);
            doctorName = itemView.findViewById(R.id.posted_by);
        }
    }

    public interface onClickListenerDoctor {
        void onclick(String key, String message, String timestamp, String addedBy);
    }
}


