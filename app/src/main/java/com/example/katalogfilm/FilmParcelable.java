package com.example.katalogfilm;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmParcelable implements Parcelable {
    private int film;
    private String judul;
    private String description;
    private String tanggalRilis;

    public FilmParcelable() {

    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    protected FilmParcelable(Parcel in) {
        film = in.readInt();
        judul = in.readString();
        description = in.readString();
        tanggalRilis = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(film);
        dest.writeString(judul);
        dest.writeString(description);
        dest.writeString(tanggalRilis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilmParcelable> CREATOR = new Creator<FilmParcelable>() {
        @Override
        public FilmParcelable createFromParcel(Parcel source) {
            return  new FilmParcelable(source);
        }

        @Override
        public FilmParcelable[] newArray(int size) {
            return new FilmParcelable[size];
        }
    };
}
