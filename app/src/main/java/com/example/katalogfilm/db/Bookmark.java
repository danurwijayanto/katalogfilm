package com.example.katalogfilm.db;

import android.provider.BaseColumns;

public class Bookmark {
    static String TABLE_NAME = "bookmark";

    static final class BookmarkColumns implements BaseColumns {
        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String TANGGAL_RILIS = "tanggalRilis";
        static String CYRCLE_IMAGE = "cyrcleImage";
        static String POSTER_IMAGE = "posterImage";
    }
}
