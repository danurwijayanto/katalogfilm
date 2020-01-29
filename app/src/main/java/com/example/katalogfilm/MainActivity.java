package com.example.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.katalogfilm.db.BookmarkHelper;
import com.example.katalogfilm.entity.FilmParcelable;
import com.example.katalogfilm.package_bookmark.BookmarkActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView filmRecycle;
    private ArrayList<FilmParcelable> list = new ArrayList<>();
    private BookmarkHelper bookmarkHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookmarkHelper = bookmarkHelper.getInstance(getApplicationContext());
        bookmarkHelper.open();

        SectionsPagerAdapter sectionPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_change_settings:
//                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(mIntent);
//                break;
//            case R.id.show_bookmark:
////                Toast.makeText(this, "Show Bookmark", Toast.LENGTH_SHORT).show();
//                Intent moveIntent = new Intent(MainActivity.this, BookmarkActivity.class);
//                startActivity(moveIntent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookmarkHelper.close();
    }
}
