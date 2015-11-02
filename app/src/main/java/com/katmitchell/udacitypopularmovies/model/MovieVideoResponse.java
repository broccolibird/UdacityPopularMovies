package com.katmitchell.udacitypopularmovies.model;

import java.util.List;

/**
 * Created by Kat on 11/1/15.
 */
public class MovieVideoResponse {

    private int id;

    private List<Video> results;

    public int getId() {
        return id;
    }

    public List<Video> getResults() {
        return results;
    }
}
