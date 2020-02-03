package com.example.katalogfilm.package_bookmark;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.katalogfilm.db.BookmarkHelper;
import com.example.katalogfilm.entity.FilmParcelable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FilmViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FilmParcelable>> listItems = new MutableLiveData<>();

    void setData(final String param, String language) {
        ArrayList<FilmParcelable> movies = BookmarkHelper.getAllDataByCategory(param);
        listItems.postValue(movies);

    }

    LiveData<ArrayList<FilmParcelable>> getData() {
        return listItems;
    }

    void updateData(final String param, String language) {
        ArrayList<FilmParcelable> movies = BookmarkHelper.getAllDataByCategory(param);
        listItems.postValue(movies);
    }
}
