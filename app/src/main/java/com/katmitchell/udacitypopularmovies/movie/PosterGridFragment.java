package com.katmitchell.udacitypopularmovies.movie;

import com.google.gson.Gson;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.katmitchell.udacitypopularmovies.data.MovieContract;
import com.katmitchell.udacitypopularmovies.data.MoviesProvider;
import com.katmitchell.udacitypopularmovies.fragment.FragmentListener;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.katmitchell.udacitypopularmovies.model.SortOrder;
import com.katmitchell.udacitypopularmovies.network.GsonSingleton;
import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.DiscoverMovieResponse;
import com.katmitchell.udacitypopularmovies.network.MovieApi;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PosterGridFragment extends Fragment implements Response.ErrorListener {

    private static final String TAG = "PosterGridFragment";

    private MovieAdapter.Listener mListener;

    private FragmentListener mFragmentListener;

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private RequestQueue mRequestQueue;

    public static PosterGridFragment newInstance() {
        PosterGridFragment fragment = new PosterGridFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PosterGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_poster_grid, container, false);

        mMovieAdapter = new MovieAdapter();

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerView.setAdapter(mMovieAdapter);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (MovieAdapter.Listener) activity;
            mFragmentListener = (FragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MovieAdapter.Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mFragmentListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFragmentListener != null) {
            mFragmentListener.setTitle(getString(R.string.app_name));
        }
        mMovieAdapter.setListener(mListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMovieAdapter.setListener(null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Failed to get list of movies", Toast.LENGTH_LONG).show();
    }


    public void resort(int sortOrder) {

        String sortQuery;
        boolean favorites = false;

        switch (sortOrder) {
            case SortOrder.POPULARITY:
                sortQuery = MovieApi.SORT_ORDER_POPULARITY;
                break;
            default:
            case SortOrder.USER_RATING:
                sortQuery = MovieApi.SORT_ORDER_HIGHEST_RATED;
                break;

            case SortOrder.FAVORITES:
                sortQuery = null;
                favorites = true;
                break;
        }
        if (sortQuery != null) {
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue.add(new DiscoverMovieRequest(sortQuery,
                    getString(R.string.tmdb_api_key), this));
        } else if (favorites) {
            new FavoriteMoviesAsyncTask().execute();
        }
    }


    private class DiscoverMovieRequest extends Request<DiscoverMovieResponse> {

        public DiscoverMovieRequest(String sortOrder, String apiKey,
                Response.ErrorListener listener) {
            super(Method.GET,
                    MovieApi.ENDPOINT_DISCOVER_MOVIES + "?" + MovieApi.QUERY_PARAM_SORT_BY + "="
                            + sortOrder + "&"
                            + MovieApi.QUERY_PARAM_API_KEY + "=" + apiKey,
                    listener);

            Log.d(TAG, "new request: " + MovieApi.ENDPOINT_DISCOVER_MOVIES + "?"
                    + MovieApi.QUERY_PARAM_SORT_BY + "="
                    + sortOrder + "&"
                    + MovieApi.QUERY_PARAM_API_KEY + "=" + apiKey);
        }

        @Override
        protected Response<DiscoverMovieResponse> parseNetworkResponse(NetworkResponse response) {
            DiscoverMovieResponse movies;
            try {
                String json = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                Gson gson = GsonSingleton.getInstance().getGson();
                movies = gson.fromJson(json, DiscoverMovieResponse.class);
                Log.d(TAG, "json\n" + json);
                return Response.success(movies, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(DiscoverMovieResponse response) {
            mMovieAdapter.setMovies(response.getResults());
        }
    }

    private class FavoriteMoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {

            Cursor cursor = getActivity().getContentResolver()
                    .query(
                            MovieContract.FavoriteMovieEntry.CONTENT_URI,
                            MovieContract.FavoriteMovieEntry.FULL_PROJECTION,
                            null,
                            null,
                            null);

            List<Movie> favoriteMovies = new ArrayList<>();

            while (cursor.moveToNext()) {
                favoriteMovies.add(MoviesProvider.cursorToMovie(cursor));
            }

            return favoriteMovies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMovieAdapter.setMovies(movies);
        }
    }


}
