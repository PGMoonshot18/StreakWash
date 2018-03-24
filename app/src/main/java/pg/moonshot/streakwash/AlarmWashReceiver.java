package pg.moonshot.streakwash;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Angel on 23/03/2018.
 */

public class AlarmWashReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent streakActivity = new Intent(context, StreakActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context,
                StreakActivity.PENDINGINTENT_WASH, streakActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder
                = (NotificationCompat.Builder) new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.icon)
                .setTicker("Please wash your hands!")
                .setContentTitle("Have you already washed your hands?")
                .setContentText("Click here to continue your wash streak!")
                .setContentIntent(saPI)
                .setAutoCancel(true);
        notificationManager.notify(StreakActivity.NOTIF_WASH, builder.build());

        AlarmManager alarmManager
                = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        Intent broadcastIntent = new Intent(context, AlarmWashReceiver.class);
        PendingIntent bcPI
                = PendingIntent.getBroadcast(context,
                StreakActivity.PENDINGINTENT_WASH, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 2*1000*60,
                bcPI);

    }
}
