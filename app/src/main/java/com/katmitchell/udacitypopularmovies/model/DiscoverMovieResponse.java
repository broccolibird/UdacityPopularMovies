package com.katmitchell.udacitypopularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kat on 9/30/15.
 */
public class DiscoverMovieResponse {

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public List<Movie> getResults() {
        return results;
    }
}
