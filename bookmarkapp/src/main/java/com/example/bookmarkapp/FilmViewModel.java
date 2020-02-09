package com.example.bookmarkapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookmarkapp.entity.FilmParcelable;
import com.example.bookmarkapp.helper.MappingHelper;

import java.util.ArrayList;

import static com.example.bookmarkapp.db.DatabaseContract.BookmarkColumns.CONTENT_URI;

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
}
