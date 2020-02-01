package com.example.katalogfilm.package_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.katalogfilm.R;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String actionBarTitle = "Reminder Setting";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_reminder);
    }
}
