package com.example.katalogfilm.package_bookmark;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.katalogfilm.R;
import com.example.katalogfilm.db.BookmarkHelper;
import com.example.katalogfilm.entity.Movie;

import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.CYRCLE_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.DESCRIPTION;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.POSTER_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TANGGAL_RILIS;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TITLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmDetailsFragment extends Fragment {
    private Movie filmDetailParcel = new Movie();
    private TextView detailsFilmDescription;
    private TextView detailsRelease;
    private TextView detailsJudul;
    private ImageView detailsFilmImage;
    private String judulFilm;
    private String descFilm;
    private String imageFilm;
    private String rilisFilm;
    private MenuItem removeBookmark;
    private MenuItem doBookmark;

    public FilmDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.menu_details,menu);
        removeBookmark = menu.findItem(R.id.remove_bookmark);
        doBookmark = menu.findItem(R.id.add_bookmark);

        removeBookmark.setVisible(true);
        doBookmark.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_bookmark:
                ContentValues values = new ContentValues();
                values.put(TITLE, judulFilm);
                values.put(DESCRIPTION, descFilm);
                values.put(TANGGAL_RILIS,rilisFilm);
                values.put(CYRCLE_IMAGE,imageFilm);
                values.put(POSTER_IMAGE,imageFilm);
//                Log.d("JUDUL", "onOptionsItemSelected: "+judulFilm);
                long result = BookmarkHelper.insert(values);
                if (result > 0) {
                    Toast.makeText(getContext(), "Suksess menambah data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
                removeBookmark.setVisible(false);
                doBookmark.setVisible(true);
                break;
            case R.id.remove_bookmark:

                long resultDelete = BookmarkHelper.deleteByJudul(String.valueOf(judulFilm));
                if (resultDelete > 0) {
                    Toast.makeText(getContext(), "Suksess menghapus data", Toast.LENGTH_SHORT).show();
                    getActivity().getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }

                removeBookmark.setVisible(false);
                doBookmark.setVisible(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_film_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

        judulFilm = filmDetailParcel.getJudul();
        descFilm = filmDetailParcel.getDescription();
        imageFilm = filmDetailParcel.getPosterImage();
        rilisFilm = filmDetailParcel.getTanggalRilis();

        detailsFilmDescription.setText(descFilm);
        detailsRelease.setText(rilisFilm);
        detailsJudul.setText(judulFilm);

        Glide.with(this)
                .load(imageFilm)
                .into(detailsFilmImage);
    }

}