package com.example.katalogfilm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.katalogfilm.entity.FilmParcelable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FilmViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private MutableLiveData<ArrayList<FilmParcelable>> listItems = new MutableLiveData<>();

    void setData(final String param, String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<FilmParcelable> listfilm = new ArrayList<>();
        final String imageW92Url = "https://image.tmdb.org/t/p";
        String category = "movie";
        String title = "original_title";
        String releaseDate = "release_date";

        if (language == "id-ID") {
            language = "id-ID";
        } else if (language == "en-US") {
            language = "en-EN";
        }

        if (param == "TV") {
            category = "tv";
            title = "name";
            releaseDate = "first_air_date";
        }

        String url = "https://api.themoviedb.org/3/discover/" + category + "?api_key=" + API_KEY + "&language=" + language;

        final String finalTitle = title;
        final String finalReleaseDate = releaseDate;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject film = list.getJSONObject(i);
                        FilmParcelable filmItems = new FilmParcelable();
                        filmItems.setId(Integer.valueOf(film.getString("id")));
                        filmItems.setCyrcleImage(imageW92Url + "/w92" + film.getString("poster_path"));
                        filmItems.setJudul(film.getString(finalTitle));
                        filmItems.setPosterImage(imageW92Url + "/w185" + film.getString("poster_path"));
                        filmItems.setTanggalRilis(film.getString(finalReleaseDate));
                        filmItems.setDescription(film.getString("overview"));
                        listfilm.add(filmItems);
                    }
                    listItems.postValue(listfilm);
//                    Log.d("DEBUG DATA", "onSuccess: "+listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());
            }
        });

    }

    LiveData<ArrayList<FilmParcelable>> getData() {
        return listItems;
    }
}
