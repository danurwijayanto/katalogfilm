package com.example.katalogfilm.package_bookmark;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.katalogfilm.R;
import com.example.katalogfilm.entity.FilmParcelable;

import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.CATEGORY;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.CYRCLE_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.DESCRIPTION;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.POSTER_IMAGE;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TANGGAL_RILIS;
import static com.example.katalogfilm.db.Bookmark.BookmarkColumns.TITLE;
import static com.example.katalogfilm.db.DatabaseContract.BookmarkColumns.CONTENT_URI;

public class FilmDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_selected_value";
    public static final int REQUEST_DETAIL = 100;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;
    public static final String EXTRA_POSITION = "extra_position";

    private MenuItem removeBookmark;
    private MenuItem doBookmark;
    private MenuItem menuBookmark;
    private MenuItem languageSetting;
    private TextView detailsFilmDescription;
    private TextView detailsRelease;
    private TextView detailsJudul;
    private ImageView detailsFilmImage;
    private String judulFilm;
    private String descFilm;
    private String imageFilm;
    private String rilisFilm;
    private String category;
    private Uri uriWithId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        detailsFilmDescription = findViewById(R.id.film_description_details);
        detailsRelease = findViewById(R.id.film_rilis_details);
        detailsJudul = findViewById(R.id.film_judul_details);
        detailsFilmImage = findViewById(R.id.image_film);


        FilmParcelable filmParcelable = getIntent().getParcelableExtra(EXTRA_FILM);
        detailsFilmDescription.setText(filmParcelable.getDescription());
        detailsRelease.setText(filmParcelable.getTanggalRilis());
        detailsJudul.setText(filmParcelable.getJudul());

        Glide.with(this)
                .load(filmParcelable.getPosterImage())
                .into(detailsFilmImage);

        judulFilm = filmParcelable.getJudul();
        descFilm = filmParcelable.getDescription();
        imageFilm = filmParcelable.getPosterImage();
        rilisFilm = filmParcelable.getTanggalRilis();
        category = filmParcelable.getCategory();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details,menu);
        removeBookmark = menu.findItem(R.id.remove_bookmark);
        doBookmark = menu.findItem(R.id.add_bookmark);
        menuBookmark = menu.findItem(R.id.show_bookmark);
        languageSetting = menu.findItem(R.id.action_change_settings);

        removeBookmark.setVisible(true);
        doBookmark.setVisible(false);
        menuBookmark.setVisible(false);
        languageSetting.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_bookmark:
                ContentValues values = new ContentValues();
                values.put(TITLE, judulFilm);
                values.put(DESCRIPTION, descFilm);
                values.put(TANGGAL_RILIS,rilisFilm);
                values.put(CYRCLE_IMAGE,imageFilm);
                values.put(POSTER_IMAGE,imageFilm);
                values.put(CATEGORY,category);

                getContentResolver().insert(CONTENT_URI, values);

                Uri result = getContentResolver().insert(CONTENT_URI, values);
                if (Integer.valueOf(result.getLastPathSegment()) > 0) {
                    Toast.makeText(this, getResources().getString(R.string.sukses_bookmark), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.gagal_bookmark), Toast.LENGTH_SHORT).show();
                }
                removeBookmark.setVisible(false);
                doBookmark.setVisible(true);
                break;
            case R.id.remove_bookmark:
                uriWithId = Uri.parse(CONTENT_URI + "/deletebytitle/" + String.valueOf(judulFilm));

                long resultDelete = getContentResolver().delete(uriWithId, null, null);
//                long resultDelete = BookmarkHelper.deleteByJudul(String.valueOf(judulFilm));
                if (resultDelete > 0) {
                    Toast.makeText(this, getResources().getString(R.string.sukses_hapus_bookmark), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_POSITION, resultDelete);
                    setResult(RESULT_DELETE, intent);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.gagal_hapus_bookmark), Toast.LENGTH_SHORT).show();
                }

                removeBookmark.setVisible(false);
                doBookmark.setVisible(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
