package com.example.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FilmDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_details);

        TextView detailsFilmDescription = findViewById(R.id.film_description_details);
        TextView detailsRelease = findViewById(R.id.film_rilis_details);
        TextView detailsJudul = findViewById(R.id.film_judul_details);
        ImageView detailsFilmImage = findViewById(R.id.film_image_details);

        FilmParcelable filmData = getIntent().getParcelableExtra(EXTRA_FILM);

        String judulFilm = filmData.getJudul();
        String descFilm = filmData.getDescription();
        Integer imageFilm = filmData.getFilm();
        String rilisFilm = filmData.getTanggalRilis();

//        String descFull = "Judul : " + judulFilm + "\n\nTahun Keluar : " + rilisFilm + "\n\nDescripsi : " + descFilm;

//        detailsFilmDescription.setText(descFull);
        detailsFilmDescription.setText(descFilm);
        detailsRelease.setText(rilisFilm);
        detailsJudul.setText(judulFilm);
        detailsFilmImage.setImageResource(imageFilm);


//        Log.d("myTag", desc);
    }
}
