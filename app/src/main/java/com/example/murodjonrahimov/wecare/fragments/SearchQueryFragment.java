package com.example.murodjonrahimov.wecare.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by mohammadnaz on 4/5/18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

/**
 * Created by mohammadnaz on 4/5/18.
 */

public class SearchQueryFragment extends Fragment {
    private Query query;
    private View view;
    private FloatingActionButton floatingActionButton;
    private String user;
    private DatabaseReference database2;
    private FirebaseRecyclerOptions<DoctorPost> options;
    private RecyclerView recyclerView;
    private DoctorsForumFragment.onClickListenerDoctor listenerDoc;
    private FirebaseRecyclerAdapter<DoctorPost, SearchQueryFragment.SearchPost> fireBaseRecyclerAdapter;
    private String search;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listenerDoc = (DoctorsForumFragment.onClickListenerDoctor) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.d_fragment_doctors, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            search = bundle.getString("search1");

        }
        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("DoctorPost").orderByChild("firstname").equalTo(search);
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


        options = new FirebaseRecyclerOptions.Builder<DoctorPost>().setQuery(query, DoctorPost.class)
                .build();

        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<DoctorPost, SearchQueryFragment.SearchPost>(options) {

            @Override
            public SearchQueryFragment.SearchPost onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listdoctorpost_itemview, parent, false);

                return new SearchQueryFragment.SearchPost(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final SearchQueryFragment.SearchPost holder, int position,
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

    public class SearchPost extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView message;
        TextView time;
        TextView doctorName;
        Button button;
        ImageView imageView1;
        Button button1;


        public SearchPost(View itemView) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.allpost, menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AllPost:
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                fm.popBackStack ("search", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Toasty.normal(getActivity(), "All Post").show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}