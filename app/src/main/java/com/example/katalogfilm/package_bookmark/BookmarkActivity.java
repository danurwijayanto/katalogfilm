package com.example.katalogfilm.package_bookmark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.katalogfilm.R;
import com.example.katalogfilm.entity.FilmParcelable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView filmRecycle;
    private ArrayList<FilmParcelable> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String actionBarTitle = "Bookmark List";
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }
}
