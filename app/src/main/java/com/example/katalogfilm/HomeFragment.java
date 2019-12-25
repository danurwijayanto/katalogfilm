package com.example.katalogfilm;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView filmRecycle;
    private ArrayList<FilmParcelable> list = new ArrayList<>();

    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView textView = view.findViewById(R.id.section_label);

        int index = 1;
        if (getArguments() != null) {
            index  = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        switch (index) {
            case 1:
                filmRecycle = view.findViewById(R.id.movie_list);
                filmRecycle.setHasFixedSize(true);

                list.addAll(getList("Movie"));
                showRecyclerList();
                break;
            case 2:
                filmRecycle = view.findViewById(R.id.movie_list);
                filmRecycle.setHasFixedSize(true);

                list.addAll(getList("TV"));
                showRecyclerList();
                break;
        }

//        textView.setText(getString(R.string.content_tab_text) + " " + index);
    }

    public ArrayList<FilmParcelable> getList(String type) {
        String[] dataName;
        String[] dataDescription;
        TypedArray dataPhoto;
        String[] dataRilis;

        switch (type) {
            case "Movie":
                dataName = getResources().getStringArray(R.array.data_name);
                dataDescription = getResources().getStringArray(R.array.data_description);
                dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
                dataRilis = getResources().getStringArray(R.array.data_rilis);
                break;
            case "TV":
                dataName = getResources().getStringArray(R.array.data_name);
                dataDescription = getResources().getStringArray(R.array.data_description);
                dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
                dataRilis = getResources().getStringArray(R.array.data_rilis);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

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
        filmRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        FilmAdapterRecycle filmAdapterRecycle = new FilmAdapterRecycle(list);
        filmRecycle.setAdapter(filmAdapterRecycle);

        filmAdapterRecycle.setOnItemClickCallback(new FilmAdapterRecycle.OnItemClickCallback() {
            @Override
            public void onItemClicked(FilmParcelable data) {
//                Intent moveIntent = new Intent(MainActivity.this, DetailActivity.class);
//                moveIntent.putExtra(String.valueOf(DetailActivity.image_gamelan), data.getPhoto());
//                moveIntent.putExtra(DetailActivity.description, data.getDetail());
//                startActivity(moveIntent);
                Toast.makeText(getActivity(), "Kamu memilih " + data.getJudul(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
