package com.example.katalogfilm.package_bookmark;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.katalogfilm.package_bookmark.FilmViewModel;
import com.example.katalogfilm.entity.FilmParcelable;
import com.example.katalogfilm.db.BookmarkHelper;
import com.example.katalogfilm.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView filmRecycle;
    private ProgressBar progressBar;
    private BroadcastReceiver mLangReceiver;
    private FilmViewModel mainViewModel;

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
//        Log.d("LOG_BAHASA_0", "showRecyclerList: "+ Locale.getDefault().toLanguageTag());
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

    private void showRecyclerList(final String param, String language) {
        filmRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FilmAdapterRecycle filmAdapterRecycle = new FilmAdapterRecycle();
        filmAdapterRecycle.notifyDataSetChanged();
        filmRecycle.setAdapter(filmAdapterRecycle);

        // Ambil semua data di database

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);
        mainViewModel.setData(param, language);

        showLoading(true);

        mainViewModel.getData().observe(this, new Observer<ArrayList<FilmParcelable>>() {
            @Override
            public void onChanged(ArrayList<FilmParcelable> filmItems) {
                if (filmItems.size() <= 0) {
                    showSnackbarMessage("Tidak ada data saat ini");
                }

                if (filmItems != null) {
                    filmAdapterRecycle.setData(filmItems);
                    showLoading(false);
                }
            }
        });
//        BookmarkHelper bookmarkHelper = new BookmarkHelper(getActivity());
//        bookmarkHelper.open();
//        ArrayList<FilmParcelable> movies = bookmarkHelper.getAllDataByCategory(param);
//        if (movies.size() > 0) {
//            filmAdapterRecycle.setData(movies);
//        }else{
//            filmAdapterRecycle.setData(new ArrayList<FilmParcelable>());
//            showSnackbarMessage("Tidak ada data saat ini");
//        }
//        showLoading(true);
        filmAdapterRecycle.setOnItemClickCallback(new FilmAdapterRecycle.OnItemClickCallback() {
            @Override
            public void onItemClicked(FilmParcelable data) {
                FilmParcelable filmItemParcel = new FilmParcelable();
                filmItemParcel.setId(data.getId());
                filmItemParcel.setCyrcleImage(data.getCyrcleImage());
                filmItemParcel.setJudul(data.getJudul());
                filmItemParcel.setDescription(data.getDescription());
                filmItemParcel.setTanggalRilis(data.getTanggalRilis());
                filmItemParcel.setPosterImage(data.getPosterImage());
                filmItemParcel.setCategory(param);

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

    /**
     * Tampilkan snackbar
     *
     * @param message inputan message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(filmRecycle, message, Snackbar.LENGTH_SHORT).show();
    }
}