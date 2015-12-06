package com.katmitchell.udacitypopularmovies.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Kat on 9/30/15.
 */
public class Movie implements Parcelable {

    private int _id;

    @SerializedName("id")
    private int id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private Date date;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double voteAverage;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private static final String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";

    private static final String POSTER_SIZE_185 = "w185";

    public static final String POSTER_SIZE_342 = "w342";

    public static final String POSTER_SIZE_500 = "w500";

    public static final String POSTER_SIZE_780 = "w780";

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterUrl() {

        return getPosterUrl(POSTER_SIZE_185);
    }

    public String getPosterUrl(String posterSize) {
        return new StringBuilder(BASE_URL_POSTER)
                .append(posterSize)
                .append(posterPath)
                .toString();
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie() {
    }

    public Movie(Parcel in) {
        id = in.readInt();
        overview = in.readString();
        date = new Date(in.readLong());
        posterPath = in.readString();
        title = in.readString();

        boolean[] videos = new boolean[1];
        in.readBooleanArray(videos);
        video = videos[0];

        voteAverage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeLong(date.getTime());
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeBooleanArray(new boolean[]{video});
        dest.writeDouble(voteAverage);

    }
}
