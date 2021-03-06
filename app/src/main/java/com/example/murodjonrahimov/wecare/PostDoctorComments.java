package com.example.murodjonrahimov.wecare;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.murodjonrahimov.wecare.controller.CommentsAdapter;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PostDoctorComments extends AppCompatActivity {

  private String doctorMessage;
  private String doctorTimeStamp;
  private String name;
  private EditText addedComment;
  private List<Comment> allComments;
  private DatabaseReference database10;
  private ImageView imageView;
  private View contentView;
  private CardView cardView;

  private String commentname;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_doctor_comments);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    TextView message = findViewById(R.id.message_d);
    TextView addedBy = findViewById(R.id.added_by_d);
    TextView timestamp = findViewById(R.id.timestamp_d);
    imageView = findViewById(R.id.image3);
    contentView = this.getWindow()
      .getDecorView()
      .findViewById(android.R.id.content);
    cardView = findViewById(R.id.cardview1);

    Intent intent = getIntent();
    final String Key = intent.getStringExtra("key");
    doctorTimeStamp = intent.getStringExtra("timestamp");
    name = intent.getStringExtra("addedby");
    doctorMessage = intent.getStringExtra("message");
    String url = intent.getStringExtra("uri");

    if (url == null) {
      imageView.setVisibility(View.GONE);
    } else {
      Picasso.get()
        .load(url)
        .into(imageView);
    }

    ImageView sendComment = findViewById(R.id.send_imageview);

    addedComment = findViewById(R.id.addingcomment);
    message.setText(doctorMessage);
    addedBy.setText(name);
    timestamp.setText(doctorTimeStamp);

    final RecyclerView recyclerView = findViewById(R.id.commentsrecyclerview);
    final CommentsAdapter commentsAdapter = new CommentsAdapter();
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    recyclerView.setAdapter(commentsAdapter);
    recyclerView.setLayoutManager(linearLayoutManager);
    String user = Database.getUserId();
    database10 = FirebaseDatabase.getInstance()
      .getReference()
      .child("doctors")
      .child(user);

    database10.getRef()
      .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          if (dataSnapshot.exists()) {
            Doctor doctor = dataSnapshot.getValue(Doctor.class);
            commentname = doctor.getFirstName() + " " + doctor.getLastName();
          } else {
            commentname = "doctor";
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

    contentView.getRootView()
      .getViewTreeObserver()
      .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

          Rect r = new Rect();
          contentView.getWindowVisibleDisplayFrame(r);
          int screenHeight = contentView.getRootView()
            .getHeight();

          // r.bottom is the position above soft keypad or device button.
          // if keypad is shown, the r.bottom is smaller than that before.
          int keypadHeight = screenHeight - r.bottom;

          Log.d("DDDD", "keypadHeight = " + keypadHeight);

          if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
            // keyboard is opened
            cardView.setVisibility(View.GONE);
          } else {
            cardView.setVisibility(View.VISIBLE);
          }
        }
      });

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
              if (retrievedPostId.equals(Key)) {
                Comment comment = dataSnapshot1.getValue(Comment.class);
                allComments.add(comment);
              }
            }
          }
          commentsAdapter.setData(allComments);
          commentsAdapter.notifyDataSetChanged();
          recyclerView.scrollToPosition(commentsAdapter.getItemCount() - 1);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });

    sendComment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String receivedComment = addedComment.getText()
          .toString();
        if (receivedComment.length() < 1) {

        } else {
          long date = System.currentTimeMillis();
          SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
          String dateString = sdf.format(date);

          Comment comment = new Comment(receivedComment, Key, dateString, commentname);
          comment.setUid(Database.getUserId());
          Database.saveComment(comment);

          addedComment.getText()
            .clear();
        }
      }
    });
  }
}



