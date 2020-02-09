package com.example.katalogfilm.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.katalogfilm.db.BookmarkHelper;

import static com.example.katalogfilm.db.DatabaseContract.AUTHORITY;
import static com.example.katalogfilm.db.DatabaseContract.BookmarkColumns.CONTENT_URI;
import static com.example.katalogfilm.db.DatabaseContract.BookmarkColumns.TABLE_NAME;

public class BookmarkProvider extends ContentProvider {
    private static final int BOOKMARK = 1;
    private static final int BOOKMARK_ID = 2;
    private static final int TITLE = 3;
    private static final int BOOKMARK_TYPE = 4;
    private static final int DELETE_BY_TITLE = 5;

    private BookmarkHelper bookmarkHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.picodiploma.mynotesapp/bookmark
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, BOOKMARK);
        // content://com.dicoding.picodiploma.mynotesapp/bookmark/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                BOOKMARK_ID);
        // content://com.dicoding.picodiploma.mynotesapp/bookmark/deletebytitle/judul
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/deletebytitle/#", TITLE);
        // content://com.dicoding.picodiploma.mynotesapp/bookmark/getdatabbybookmarktype/judul
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/getdatabbybookmarktype/*", BOOKMARK_TYPE);
        // content://com.dicoding.picodiploma.mynotesapp/bookmark/getdatabbybookmarktype/judul
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/deletebytitle/*", DELETE_BY_TITLE);
    }

    public BookmarkProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case DELETE_BY_TITLE:
                deleted = bookmarkHelper.deleteByJudul(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

//        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long added;
        switch (sUriMatcher.match(uri)) {
            case BOOKMARK:
                added = bookmarkHelper.insert(values);
                break;
            default:
                added = 0;
                break;
        }

//        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        bookmarkHelper = BookmarkHelper.getInstance(getContext());
        bookmarkHelper.open();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        Log.d("setData", "setData: " + uri + "||" + sUriMatcher.match(uri) + "||" + uri.getLastPathSegment());

        switch (sUriMatcher.match(uri)){
            case BOOKMARK:
                cursor = bookmarkHelper.queryAll();
                break;
            case BOOKMARK_ID:
                cursor = bookmarkHelper.queryById(uri.getLastPathSegment());
                break;
            case BOOKMARK_TYPE:
                cursor = bookmarkHelper.getAllDataByCategoryCursor(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
