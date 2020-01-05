package com.example.katalogfilm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView filmRecycle;
    private ProgressBar progressBar;
    private FilmViewModel mainViewModel;
    private BroadcastReceiver mLangReceiver;

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
    public void onViewCreated (final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filmRecycle = view.findViewById(R.id.movie_list);
        filmRecycle.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progressBar);
        Log.d("LOG_BAHASA_0", "showRecyclerList: "+ Locale.getDefault().toLanguageTag());
        int index = 1;
        if (getArguments() != null) {
            index  = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        switch (index) {
            case 1:
                showRecyclerList("Movie", "en-US");
                break;
            case 2:
                showRecyclerList("TV", "en-US");
                break;
        }
    }

    private void showRecyclerList(String param, String language) {
        filmRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FilmAdapterRecycle filmAdapterRecycle = new FilmAdapterRecycle();
        filmAdapterRecycle.notifyDataSetChanged();
        filmRecycle.setAdapter(filmAdapterRecycle);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        mainViewModel.setData(param, language);

        showLoading(true);

        mainViewModel.getData().observe(this, new Observer<ArrayList<FilmParcelable>>() {
            @Override
            public void onChanged(ArrayList<FilmParcelable> filmItems) {
                if (filmItems != null) {
                    filmAdapterRecycle.setData(filmItems);
                    showLoading(false);
                }
            }
        });

        filmAdapterRecycle.setOnItemClickCallback(new FilmAdapterRecycle.OnItemClickCallback() {
            @Override
            public void onItemClicked(FilmParcelable data) {
                FilmParcelable filmItemParcel = new FilmParcelable();
                filmItemParcel.setCyrcleImage(data.getCyrcleImage());
                filmItemParcel.setJudul(data.getJudul());
                filmItemParcel.setDescription(data.getDescription());
                filmItemParcel.setTanggalRilis(data.getTanggalRilis());
                filmItemParcel.setPosterImage(data.getPosterImage());

                FilmDetailsFragment filmDetailsFragment = new FilmDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("EXTRA_FILM", filmItemParcel);
                filmDetailsFragment.setArguments(bundle);

                FragmentManager mFragmentManager = getFragmentManager();
                if (mFragmentManager != null) {
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, filmDetailsFragment, FilmDetailsFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
