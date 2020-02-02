package com.example.katalogfilm.package_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.katalogfilm.R;
import com.example.katalogfilm.entity.ReminderParcelable;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch dailyReminder;
    Switch releaseReminder;
    private ReminderParcelable reminderParcelable;
    private ReminderPreferences mReminderPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        dailyReminder = (Switch) findViewById(R.id.daily_reminder);
        releaseReminder = (Switch) findViewById(R.id.release_reminder);

        String actionBarTitle = "Reminder Setting";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mReminderPreferences = new ReminderPreferences(this);
        showExistingPreference();

        dailyReminder.setOnCheckedChangeListener(this);
        releaseReminder.setOnCheckedChangeListener(this);
    }


    private void showExistingPreference() {
        reminderParcelable = mReminderPreferences.getReminderPref();
        populateView(reminderParcelable);
    }

    private void populateView(ReminderParcelable reminderParcelable) {
        dailyReminder.setChecked(reminderParcelable.isDailyReminder());
        releaseReminder.setChecked(reminderParcelable.isReleaseReminder());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        ReminderPreferences reminderPreferences = new ReminderPreferences(this);

        if (dailyReminder.isChecked()){
            reminderParcelable.setDailyReminder(true);
        }else{
            reminderParcelable.setDailyReminder(false);
        }

        if (releaseReminder.isChecked()){
            reminderParcelable.setReleaseReminder(true);
        }else{
            reminderParcelable.setReleaseReminder(false);
        }

        reminderPreferences.setReminderPref(reminderParcelable);
        Toast.makeText(ReminderActivity.this, getResources().getString(R.string.sukses_simpan_preferences), Toast.LENGTH_SHORT).show();
    }
}