package com.example.katalogfilm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.katalogfilm.entity.FilmParcelable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FilmAdapterRecycle extends RecyclerView.Adapter<FilmAdapterRecycle.ListViewHolder> {
    private ArrayList<FilmParcelable> listFilm = new ArrayList<>();
    private ArrayList<FilmParcelable> listFilmTmp = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;
    private String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private final String imageW92Url = "https://image.tmdb.org/t/p";


    public void setData(ArrayList<FilmParcelable> items) {
        listFilm.clear();
        listFilm.addAll(items);
        listFilmTmp.clear();
        listFilmTmp.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final FilmParcelable item) {
        listFilm.add(item);
        listFilmTmp.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {

        listFilm.clear();
        listFilmTmp.clear();

    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        FilmParcelable film = listFilm.get(position);

        Glide.with(holder.itemView.getContext())
                .load(film.getCyrcleImage())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.filmJudul.setText(film.getJudul());
        holder.filmDescription.setText(film.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listFilm.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView filmJudul, filmDescription;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            filmJudul = itemView.findViewById(R.id.film_item_name);
            filmDescription = itemView.findViewById(R.id.film_item_description);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(FilmParcelable data);
    }

    public Filter getFilter(final String activeTab) {
        final AsyncHttpClient client = new AsyncHttpClient();
        Log.d("dancuk", "onSuccess: "+ activeTab);

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    listFilm = listFilmTmp;

                } else {
                    final ArrayList<FilmParcelable> filteredListFilm = new ArrayList<>();

                    String url = "https://api.themoviedb.org/3/search/" + activeTab + "?api_key=" + API_KEY + "&language=en-US&query="+charString;
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

                                    if (activeTab == "tv") {
                                        filmItems.setId(Integer.valueOf(film.getString("id")));
                                        filmItems.setCyrcleImage(imageW92Url + "/w92" + film.getString("poster_path"));
                                        filmItems.setJudul(film.getString("name"));
                                        filmItems.setPosterImage(imageW92Url + "/w185" + film.getString("poster_path"));
                                        filmItems.setTanggalRilis(film.getString("first_air_date"));
                                        filmItems.setDescription(film.getString("overview"));
                                    }else{
                                        filmItems.setId(Integer.valueOf(film.getString("id")));
                                        filmItems.setCyrcleImage(imageW92Url + "/w92" + film.getString("poster_path"));
                                        filmItems.setJudul(film.getString("title"));
                                        filmItems.setPosterImage(imageW92Url + "/w185" + film.getString("poster_path"));
                                        filmItems.setTanggalRilis(film.getString("release_date"));
                                        filmItems.setDescription(film.getString("overview"));
                                    }

                                    filteredListFilm.add(filmItems);
                                }
                                listFilm = filteredListFilm;
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

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilm;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFilm = (ArrayList<FilmParcelable>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
