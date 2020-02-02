package com.example.katalogfilm;


import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.katalogfilm.entity.FilmParcelable;
import com.example.katalogfilm.entity.ReminderParcelable;
import com.example.katalogfilm.package_bookmark.BookmarkActivity;
import com.example.katalogfilm.package_reminder.ReminderActivity;
import com.example.katalogfilm.package_reminder.ReminderPreferences;

import java.util.ArrayList;
import java.util.Locale;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView filmRecycle;
    private ProgressBar progressBar;
    private FilmViewModel mainViewModel;
    private BroadcastReceiver mLangReceiver;
    private FilmAdapterRecycle filmAdapterRecycle;

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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated (final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filmRecycle = view.findViewById(R.id.movie_list);
        filmRecycle.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progressBar);

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

        filmAdapterRecycle = new FilmAdapterRecycle();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // filter recycler view when query submitted
                    filmAdapterRecycle.getFilter().filter(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    filmAdapterRecycle.getFilter().filter(newText);
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.show_bookmark:
//                Toast.makeText(this, "Show Bookmark", Toast.LENGTH_SHORT).show();
                Intent moveIntent = new Intent(getActivity(), BookmarkActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.reminder:
                Intent moveIntentReminder = new Intent(getActivity(), ReminderActivity.class);
                startActivity(moveIntentReminder);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
