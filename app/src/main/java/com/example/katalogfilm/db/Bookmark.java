package com.example.katalogfilm.db;

import android.provider.BaseColumns;

public class Bookmark {
    static String TABLE_NAME = "bookmark";

    public static final class BookmarkColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String TANGGAL_RILIS = "tanggalRilis";
        public static String CYRCLE_IMAGE = "cyrcleImage";
        public static String POSTER_IMAGE = "posterImage";
    }
}
