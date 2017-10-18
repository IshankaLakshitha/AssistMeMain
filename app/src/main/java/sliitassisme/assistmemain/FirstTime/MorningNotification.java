package sliitassisme.assistmemain.FirstTime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.TextView;

import sliitassisme.assistmemain.PopUpApp;
import sliitassisme.assistmemain.R;

public class MorningNotification extends BroadcastReceiver {

    private String myAppId = "dcb6553bfccc040683d9917eedd6cfbe";

    TextView aa;

    //Weather weather = new Weather();

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, PopUpApp.class);
        Log.v("dd","Alarm reciver Done");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(PopUpApp.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("AssistMe")
                .setContentText("Hey Brian, Check these out!")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
