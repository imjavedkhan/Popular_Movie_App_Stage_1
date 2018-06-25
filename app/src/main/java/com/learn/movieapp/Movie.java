package com.learn.movieapp;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    private String posterPath;
    private String overview;
    private String releaseDate;
    private String title;
    private long voteAverage;

    public Movie(){
        }

    protected Movie(Parcel in){
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        voteAverage = in.readLong();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath(){ return posterPath; }

    public void setPosterPath(String posterPath){ this.posterPath = posterPath; }

    public String getOverview() { return overview; }

    public void setOverview(String overview){ this.overview = overview; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate){ this.releaseDate = releaseDate; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public long getVoteAverage() { return voteAverage; }

    public void setVoteAverage(long voteAverage) { this.voteAverage = voteAverage; }

    @Override
    public  int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeLong(voteAverage);
    }
}
