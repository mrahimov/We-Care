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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.R;
import com.example.murodjonrahimov.wecare.controller.AllPostsAdapter;
import com.example.murodjonrahimov.wecare.controller.NavigationAdapter;
import com.example.murodjonrahimov.wecare.controller.NavigationDoctorAdapter;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.listeners.CategoryPills;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.DoctorPost;
import com.example.murodjonrahimov.wecare.model.Post;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorsForumFragment extends Fragment implements CategoryPills {

  private View view;
  private FloatingActionButton floatingActionButton;
  private DatabaseReference database;
  private DatabaseReference database2;
  private Doctor doctor2;
  private String user;
  private String name;
  private List<String> catigoryList = new ArrayList<>();
  private RecyclerView recyclerViewPills;
  private NavigationDoctorAdapter adapterPills;

  private RecyclerView recyclerView;
  private onClickListenerDoctor listenerDoc;
  private FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;
  private View chooseDoctor;
  private TextView textViewGP;
  private TextView textViewAllergist;
  private TextView textViewCardiologist;
  private TextView textViewDermatologist;
  private TextView textViewNephrologist;
  private TextView textViewNeurologist;
  private TextView textViewObstetrician;
  private TextView textViewOphthalmologist;
  private TextView textViewOtolaryngologist;
  private TextView textViewPediatrician;
  private TextView textViewPsychiatrist;
  private TextView textViewRheumatologist;
  private TextView textViewUrologist;
  private TextView textViewGastroenterologist;
  private TextView textViewOther;
  private TextView textviewChooseDoctor;
  private String doctorINeed;
  private String key;

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
      .child("Doctor")
      .child(user);

    database.keepSynced(true);
    FirebaseMessaging.getInstance()
      .subscribeToTopic("notifications");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.d_fragment_doctors, container, false);

    recyclerViewPills = view.findViewById(R.id.doctors_navigation_pills);
    recyclerViewPills.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    adapterPills = new NavigationDoctorAdapter(this);
    recyclerViewPills.setAdapter(adapterPills);

    catigoryList.add("All Post's");
    catigoryList.add("Allergist");
    catigoryList.add("Cardiologist");
    catigoryList.add("Dermatologist");
    catigoryList.add("GP");
    catigoryList.add("Gastroenterologist");
    catigoryList.add("Nephrologist");
    catigoryList.add("Neurologist");
    catigoryList.add("Obstetrician");
    catigoryList.add("Ophthalmologist");
    catigoryList.add("Other");
    catigoryList.add("Otolaryngologist");
    catigoryList.add("Pediatrician");
    catigoryList.add("Psychiatrist");
    catigoryList.add("Rheumatologist");
    catigoryList.add("Urologist");

    adapterPills.setCategoryList(catigoryList);

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

        database2 = FirebaseDatabase.getInstance()
          .getReference()
          .child("doctors")
          .child(doctor.getAddedBy());

        database2.getRef()
          .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              doctor2 = dataSnapshot.getValue(Doctor.class);
              name = doctor2.getFirstName() + " " + doctor2.getLastName();
              holder.doctorName.setText(name);
              holder.message.setText(doctor.getMessage());
              holder.time.setText(doctor.getTimeStamp());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
          });

         key = fireBaseRecyclerAdapter.getRef(position)
          .getKey();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            listenerDoc.onclick(key, doctor.getMessage(), doctor.getTimeStamp(), doctor.getAddedBy(), name);
          }
        });
      }
    };
    recyclerView.setAdapter(fireBaseRecyclerAdapter);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add post");

        final View viewInflated = LayoutInflater.from(getContext())
          .inflate(R.layout.alertbox, (ViewGroup) getView(), false);
        final EditText input1 = viewInflated.findViewById(R.id.input1);

        chooseDoctor = viewInflated.findViewById(R.id.linearlayout_question_related);
        textViewGP = viewInflated.findViewById(R.id.doctor_post_gp);
        textViewAllergist = viewInflated.findViewById(R.id.doctor_post_allergist);
        textViewCardiologist = viewInflated.findViewById(R.id.doctor_post_cardiologist);
        textViewDermatologist = viewInflated.findViewById(R.id.doctor_post_dermatologist);
        textViewNephrologist = viewInflated.findViewById(R.id.doctor_post_nephrologist);
        textViewNeurologist = viewInflated.findViewById(R.id.doctor_post_neurologist);
        textViewObstetrician = viewInflated.findViewById(R.id.doctor_post_obstetrician);
        textViewOphthalmologist = viewInflated.findViewById(R.id.doctor_post_ophthalmologist);
        textViewOtolaryngologist = viewInflated.findViewById(R.id.doctor_post_otolaryngologist);
        textViewPediatrician = viewInflated.findViewById(R.id.doctor_post_pediatrician);
        textViewPsychiatrist = viewInflated.findViewById(R.id.doctor_post_psychiatrist);
        textViewRheumatologist = viewInflated.findViewById(R.id.doctor_post_rheumatologist);
        textViewUrologist = viewInflated.findViewById(R.id.doctor_post_urologist);
        textViewGastroenterologist = viewInflated.findViewById(R.id.doctor_post_gastroenterologist);
        textViewOther = viewInflated.findViewById(R.id.doctor_post_other);
        textviewChooseDoctor = viewInflated.findViewById(R.id.choose_doctor_textview);


        chooseDoctor.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            if (textViewAllergist.getVisibility() == View.GONE) {

              textViewAllergist.setVisibility(View.VISIBLE);
              textViewCardiologist.setVisibility(View.VISIBLE);
              textViewDermatologist.setVisibility(View.VISIBLE);
              textViewNephrologist.setVisibility(View.VISIBLE);
              textViewObstetrician.setVisibility(View.VISIBLE);
              textViewOphthalmologist.setVisibility(View.VISIBLE);
              textViewOtolaryngologist.setVisibility(View.VISIBLE);
              textViewPediatrician.setVisibility(View.VISIBLE);
              textViewPsychiatrist.setVisibility(View.VISIBLE);
              textViewRheumatologist.setVisibility(View.VISIBLE);
              textViewUrologist.setVisibility(View.VISIBLE);
              textViewGastroenterologist.setVisibility(View.VISIBLE);
              textViewNeurologist.setVisibility(View.VISIBLE);
              textViewOther.setVisibility(View.VISIBLE);
              textViewGP.setVisibility(View.VISIBLE);

            } else {

              makeTextGone();

            }
          }
        });


        textViewGP.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "GP";
            textviewChooseDoctor.setText(R.string.gp);
            makeTextGone();
          }
        });

        textViewAllergist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Allergist";
            textviewChooseDoctor.setText(R.string.allergist);
            makeTextGone();
          }
        });

        textViewCardiologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Cardiologist";
            makeTextGone();
          }
        });

        textViewDermatologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Dermatologist";
            textviewChooseDoctor.setText(R.string.dermatologist);
            makeTextGone();
          }
        });

        textViewNephrologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Nephrologist";
            textviewChooseDoctor.setText(R.string.nephrologist);
            makeTextGone();
          }
        });

        textViewObstetrician.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Obstetrician/Gynecologist";
            textviewChooseDoctor.setText(R.string.obstetrician_gynecologist);
            makeTextGone();
          }
        });

        textViewOphthalmologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Ophthalmologist";
            textviewChooseDoctor.setText(R.string.ophthalmologist);
            makeTextGone();
          }
        });

        textViewOtolaryngologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Otolaryngologist";
            textviewChooseDoctor.setText(R.string.otolaryngologist);
            makeTextGone();
          }
        });

        textViewPediatrician.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Pediatrician";
            textviewChooseDoctor.setText(R.string.pediatrician);
            makeTextGone();
          }
        });

        textViewPsychiatrist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Psychiatrist";
            textviewChooseDoctor.setText(R.string.psychiatrist);
            makeTextGone();
          }
        });

        textViewRheumatologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Rheumatologist";
            textviewChooseDoctor.setText(R.string.rheumatologist);
            makeTextGone();
          }
        });

        textViewUrologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Urologist";
            textviewChooseDoctor.setText(R.string.urologist);
            makeTextGone();
          }
        });

        textViewGastroenterologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Gastroenterologist";
            textviewChooseDoctor.setText(R.string.gastroenterologist);
            makeTextGone();
          }
        });

        textViewNeurologist.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Neurologist";
            textviewChooseDoctor.setText(R.string.neurologist);
            makeTextGone();
          }
        });

        textViewOther.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            doctorINeed = "Other";
            textviewChooseDoctor.setText(R.string.other);
            makeTextGone();
          }
        });


        builder.setView(viewInflated);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        final String format = simpleDateFormat.format(new Date());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

          String message = input1.getText()
            .toString();

          @Override
          public void onClick(DialogInterface dialog, int which) {
            final DoctorPost doctorPost = new DoctorPost(message, user, format);
            Database.saveDoctorPost(doctorPost);

            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
            String dateString = sdf.format(date);

            if (doctorINeed == null) {
              doctorINeed = "Others";
            }

            DoctorPost post = new DoctorPost(message, dateString, name, doctorINeed);
            Database.saveDoctorPost(post);

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

  @Override
  public void onCategoryListener(String category) {

  }

  public static class DoctorPosts extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    TextView message;
    TextView time;
    TextView doctorName;
    Button button;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter;
    String name;

    public DoctorPosts(View itemView,
                       FirebaseRecyclerAdapter<DoctorPost, DoctorsForumFragment.DoctorPosts> fireBaseRecyclerAdapter) {
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
        .child("doctors")
        .child(user);

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
        if (name.equals(doctorName.getText()
          .toString())) {
          fireBaseRecyclerAdapter.getRef(getAdapterPosition())
            .removeValue();
        }
      }
    }

    @Override
    public boolean onLongClick(View v) {
      if (v.getId() == itemView.getId()) {
        if (name.equals(doctorName.getText()
          .toString())) {
          button.setVisibility(View.VISIBLE);
          return true;
        }
      }
      return false;
    }
  }

  public interface onClickListenerDoctor {
    void onclick(String key, String message, String timestamp, String addedBy, String name);
  }

  public void makeTextGone() {
    textViewAllergist.setVisibility(View.GONE);
    textViewCardiologist.setVisibility(View.GONE);
    textViewDermatologist.setVisibility(View.GONE);
    textViewNephrologist.setVisibility(View.GONE);
    textViewObstetrician.setVisibility(View.GONE);
    textViewOphthalmologist.setVisibility(View.GONE);
    textViewOtolaryngologist.setVisibility(View.GONE);
    textViewPediatrician.setVisibility(View.GONE);
    textViewPsychiatrist.setVisibility(View.GONE);
    textViewRheumatologist.setVisibility(View.GONE);
    textViewUrologist.setVisibility(View.GONE);
    textViewGastroenterologist.setVisibility(View.GONE);
    textViewNeurologist.setVisibility(View.GONE);
    textViewOther.setVisibility(View.GONE);
    textViewGP.setVisibility(View.GONE);
  }
}
