package com.katmitchell.udacitypopularmovies.adapter;

import com.katmitchell.udacitypopularmovies.R;
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

    private Listener mListener;

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
        final Movie movie =mMovies.get(position);
        PosterViewHolder pvh = (PosterViewHolder) holder;

        pvh.populate(movie);
        pvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMovieSelected(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mMovies == null) ? 0 : mMovies.size();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {

        void onMovieSelected(Movie movie);
    }
}
