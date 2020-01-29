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

import java.util.ArrayList;

public class FilmAdapterRecycle extends RecyclerView.Adapter<FilmAdapterRecycle.ListViewHolder> {
    private ArrayList<FilmParcelable> listFilm = new ArrayList<>();
    private ArrayList<FilmParcelable> listFilmTmp = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("performFiltering", "performFiltering: " + charSequence.toString());

                if (charString.isEmpty()) {
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = listFilmTmp;
//                    return filterResults;
                    listFilm = listFilmTmp;

                } else {
                    ArrayList<FilmParcelable> filteredListFilm = new ArrayList<>();

                    for (FilmParcelable row : listFilm) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getJudul().toLowerCase().contains(charString.toLowerCase())) {
                            filteredListFilm.add(row);
                        }
                    }

                    listFilm = filteredListFilm;
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
