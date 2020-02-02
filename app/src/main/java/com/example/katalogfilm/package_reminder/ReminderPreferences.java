package com.example.katalogfilm.package_reminder;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.katalogfilm.entity.ReminderParcelable;

public class ReminderPreferences {

    private static final String PREFS_NAME = "reminder_pref";

    private static final String DAILY_REMINDER = "isdaily";
    private static final String RELEASE_REMINDER = "isrelease";

    private final SharedPreferences preferences;

    public ReminderPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setReminderPref(ReminderParcelable value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DAILY_REMINDER, value.isDailyReminder());
        editor.putBoolean(RELEASE_REMINDER, value.isReleaseReminder());
        editor.apply();
    }

    public ReminderParcelable getReminderPref() {
        ReminderParcelable model = new ReminderParcelable();
        model.setDailyReminder(preferences.getBoolean(DAILY_REMINDER, false));
        model.setReleaseReminder(preferences.getBoolean(RELEASE_REMINDER, false));
        return model;
    }
}
