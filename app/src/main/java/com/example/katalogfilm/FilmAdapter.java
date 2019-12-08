package com.example.katalogfilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Film> films = new ArrayList<>();

    //settter hasil generate
    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    //constructor hasil generate
    FilmAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Film film = (Film) getItem(position);
        viewHolder.bind(film);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgPhoto;

        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txt_name);
            txtDescription = view.findViewById(R.id.txt_description);
            imgPhoto = view.findViewById(R.id.img_photo);
        }

        void bind(Film film) {
            txtName.setText(film.getJudul());
            txtDescription.setText(film.getDescription());
            imgPhoto.setImageResource((film.getFilm()));
        }
    }

}