package com.katmitchell.udacitypopularmovies;

import com.google.gson.Gson;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.katmitchell.udacitypopularmovies.model.DiscoverMovieResponse;

import android.app.Activity;
import android.net.Uri;
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

public class PosterGridFragment extends Fragment implements Response.ErrorListener {

    private static final String TAG = "PosterGridFragment";

    private OnFragmentInteractionListener mListener;

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

        mRequestQueue = Volley.newRequestQueue(getActivity());
        mRequestQueue.add(new DiscoverMovieRequest(SORT_ORDER_HIGHEST_RATED, getString(R.string.tmdb_api_key), this));
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Failed to get list of movies", Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private static final String ENDPOINT_DISCOVER_MOVIES
            = "http://api.themoviedb.org/3/discover/movie";

    private static final String QUERY_PARAM_SORT_BY = "sort_by";

    private static final String SORT_ORDER_POPULARITY = "popularity.desc";

    private static final String SORT_ORDER_HIGHEST_RATED = "vote_average.desc";

    private static final String QUERY_PARAM_API_KEY = "api_key";


    private class DiscoverMovieRequest extends Request<DiscoverMovieResponse> {

        public DiscoverMovieRequest(String sortOrder, String apiKey,
                Response.ErrorListener listener) {
            super(Method.GET,
                    ENDPOINT_DISCOVER_MOVIES + "?" + QUERY_PARAM_SORT_BY + "=" + sortOrder + "&"
                            + QUERY_PARAM_API_KEY + "=" + apiKey,
                    listener);

            Log.d(TAG, "new request: " + ENDPOINT_DISCOVER_MOVIES + "?" + QUERY_PARAM_SORT_BY + "="
                    + sortOrder + "&"
                    + QUERY_PARAM_API_KEY + "=" + apiKey);
        }

        @Override
        protected Response<DiscoverMovieResponse> parseNetworkResponse(NetworkResponse response) {
            DiscoverMovieResponse movies;
            try {
                String json = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                Gson gson = GsonSingleton.getInstance().getGson();
                movies = gson.fromJson(json, DiscoverMovieResponse.class);
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

}
