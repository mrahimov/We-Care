package com.example.murodjonrahimov.wecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murodjonrahimov.wecare.controller.CommentsAdapter;
import com.example.murodjonrahimov.wecare.database.Database;
import com.example.murodjonrahimov.wecare.model.Comment;
import com.example.murodjonrahimov.wecare.model.Post;
import com.example.murodjonrahimov.wecare.view.PatientPostsViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostWithComments extends AppCompatActivity {

    private EditText addedComment;
    private List<Comment> allComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_with_comments);

        TextView message = findViewById(R.id.message_ed);
        TextView addedBy = findViewById(R.id.added_by_ed);
        TextView timestamp = findViewById(R.id.timestamp_ed);

        Intent intent = getIntent();
        final Post post = intent.getParcelableExtra(PatientPostsViewHolder.POST_KEY);
        final String postKey = post.getKey();

        message.setText("Message:" + post.getMessage());
        addedBy.setText("Added By: " + post.getAddedBy());
        timestamp.setText("Date: " + post.getTimeStamp());

        addedComment = findViewById(R.id.adding_comment);
        ImageView sendComment = findViewById(R.id.send_image_view);


        RecyclerView recyclerView = findViewById(R.id.comments_recyclerview);
        final CommentsAdapter commentsAdapter = new CommentsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference db = Database.getDatabase();

        db.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allComments = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String retrievedPostKeydataSnapshot1 = dataSnapshot1.child("postId").getValue().toString();
                    if (retrievedPostKeydataSnapshot1.equals(postKey)) {
                        Comment comment = dataSnapshot1.getValue(Comment.class);
                        allComments.add(comment);
                    }
                }
                commentsAdapter.setData(allComments);
                commentsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String receivedComment = addedComment.getText().toString();
                Comment comment = new Comment(receivedComment, postKey);
                Database.saveComment(comment);
                addedComment.getText().clear();

            }
        });


    }


}


