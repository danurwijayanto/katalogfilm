package com.example.katalogfilm.helper;

import android.database.Cursor;

import com.example.katalogfilm.db.Bookmark;
import com.example.katalogfilm.entity.FilmParcelable;
import com.example.katalogfilm.entity.Movie;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.DESCRIPTION));
            String release_date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TANGGAL_RILIS));
            String poster_image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.POSTER_IMAGE));
            String cyrcle_image = notesCursor.getString(notesCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.CYRCLE_IMAGE));
//            movies.add(new Movie(id, title, description, release_date, cyrcle_image, poster_image));
            movies.add(new Movie());

        }
        return movies;
    }
}
