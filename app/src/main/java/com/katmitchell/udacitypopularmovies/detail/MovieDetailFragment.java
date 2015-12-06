package com.katmitchell.udacitypopularmovies.detail;

import com.google.gson.Gson;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.katmitchell.udacitypopularmovies.model.MovieReviewResponse;
import com.katmitchell.udacitypopularmovies.model.MovieVideoResponse;
import com.katmitchell.udacitypopularmovies.model.Video;
import com.katmitchell.udacitypopularmovies.network.GsonSingleton;
import com.katmitchell.udacitypopularmovies.network.MovieApi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;

public class MovieDetailFragment extends Fragment implements Response.ErrorListener,
        MovieDetailAdapter.Listener {

    private static final String TAG = "MovieDetailFragment";

    private static final String ARG_MOVIE = "movie";

    private Movie mMovie;

    private String mFirstTrailerUrl;

    private RequestQueue mRequestQueue;

    private RecyclerView mRecyclerView;

    private MovieDetailAdapter mAdapter;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieDetailFragment newInstance() {
        MovieDetailFragment fragment = new MovieDetailFragment();
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        mAdapter = new MovieDetailAdapter();

        if (mMovie == null) {
            root.findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAdapter.setMovie(getActivity(), mMovie);
            mRecyclerView.setAdapter(mAdapter);
        }

        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapter.setListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMovie != null) {
            String apiKey = getString(R.string.tmdb_api_key);
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue
                    .add(new MovieVideoRequest(mMovie.getId(), apiKey, this));
            mRequestQueue.add(new MovieReviewRequest(mMovie.getId(), apiKey, this));
            mAdapter.setListener(this);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i(TAG, "onErrorResponse: " + error);
        // TODO
    }

    @Override
    public void onVideoSelected(Video video) {
        Uri webpage = Uri.parse(video.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    public String getFirstTrailerUrl() {
        return mFirstTrailerUrl;
    }


    private class MovieVideoRequest extends Request<MovieVideoResponse> {

        private static final String TAG = "MovieVideoRequest";

        public MovieVideoRequest(int id, String apiKey, Response.ErrorListener listener) {
            super(Method.GET,
                    String.format(MovieApi.ENDPOINT_VIDEOS, id) +
                            "?" + MovieApi.QUERY_PARAM_API_KEY + "=" + apiKey, listener);
            Log.d(TAG, "new request: " + String.format(MovieApi.ENDPOINT_VIDEOS, id)
                    + "?" + MovieApi.QUERY_PARAM_API_KEY + "=" + apiKey);
        }

        @Override
        protected Response<MovieVideoResponse> parseNetworkResponse(NetworkResponse response) {

            MovieVideoResponse movies;
            try {
                String json = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                Gson gson = GsonSingleton.getInstance().getGson();
                movies = gson.fromJson(json, MovieVideoResponse.class);
                Log.d(TAG, "videos:\n" + json);
                return Response.success(movies, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(MovieVideoResponse response) {
            mAdapter.setVideos(response.getResults());

            if (response.getResults().size() > 0) {
                mFirstTrailerUrl = response.getResults().get(0).getUrl();
            }
        }
    }

    private class MovieReviewRequest extends Request<MovieReviewResponse> {

        private static final String TAG = "MovieReviewRequest";

        public MovieReviewRequest(int id, String apiKey, Response.ErrorListener listener) {
            super(Method.GET,
                    String.format(MovieApi.ENDPOINT_REVIEWS, id) + "?"
                            + MovieApi.QUERY_PARAM_API_KEY
                            + "=" + apiKey, listener);
        }

        @Override
        protected Response<MovieReviewResponse> parseNetworkResponse(NetworkResponse response) {
            MovieReviewResponse reviews;
            try {
                String json = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                Gson gson = GsonSingleton.getInstance().getGson();
                reviews = gson.fromJson(json, MovieReviewResponse.class);
                return Response.success(reviews, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(MovieReviewResponse response) {
            mAdapter.setReviews(response.getResults());
        }
    }
}
