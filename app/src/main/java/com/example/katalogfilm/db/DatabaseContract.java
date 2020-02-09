package com.example.katalogfilm.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.katalogfilm";
    private static final String SCHEME = "content";

    private DatabaseContract() {}

    public static final class BookmarkColumns implements BaseColumns {
        public static final String TABLE_NAME = "bookmark";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String TANGGAL_RILIS = "tanggalRilis";
        public static final String CYRCLE_IMAGE = "cyrcleImage";
        public static final String POSTER_IMAGE = "posterImage";
        public static final String CATEGORY = "category";

        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
