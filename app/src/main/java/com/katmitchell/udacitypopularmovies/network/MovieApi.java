package com.katmitchell.udacitypopularmovies.network;

/**
 * Created by Kat on 11/1/15.
 */
public class MovieApi {

    public static final String BASE_URL = "http://api.themoviedb.org/3";

    public static final String ENDPOINT_DISCOVER_MOVIES = BASE_URL + "/discover/movie";

    public static final String ENDPOINT_VIDEOS = BASE_URL + "/movie/%d/videos";

    public static final String QUERY_PARAM_SORT_BY = "sort_by";

    public static final String SORT_ORDER_POPULARITY = "popularity.desc";

    public static final String SORT_ORDER_HIGHEST_RATED = "vote_average.desc";

    public static final String QUERY_PARAM_API_KEY = "api_key";
}
