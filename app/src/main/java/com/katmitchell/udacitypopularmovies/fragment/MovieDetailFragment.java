package com.katmitchell.udacitypopularmovies.fragment;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MovieDetailFragment extends Fragment {

    private static final String ARG_MOVIE = "movie";

    private Movie mMovie;

    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyy");

    private TextView mTitleTextView;

    private ImageView mPosterThumbnail;

    private TextView mYearTextView;

    private TextView mUserRatingTextView;

    private TextView mSynopsisTextView;

    private FragmentListener mListener;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
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

        mTitleTextView = (TextView) root.findViewById(R.id.title_textview);
        mPosterThumbnail = (ImageView) root.findViewById(R.id.poster_thumbnail);
        mYearTextView = (TextView) root.findViewById(R.id.year);
        mUserRatingTextView = (TextView) root.findViewById(R.id.user_rating);
        mSynopsisTextView = (TextView) root.findViewById(R.id.synopsis);

        mTitleTextView.setText(mMovie.getTitle());
        mYearTextView.setText(mSdf.format(mMovie.getDate()));
        mUserRatingTextView.setText("" + mMovie.getVoteAverage() + "/10");
        mSynopsisTextView.setText(mMovie.getOverview());

        Picasso.with(getActivity()).load(mMovie.getPosterUrl(Movie.POSTER_SIZE_500))
                .into(mPosterThumbnail);
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (FragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mListener != null) {
            mListener.setTitle(mMovie.getTitle());
        }
    }
}
