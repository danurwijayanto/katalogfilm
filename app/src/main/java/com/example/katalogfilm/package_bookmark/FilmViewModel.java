package com.example.katalogfilm.package_bookmark;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.katalogfilm.db.BookmarkHelper;
import com.example.katalogfilm.db.DatabaseContract;
import com.example.katalogfilm.entity.FilmParcelable;
import com.example.katalogfilm.helper.MappingHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.katalogfilm.db.DatabaseContract.BookmarkColumns.CONTENT_URI;

public class FilmViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FilmParcelable>> listItems = new MutableLiveData<>();

    void setData(final String param, String language, Context context) {
        Uri uriWithparam = Uri.parse(CONTENT_URI + "/getdatabbybookmarktype/" + param);

        Cursor dataCursor = context.getContentResolver().query(uriWithparam, null, null, null, null);
        ArrayList<FilmParcelable> movies =  MappingHelper.mapCursorToArrayList(dataCursor);

//        ArrayList<FilmParcelable> movies = BookmarkHelper.getAllDataByCategory(param);
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
