package com.example.katalogfilm.helper;

import android.database.Cursor;
import android.util.Log;

import com.example.katalogfilm.db.Bookmark;
import com.example.katalogfilm.entity.FilmParcelable;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<FilmParcelable> mapCursorToArrayList(Cursor filmCursor) {
        ArrayList<FilmParcelable> movies = new ArrayList<>();

        while (filmCursor.moveToNext()) {
            int id = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns._ID));
            String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TITLE));
            String description = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.DESCRIPTION));
            String release_date = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TANGGAL_RILIS));
            String poster_image = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.POSTER_IMAGE));
            String cyrcle_image = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.CYRCLE_IMAGE));
            movies.add(new FilmParcelable(id, title, description, release_date, cyrcle_image, poster_image));
//            movies.add(new FilmParcelable());
        }
        return movies;
    }

    public static FilmParcelable mapCursorToObject(Cursor filmCursor) {
        filmCursor.moveToFirst();
        int id = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns._ID));
        String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TITLE));
        String description = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.DESCRIPTION));
        String release_date = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.TANGGAL_RILIS));
        String poster_image = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.POSTER_IMAGE));
        String cyrcle_image = filmCursor.getString(filmCursor.getColumnIndexOrThrow(Bookmark.BookmarkColumns.CYRCLE_IMAGE));

        return new FilmParcelable(id, title, description, release_date, poster_image, cyrcle_image);
    }
}
