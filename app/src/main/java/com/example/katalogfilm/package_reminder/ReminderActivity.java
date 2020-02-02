package com.example.katalogfilm.package_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.katalogfilm.R;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch dailyReminder;
    Switch releaseReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        dailyReminder = (Switch) findViewById(R.id.daily_reminder);
        releaseReminder = (Switch) findViewById(R.id.release_reminder);

        dailyReminder.setOnCheckedChangeListener(this);
        releaseReminder.setOnCheckedChangeListener(this);

        String actionBarTitle = "Reminder Setting";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (dailyReminder.isChecked()){
            Toast.makeText(ReminderActivity.this, "Checked", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ReminderActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
        }

        if (releaseReminder.isChecked()){
            Toast.makeText(ReminderActivity.this, "Checked", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ReminderActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
        }
    }
}
