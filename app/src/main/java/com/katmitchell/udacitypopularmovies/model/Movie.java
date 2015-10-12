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

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genre_ids")
    private int[] genreIds;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("oritinal_title")
    private String originalTitle;

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

    @SerializedName("vote_count")
    private int voteCount;

    private static final String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";

    private static final String POSTER_SIZE_185 = "w185";

    public static final String POSTER_SIZE_342 = "w342";

    public static final String POSTER_SIZE_500 = "w500";

    public static final String POSTER_SIZE_780 = "w780";

    public String getPosterUrl() {

        return getPosterUrl(POSTER_SIZE_185);
    }

    public String getPosterUrl(String posterSize) {
        return new StringBuilder(BASE_URL_POSTER)
                .append(posterSize)
                .append(posterPath)
                .toString();
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Date getDate() {
        return date;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel in) {
        boolean[] adults = new boolean[1];
        in.readBooleanArray(adults);
        adult = adults[0];

        backdropPath = in.readString();

        int numGenreIds = in.readInt();
        genreIds = new int[numGenreIds];
        in.readIntArray(genreIds);

        originalLanguage = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        date = new Date(in.readLong());
        posterPath = in.readString();
        title = in.readString();

        boolean[] videos = new boolean[1];
        in.readBooleanArray(videos);
        video = videos[0];

        voteAverage = in.readDouble();

        voteCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[]{adult});
        dest.writeString(backdropPath);
        dest.writeInt(genreIds.length);
        dest.writeIntArray(genreIds);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeLong(date.getTime());
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeBooleanArray(new boolean[]{video});
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);

    }
}
