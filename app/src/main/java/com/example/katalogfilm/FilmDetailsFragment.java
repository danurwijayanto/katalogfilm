package com.example.katalogfilm;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.katalogfilm.FilmDetailsActivity.EXTRA_FILM;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmDetailsFragment extends Fragment {
    FilmParcelable filmDetailParcel = new FilmParcelable();
    TextView detailsFilmDescription;
    TextView detailsRelease;
    TextView detailsJudul;
    ImageView detailsFilmImage;

    public FilmDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_film_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsFilmDescription = view.findViewById(R.id.film_description_details);
        detailsRelease = view.findViewById(R.id.film_rilis_details);
        detailsJudul = view.findViewById(R.id.film_judul_details);
        detailsFilmImage = view.findViewById(R.id.image_film);


    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            filmDetailParcel = bundle.getParcelable("EXTRA_FILM");
        }

//        FilmParcelable filmData = getActivity().getIntent().getParcelableExtra(EXTRA_FILM);

        String judulFilm = filmDetailParcel.getJudul();
        String descFilm = filmDetailParcel.getDescription();
        Integer imageFilm = filmDetailParcel.getFilm();
        String rilisFilm = filmDetailParcel.getTanggalRilis();

        detailsFilmDescription.setText(descFilm);
        detailsRelease.setText(rilisFilm);
        detailsJudul.setText(judulFilm);
        detailsFilmImage.setImageResource(imageFilm);
    }

}