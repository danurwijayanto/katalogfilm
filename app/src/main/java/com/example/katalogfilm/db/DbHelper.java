package com.example.katalogfilm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmoviecatalogapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            Bookmark.TABLE_NAME,
            Bookmark.BookmarkColumns._ID,
            Bookmark.BookmarkColumns.TITLE,
            Bookmark.BookmarkColumns.DESCRIPTION,
            Bookmark.BookmarkColumns.TANGGAL_RILIS,
            Bookmark.BookmarkColumns.POSTER_IMAGE,
            Bookmark.BookmarkColumns.CYRCLE_IMAGE,
            Bookmark.BookmarkColumns.CATEGORY
    );

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Bookmark.TABLE_NAME);
        onCreate(db);
    }
}
