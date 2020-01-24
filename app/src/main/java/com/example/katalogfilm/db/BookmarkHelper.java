package com.example.katalogfilm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.katalogfilm.entity.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.CATEGORY;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.CYRCLE_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.DESCRIPTION;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.POSTER_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TANGGAL_RILIS;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TITLE;
import static com.example.katalogfilm.db.Bookmark.TABLE_NAME;

public class BookmarkHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DbHelper dataBaseHelper;
    private static BookmarkHelper INSTANCE;
    private static SQLiteDatabase database;

    public BookmarkHelper(Context context) {
        dataBaseHelper = new DbHelper(context);
    }

    public static BookmarkHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookmarkHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public static long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

    public static int deleteByJudul(String judul) {
        return database.delete(DATABASE_TABLE, TITLE + " = ?", new String[]{judul});
    }

    public ArrayList<Movie> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL_RILIS)));
                movie.setPosterImage(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_IMAGE)));
                movie.setCyrcleImage(cursor.getString(cursor.getColumnIndexOrThrow(CYRCLE_IMAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Movie> getAllDataByCategory(String category) {
        Cursor cursor = database.query(
                TABLE_NAME,
                null,
                CATEGORY + " = ?",
                new String[]{category},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL_RILIS)));
                movie.setPosterImage(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_IMAGE)));
                movie.setCyrcleImage(cursor.getString(cursor.getColumnIndexOrThrow(CYRCLE_IMAGE)));
                movie.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
}
