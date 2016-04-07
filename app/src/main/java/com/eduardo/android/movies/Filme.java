package com.eduardo.android.movies;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Currency;

/**
 * Created by Eduardo on 01/04/2016.
 */
public class Filme implements Parcelable{

    private int id;
    private String titulo;
    private String poster;
    private String backdrop;
    private String overview;
    private int rating;
    private String date;

    public Filme() { }

    public Filme(JSONObject filmeJson) throws JSONException {
        this.id = filmeJson.getInt("id");
        this.titulo = filmeJson.getString("original_title");
        this.poster = filmeJson.getString("poster_path");
        this.backdrop = filmeJson.getString("backdrop_path");
        this.overview = filmeJson.getString("overview");
        this.rating = filmeJson.getInt("vote_average");
        this.date = filmeJson.getString("release_date");
    }

    public Filme(Cursor cursor){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(overview);
        dest.writeInt(rating);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Filme> CREATOR
            = new Parcelable.Creator<Filme>(){

        @Override
        public Filme createFromParcel(Parcel source) {
            return new Filme(source);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

    private Filme(Parcel parcel){
        id = parcel.readInt();
        titulo = parcel.readString();
        poster = parcel.readString();
        backdrop = parcel.readString();
        overview = parcel.readString();
        rating = parcel.readInt();
        date = parcel.readString();
    }


}
