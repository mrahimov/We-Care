package com.example.murodjonrahimov.wecare.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DoctorsForumFragment extends Fragment {

    private View view;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference database;
    private String user;
    DatabaseReference database2;
    StorageReference storageReference;


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
        database2 = FirebaseDatabase.getInstance()
                .getReference()
                .child("doctors").child(user);
        storageReference = FirebaseStorage.getInstance()
                .getReference();
        database.keepSynced(true);
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





                final String key = fireBaseRecyclerAdapter.getRef(position)
                        .getKey();
                holder.setKey(key);
                Picasso.get().load(doctor.getUri()).into(holder.imageView1);
                holder.doctorName.setText(doctor.getFirstname()+" "+doctor.getLastname());
                holder.message.setText(doctor.getMessage());
                holder.time.setText(doctor.getTimeStamp());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listenerDoc.onclick(key, doctor.getMessage(), doctor.getTimeStamp(), doctor.getAddedBy(), doctor.getFirstname()+" "+doctor.getLastname());
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
                    public void onClick(final DialogInterface dialog, int which) {

                        database2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    Doctor doctor = dataSnapshot.getValue(Doctor.class);

                                    final DoctorPost doctorPost = new DoctorPost(input1.getText()
                                            .toString(), user, format, doctor.getFirstName(), doctor.getLastName());
                                    Database.saveDoctorPost(doctorPost);
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
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
    public class DoctorPosts extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView message;
        TextView time;
        TextView doctorName;
        Button button;
        DatabaseReference databaseReference;
        FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;
        String name;
        Boolean vis=true;
        ImageView imageView1;
        Button button1;
        String key1;

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
            imageView1= itemView.findViewById(R.id.image2);
            button1 = itemView.findViewById(R.id.upload);
            //button1.setVisibility(View.GONE);
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

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   listenerDoc.Uri(DoctorPosts.this, key1);
                }
            });
        }
        public void setKey(String key1){
            this.key1=key1;
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
                    button1.setVisibility(View.VISIBLE);
                    vis=false;
                    return true;
                }
            } else{
                button.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                vis=true;
                return true;
            }
            return false;
        }
        public void loadingProfileImage(Uri downloadUri, String Lf) {
            Log.d("url", "loadingProfileImage: " + Lf + downloadUri);
            Picasso.get()
                    .load(downloadUri)
                    .into(imageView1);
        }
    }
    public interface onClickListenerDoctor {
        void onclick(String key, String message, String timestamp, String addedBy, String name);
        void Uri(DoctorPosts doctorPosts,String key);
    }



}
