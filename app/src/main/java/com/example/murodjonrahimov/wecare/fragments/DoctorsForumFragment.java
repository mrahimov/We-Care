package com.example.murodjonrahimov.wecare.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;


public class DoctorsForumFragment extends Fragment {

    private View view;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference database;
    private String user;
    private DatabaseReference database2;
    FirebaseRecyclerOptions<DoctorPost> options;
    private RecyclerView recyclerView;
    private onClickListenerDoctor listenerDoc;
    private FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;
    private Button search;
    private EditText searchText;

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
        user = Database.getUserId();
        database = FirebaseDatabase.getInstance()
                .getReference()
                .child("DoctorPost");
        setHasOptionsMenu(true);
        database2 = FirebaseDatabase.getInstance()
                .getReference()
                .child("doctors").child(user);

//        query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("DoctorPost").orderByChild("addedBy").equalTo(user);
        //        query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("DoctorPost").orderByChild("firstname").equalTo("g");



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


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        floatingActionButton = view.findViewById(R.id.fab);
        search= view.findViewById(R.id.searchpost1);
        searchText =view.findViewById(R.id.searchPost);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText().toString().matches("")){
                    Toasty.normal(getActivity().getApplicationContext(),
                            "please enter search query",
                            500)
                            .show();
                }
                else {
                    Toasty.normal(getActivity().getApplicationContext(),
                            "searching for "+ searchText.getText().toString(),
                            500)
                            .show();
                    listenerDoc.search(searchText.getText().toString());

                }
            }
        });

//         options = new FirebaseRecyclerOptions.Builder<DoctorPost>().setQuery(query, DoctorPost.class)
//                        .build();
        options = new FirebaseRecyclerOptions.Builder<DoctorPost>().setQuery(database, DoctorPost.class)
                .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorPost, DoctorPosts>(options) {

            @Override
            public DoctorsForumFragment.DoctorPosts onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listdoctorpost_itemview, parent, false);

                return new DoctorsForumFragment.DoctorPosts(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final DoctorsForumFragment.DoctorPosts holder, int position,
                                            @NonNull final DoctorPost doctor) {

                final String key = fireBaseRecyclerAdapter.getRef(position)
                        .getKey();
                if(doctor.getAddedBy().equals(user)){
                    holder.button1.setVisibility(View.VISIBLE);
                    holder.button.setVisibility(View.VISIBLE);
                }
                else {
                    holder.button1.setVisibility(View.GONE);
                    holder.button.setVisibility(View.GONE);
                }
                Glide.with(holder.imageView1.getContext())
                        .load(doctor.getUri()).into(holder.imageView1);

                holder.doctorName.setText(doctor.getFirstname() + " " + doctor.getLastname());
                holder.message.setText(doctor.getMessage());
                holder.time.setText(doctor.getTimeStamp());

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user.equals(doctor.getAddedBy())) {
                            fireBaseRecyclerAdapter.getRef(holder.getAdapterPosition()).removeValue();
                            Toasty.custom(holder.itemView.getContext(), "Post Deleted",
                                    ContextCompat.getDrawable(holder.itemView.getContext(),
                                            R.drawable.ic_rubbish_bin),
                                    1000, true).show();

                        }
                    }
                });

                holder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenerDoc.Uri(key);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listenerDoc.onclick(key, doctor.getMessage(), doctor.getTimeStamp(), doctor.getAddedBy(), doctor.getFirstname() + " " + doctor.getLastname(), doctor.getUri());
                    }
                });
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
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
                                if (dataSnapshot.exists()) {
                                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                                    final DoctorPost doctorPost = new DoctorPost(input1.getText()
                                            .toString(), user, format, doctor.getFirstName(), doctor.getLastName());
                                    Database.saveDoctorPost(doctorPost);
                                    fireBaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                        @Override
                                        public void onItemRangeInserted(int positionStart, int itemCount) {
                                            super.onItemRangeInserted(positionStart, itemCount);
                                            int friendlyMessageCount = fireBaseRecyclerAdapter.getItemCount();
                                            int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                                            // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                                            // to the bottom of the list to show the newly added message.
                                            if (lastVisiblePosition == -1 ||
                                                    (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                                                linearLayoutManager.scrollToPosition(positionStart);
                                            }
                                        }
                                    });
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

    public class DoctorPosts extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView message;
        TextView time;
        TextView doctorName;
        Button button;
        ImageView imageView1;
        Button button1;


        public DoctorPosts(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message1);
            time = itemView.findViewById(R.id.time1);
            doctorName = itemView.findViewById(R.id.posted_by);
            button = itemView.findViewById(R.id.Del1);
            button.setOnClickListener(this);
            imageView1 = itemView.findViewById(R.id.image2);
            button1 = itemView.findViewById(R.id.upload);


        }
        @Override
        public void onClick(View v) {
            if (v.getId() == button.getId()) {


                }
            }
        }


    public interface onClickListenerDoctor {
        void onclick(String key, String message, String timestamp, String addedBy, String name, String Uri);

        void Uri(String key);
        void yourPost();
        void search(String search);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.yourpost, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MyPost:
                listenerDoc.yourPost();
                Toasty.normal(getActivity(), "My Post").show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
