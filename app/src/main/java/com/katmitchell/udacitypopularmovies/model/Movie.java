package com.katmitchell.udacitypopularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Kat on 9/30/15.
 */
public class Movie {

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

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

    public String getPosterUrl() {

        return new StringBuilder(BASE_URL_POSTER)
                .append(POSTER_SIZE_185)
                .append(posterPath)
                .toString();
    }
}
