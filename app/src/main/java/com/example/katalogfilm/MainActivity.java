package com.example.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView filmRecycle;
    private ArrayList<FilmParcelable> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycle);

        filmRecycle = findViewById(R.id.movie_list);
        filmRecycle.setHasFixedSize(true);

        list.addAll(getListFilms());
        showRecyclerList();

//        ListView listView = findViewById(R.id.lv_list);
//        adapter = new FilmAdapter(this);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
//                FilmParcelable filmItemParcel = new FilmParcelable();
//                filmItemParcel.setFilm(filmss.get(i).getFilm());
//                filmItemParcel.setJudul(filmss.get(i).getJudul());
//                filmItemParcel.setDescription(filmss.get(i).getDescription());
//                filmItemParcel.setTanggalRilis(filmss.get(i).getTanggalRilis());
//////
//                Intent filmDetailsActivity = new Intent(MainActivity.this, FilmDetailsActivity.class);
//                filmDetailsActivity.putExtra(FilmDetailsActivity.EXTRA_FILM, filmItemParcel);
//                startActivity(filmDetailsActivity);
////                Toast.makeText(MainActivity.this, filmss.get(i).getJudul(), Toast.LENGTH_SHORT).show();
//            }
//        });
////        break;
    }


    public ArrayList<FilmParcelable> getListFilms() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
        String[] dataRilis = getResources().getStringArray(R.array.data_rilis);

        ArrayList<FilmParcelable> listFilm = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            FilmParcelable film = new FilmParcelable();
            film.setFilm(dataPhoto.getResourceId(i, -1));
            film.setJudul(dataName[i]);
            film.setDescription(dataDescription[i]);
            film.setTanggalRilis(dataRilis[i]);

            listFilm.add(film);
        }
        return listFilm;
    }

    private void showRecyclerList() {
        filmRecycle.setLayoutManager(new LinearLayoutManager(this));
        FilmAdapterRecycle filmAdapterRecycle = new FilmAdapterRecycle(list);
        filmRecycle.setAdapter(filmAdapterRecycle);
    }
}
