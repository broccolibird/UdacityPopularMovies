package com.katmitchell.udacitypopularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kat on 11/15/15.
 */
public class MovieReviewResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Review> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public List<Review> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
