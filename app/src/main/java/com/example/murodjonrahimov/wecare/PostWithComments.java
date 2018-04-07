package com.example.murodjonrahimov.wecare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.controller.CommentsAdapter;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.example.murodjonrahimov.wecare.model.Post;
import com.example.murodjonrahimov.wecare.view.PatientPostsViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostWithComments extends AppCompatActivity {

  private EditText addedComment;
  private List<Comment> allComments;
  private String userName;
  private View contentView;
  private CardView cardView;
  private ImageView patientImage01;
  private ImageView patientImage02;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.post_with_comments);

    TextView message = findViewById(R.id.message_ed);
    TextView postedBy = findViewById(R.id.posted_by_ed);
    patientImage01 = findViewById(R.id.patient_post_image01);
    patientImage02 = findViewById(R.id.patient_post_image02);
    final TextView timestamp = findViewById(R.id.timestamp_ed);
    contentView=this.getWindow().getDecorView().findViewById(android.R.id.content);
    cardView= findViewById(R.id.cardview);

    checkUserProfile();

    Intent intent = getIntent();
    final Post post = intent.getParcelableExtra(PatientPostsViewHolder.POST_KEY);
    final String postKey = post.getKey();

    message.setText(post.getMessage());
    timestamp.setText(post.getTimeStamp());
    userName = post.getPostedByUserName();
    postedBy.setText("Posted by: " + userName);


    if (post.getUri() != null) {
      Uri uri = Uri.parse(post.getUri());

      loadingProfileImage(uri, "");
      patientImage01.setVisibility(View.VISIBLE);

    }

      addedComment = findViewById(R.id.adding_comment);
    ImageView sendComment = findViewById(R.id.send_image_view);

    final RecyclerView recyclerView = findViewById(R.id.comments_recyclerview);
    final CommentsAdapter commentsAdapter = new CommentsAdapter();
    LinearLayoutManager linearLayoutManager =
      new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    //linearLayoutManager.setStackFromEnd(true);

    recyclerView.setAdapter(commentsAdapter);
    recyclerView.setLayoutManager(linearLayoutManager);

    DatabaseReference db = Database.getDatabase();

    db.child("comments")
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

          allComments = new ArrayList<>();

          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
            Object postIdWrapper = dataSnapshot1.child("postId")
              .getValue();
            if (postIdWrapper != null) {
              String retrievedPostId = postIdWrapper.toString();
              if (retrievedPostId.equals(postKey)) {
                Comment comment = dataSnapshot1.getValue(Comment.class);
                allComments.add(comment);
              }
            }
          }
          commentsAdapter.setData(allComments);
          commentsAdapter.notifyDataSetChanged();
          recyclerView.scrollToPosition(commentsAdapter.getItemCount()-1);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

    contentView.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {

        Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        int screenHeight = contentView.getRootView().getHeight();

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        int keypadHeight = screenHeight - r.bottom;

        Log.d("DDDD", "keypadHeight = " + keypadHeight);

        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
          // keyboard is opened
          cardView.setVisibility(View.GONE);
        }
        else {
          cardView.setVisibility(View.VISIBLE);
        }
      }
    });


    sendComment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String receivedComment = addedComment.getText()
          .toString();
        if(receivedComment.length()<1){

        }

        else {
          long date = System.currentTimeMillis();
          SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
          String dateString = sdf.format(date);

          Comment comment = new Comment(receivedComment, postKey, dateString, userName);
          comment.setUid(Database.getUserId());
          Database.saveComment(comment);

          int count = post.getCountOfComments() + 1;
          post.setCountOfComments(count);
          Database.updatePost(post.getKey(), count);
          addedComment.getText()
                  .clear();
          recyclerView.scrollToPosition(commentsAdapter.getItemCount() - 1);

        }

      }
    });
  }

  public void checkUserProfile() {

    Database.getDatabase()
      .child("patients")
      .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          String id = Database.getUserId();

          for (DataSnapshot patient : dataSnapshot.getChildren()) {
            if (patient.getKey()
              .equals(id)) {
              SharedPreferences preferences =
                getSharedPreferences(RegistrationActivity.WE_CARE_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
              userName = preferences.getString(RegistrationActivity.USERNAME_KEY, "");
              return;
            }
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      });

    if (userName != null) {
      return;
    }

    Database.getDatabase()
      .child("doctors")
      .addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          String id = Database.getUserId();

          for (DataSnapshot doctor : dataSnapshot.getChildren()) {
            if (doctor.getKey()
              .equals(id)) {
              Doctor doctor1 = doctor.getValue(Doctor.class);
              userName = doctor1.getFirstName() + " " + doctor1.getLastName();
              return;
            }
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      });
  }
  private void loadingProfileImage(Uri downloadUri, String Lf) {
    Log.d("url", "loadingProfileImage: " + Lf + downloadUri);
    Picasso.get()
      .load(downloadUri)
      .into(patientImage01);
    //progressDialog.dismiss();
  }
}



