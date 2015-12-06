package com.katmitchell.udacitypopularmovies.movie.view;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Kat on 9/30/15.
 */
public class PosterViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;

    public PosterViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.imageview);
    }

    public void populate(Movie movie) {
        Picasso.with(itemView.getContext()).load(movie.getPosterUrl()).fit().into(mImageView);
    }
}
