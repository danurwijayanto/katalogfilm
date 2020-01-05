package com.example.katalogfilm;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmParcelable implements Parcelable {

    private String judul;
    private String description;
    private String tanggalRilis;
    private String cyrcleImage;
    private String posterImage;

    public String getCyrcleImage() {
        return cyrcleImage;
    }

    public void setCyrcleImage(String cyrcleImage) {
        this.cyrcleImage = cyrcleImage;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public static Creator<FilmParcelable> getCREATOR() {
        return CREATOR;
    }

    public FilmParcelable() {

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
        judul = in.readString();
        description = in.readString();
        tanggalRilis = in.readString();
        cyrcleImage = in.readString();
        posterImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(description);
        dest.writeString(tanggalRilis);
        dest.writeString(cyrcleImage);
        dest.writeString(posterImage);
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
