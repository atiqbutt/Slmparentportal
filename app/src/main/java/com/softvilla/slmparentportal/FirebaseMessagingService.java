package com.softvilla.slmparentportal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"));
        String topic = remoteMessage.getData().get("topic");
        String id = remoteMessage.getData().get("id");
        //Toast.makeText(this, topic + "    " + id, Toast.LENGTH_SHORT).show();
        List<Child> child = Child.listAll(Child.class);
        for(Child obj : child){
            if(obj.childIdentity.equalsIgnoreCase(id)){
                switch(topic){
                    case "attendance":
                        obj.attendance++;
                        obj.save();
                        break;
                    case "result":
                        obj.result++;
                        obj.save();
                        break;
                    case "fee":
                        obj.fee++;
                        obj.save();
                        break;
                    case "notice":
                        obj.notice++;
                        obj.save();
                        break;
                }
            }
        }
    }

    private void showNotification(String message) {
        Intent i = new Intent(this,Children.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long pattern[] = { 0, 100, 200, 300, 400 };
        vibrator.vibrate(pattern,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("SLM Parent Portal")
                .setContentText(message)
                .setSmallIcon(R.mipmap.logo)
                .setSound(soundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.logo))
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
        vibrator.cancel();
    }
}

