package com.katmitchell.udacitypopularmovies.detail.view;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;

/**
 * Created by Kat on 11/1/15.
 */
public class MovieInfoViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MovieInfoViewHolder";

    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyy");

    private TextView mTitleTextView;

    private ImageView mPosterThumbnail;

    private TextView mYearTextView;

    private TextView mUserRatingTextView;

    private TextView mSynopsisTextView;

    public ToggleButton mFavoriteButton;

    public MovieInfoViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = (TextView) itemView.findViewById(R.id.title_textview);
        mPosterThumbnail = (ImageView) itemView.findViewById(R.id.poster_thumbnail);
        mYearTextView = (TextView) itemView.findViewById(R.id.year);
        mUserRatingTextView = (TextView) itemView.findViewById(R.id.user_rating);
        mSynopsisTextView = (TextView) itemView.findViewById(R.id.synopsis);
        mFavoriteButton = (ToggleButton) itemView.findViewById(R.id.favorite);

    }

    public void populate(Movie mMovie) {
        mTitleTextView.setText(mMovie.getTitle());
        mYearTextView.setText(mSdf.format(mMovie.getDate()));
        mUserRatingTextView.setText("" + mMovie.getVoteAverage() + "/10");
        mSynopsisTextView.setText(mMovie.getOverview());

        Picasso.with(itemView.getContext()).load(mMovie.getPosterUrl(Movie.POSTER_SIZE_500))
                .into(mPosterThumbnail);
    }
}
