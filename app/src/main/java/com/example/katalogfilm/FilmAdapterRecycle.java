package com.example.katalogfilm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FilmAdapterRecycle extends RecyclerView.Adapter<FilmAdapterRecycle.ListViewHolder>{
    private ArrayList<FilmParcelable> listFilm;

    public FilmAdapterRecycle(ArrayList<FilmParcelable> list) {
        this.listFilm = list;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        FilmParcelable film = listFilm.get(position);

        Glide.with(holder.itemView.getContext())
                .load(film.getFilm())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgPhoto);
        holder.filmJudul.setText(film.getJudul());
        holder.filmDescription.setText(film.getDescription());
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
}
