package com.indtop10.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String original_title;
    private String poster_path;
    private String overview;
    private String vote_avg;
    private String release_date;

    public Movie() {

    }

    private Movie(Parcel input){

        this.original_title = input.readString();
        this.poster_path = input.readString();
        this.overview = input.readString();
        this.vote_avg = input.readString();
        this.release_date = input.readString();
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_avg() {
        return vote_avg;
    }

    public void setVote_avg(String vote_avg) {
        this.vote_avg = vote_avg;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(vote_avg);
        dest.writeString(release_date);
    }

   public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

       @Override
       public Movie createFromParcel(Parcel source) {

           return new Movie(source);
       }

       @Override
       public Movie[] newArray(int size) {
           return new Movie[size];
       }
   };
}
