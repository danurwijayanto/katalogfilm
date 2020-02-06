package com.example.katalogfilm.package_reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.katalogfilm.BuildConfig;
import com.example.katalogfilm.MainActivity;
import com.example.katalogfilm.R;
import com.example.katalogfilm.entity.FilmParcelable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String TYPE_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_RELEASE_REMINDER = "ReleaseReminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private final static String GROUP_KEY_REMINDER = "group_key_reminder";
    private ArrayList<FilmParcelable> stackNotif = new ArrayList<>();
    private int idNotification = 0;


    // Siapkan 2 id untuk 2 macam reminder, daily dan release
    private final int ID_DAILY = 100;
    private final int ID_RELEASE = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? TYPE_DAILY_REMINDER : TYPE_RELEASE_REMINDER;
        int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;
//        showToast(context, title, message);
        showReminderNotification(context, title, message, notifId);
    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    public void setDailyReminder(Context context, String type, String time, String message) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setReleaseReminder(Context context, String type, String time, String message) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showReminderNotification(final Context context, final String title, final String message, final int notifId) {
        final String CHANNEL_ID = "Channel_1";
        final String CHANNEL_NAME = "Reminder Manager channel";
        String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedTodayDate = simpleDateFormat.format(today);
        final String imageW92Url = "https://image.tmdb.org/t/p";

        Intent intentOpenCatalogApps = new Intent(context, MainActivity.class);
        final PendingIntent pendingIntentOpenCatalogApps = PendingIntent.getActivity(context, 0, intentOpenCatalogApps, 0);
        final NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder[] mBuilder = new NotificationCompat.Builder[1];
        Log.d("notifid", "showReminderNotification: "+notifId);
        if (notifId == 100) {

            mBuilder[0] = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(pendingIntentOpenCatalogApps)
                    .setSmallIcon(R.drawable.ic_notifications_active)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

                mBuilder[0].setChannelId(CHANNEL_ID);

                if (notificationManagerCompat != null) {
                    notificationManagerCompat.createNotificationChannel(channel);
                }
            }

            Notification notification = mBuilder[0].build();

            if (notificationManagerCompat != null) {
                notificationManagerCompat.notify(notifId, notification);
            }

        }else {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + formattedTodayDate + "&primary_release_date.lte=" + formattedTodayDate;

            AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");
                        Log.d("showReminder", "onSuccess: " + list.length());
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject film = list.getJSONObject(i);
                            FilmParcelable filmItems = new FilmParcelable();
                            filmItems.setId(Integer.valueOf(film.getString("id")));
                            filmItems.setCyrcleImage(imageW92Url + "/w92" + film.getString("poster_path"));
                            filmItems.setJudul(film.getString("title"));
                            filmItems.setPosterImage(imageW92Url + "/w185" + film.getString("poster_path"));
                            filmItems.setTanggalRilis(film.getString("release_date"));
                            filmItems.setDescription(film.getString("overview"));
                            stackNotif.add(filmItems);
                            idNotification++;
                        }

                        Log.d("showReminder", "showReminderNotification: "+idNotification);
                        if (idNotification < 2) {
                            mBuilder[0] = new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_notifications_active)
                                    .setContentTitle(stackNotif.size()+ " New Movies")
                                    .setContentText(message)
                                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                    .setSound(alarmSound)
                                    .setAutoCancel(true);
                        }else{
                            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                            for (FilmParcelable row : stackNotif) {
                                inboxStyle.addLine("New movie : " + row.getJudul());
                            }
                            inboxStyle.setBigContentTitle(context.getResources().getString(R.string.new_movies,stackNotif.size()))
                                    .setSummaryText(context.getResources().getString(R.string.reminder_release));

                            mBuilder[0] = new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setSmallIcon(R.drawable.ic_notifications_active)
                                    .setContentTitle(context.getResources().getString(R.string.new_movies,stackNotif.size()))
                                    .setContentText(message)
                                    .setGroup(GROUP_KEY_REMINDER)
                                    .setGroupSummary(true)
                                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                    .setSound(alarmSound)
                                    .setStyle(inboxStyle)
                                    .setAutoCancel(true);
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                                    CHANNEL_NAME,
                                    NotificationManager.IMPORTANCE_DEFAULT);

                            channel.enableVibration(true);
                            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

                            mBuilder[0].setChannelId(CHANNEL_ID);

                            if (notificationManagerCompat != null) {
                                notificationManagerCompat.createNotificationChannel(channel);
                            }
                        }

                        Notification notification = mBuilder[0].build();

                        if (notificationManagerCompat != null) {
                            notificationManagerCompat.notify(notifId, notification);
                        }


                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Exception", error.getMessage());
                }
            };

            client.get(url, responseHandler);
        }

    }

    public void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY : ID_RELEASE;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
