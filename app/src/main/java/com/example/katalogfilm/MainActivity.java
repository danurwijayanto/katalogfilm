package com.example.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private String[] dataName = {"Cut Nyak Dien","Ki Hajar Dewantara","Moh Yamin","Patimura","R A Kartini","Sukarno"};
    private FilmAdapter adapter;
    private String[] dataName;
    private String[] dataDescription;
    private String[] dataRilis;
    private TypedArray dataPhoto;
    private ArrayList<FilmParcelable> filmss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lv_list);
        adapter = new FilmAdapter(this);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                FilmParcelable filmItemParcel = new FilmParcelable();
                filmItemParcel.setFilm(filmss.get(i).getFilm());
                filmItemParcel.setJudul(filmss.get(i).getJudul());
                filmItemParcel.setDescription(filmss.get(i).getDescription());
                filmItemParcel.setTanggalRilis(filmss.get(i).getTanggalRilis());
////
                Intent filmDetailsActivity = new Intent(MainActivity.this, FilmDetailsActivity.class);
                filmDetailsActivity.putExtra(FilmDetailsActivity.EXTRA_FILM, filmItemParcel);
                startActivity(filmDetailsActivity);
//                Toast.makeText(MainActivity.this, filmss.get(i).getJudul(), Toast.LENGTH_SHORT).show();
            }
        });
//        break;
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
        dataRilis = getResources().getStringArray(R.array.data_rilis);
    }

    private void addItem() {
        filmss = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            FilmParcelable filmParcel = new FilmParcelable();
            filmParcel.setFilm(dataPhoto.getResourceId(i, -1));
            filmParcel.setJudul(dataName[i]);
            filmParcel.setDescription(dataDescription[i]);
            filmParcel.setTanggalRilis(dataRilis[i]);

//            film.setFilm(dataPhoto.getResourceId(i, -1));
//            film.setJudul(dataName[i]);
//            film.setDescription(dataDescription[i]);
            filmss.add(filmParcel);
        }
        adapter.setFilms(filmss);
    }
}
