package com.example.matija.myapplication.Broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.matija.myapplication.Activity.LogInActivity;
import com.example.matija.myapplication.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by matija on 27.09.17..
 */

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int id = intent.getIntExtra("id", 0);
        int img = intent.getIntExtra("img", R.drawable.clipboard);

        makeNotification(title, text, id, img);
    }

    public void makeNotification(String title, String text, int id, int img){

        Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                img);

        Bitmap resizedIcon = Bitmap.createScaledBitmap(icon, 120, 120, true);
        long[] pattern = {0, 100, 1000};

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getContext(), LogInActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder = null;

        mBuilder = new NotificationCompat.Builder(getContext());
            mBuilder.setSmallIcon(R.drawable.notification)
                    .setContentTitle(title)
                    .setTicker(title)
                    .setContentText(text)
                    //.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(resizedIcon)
                    .setSound(alarmSound)
                    .setVibrate(pattern);



        NotificationManager mNotifyMgr =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, mBuilder.build());

    }


}
