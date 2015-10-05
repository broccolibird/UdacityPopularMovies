package com.katmitchell.udacitypopularmovies;

import com.katmitchell.udacitypopularmovies.model.Movie;
import com.katmitchell.udacitypopularmovies.view.PosterViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Kat on 9/30/15.
 */
public class MovieAdapter extends RecyclerView.Adapter {

    private List<Movie> mMovies;

    public MovieAdapter() {
    }

    public void setMovies(List<Movie> movies) {
        this.mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie_poster, parent, false);
        PosterViewHolder pvh = new PosterViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PosterViewHolder pvh = (PosterViewHolder) holder;

        pvh.populate(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return (mMovies == null) ? 0 : mMovies.size();
    }
}
