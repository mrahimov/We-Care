package com.example.murodjonrahimov.wecare.service;

/**
 * Created by mohammadnaz on 3/21/18.
 */

import com.example.murodjonrahimov.wecare.DoctorActivity;
import com.example.murodjonrahimov.wecare.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    String notificationTitle = null, notificationBody = null;
    String dataMessage = null;

    if (remoteMessage.getData()
      .size() > 0) {
      dataMessage = remoteMessage.getData()
        .get("message");
    }

    if (remoteMessage.getNotification() != null) {
      notificationTitle = remoteMessage.getNotification()
        .getTitle();
      notificationBody = remoteMessage.getNotification()
        .getBody();
    }

    sendNotification(notificationTitle, dataMessage);
  }

  private void sendNotification(String notificationTitle, String dataMessage) {
    Intent intent = new Intent(this, DoctorActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder =
      (NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(notificationTitle)
        .setContentText(dataMessage)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }
}


