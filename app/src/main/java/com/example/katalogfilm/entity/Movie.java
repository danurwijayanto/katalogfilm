package com.example.katalogfilm.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private Integer id;
    private String judul;
    private String description;
    private String tanggalRilis;
    private String cyrcleImage;
    private String posterImage;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

//    public Movie(int id, String judul, String description, String tanggalRilis, String cyrcleImage, String posterImage) {
    public Movie() {
//        this.id = id;
//        this.judul = judul;
//        this.description = description;
//        this.tanggalRilis = tanggalRilis;
//        this.cyrcleImage = cyrcleImage;
//        this.posterImage = posterImage;
    }

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        judul = in.readString();
        description = in.readString();
        tanggalRilis = in.readString();
        cyrcleImage = in.readString();
        posterImage = in.readString();
        category = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(judul);
        dest.writeString(description);
        dest.writeString(tanggalRilis);
        dest.writeString(cyrcleImage);
        dest.writeString(posterImage);
        dest.writeString(category);
    }
}
