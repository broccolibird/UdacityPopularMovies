package com.katmitchell.udacitypopularmovies.adapter.detail;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.katmitchell.udacitypopularmovies.model.Video;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Kat on 11/1/15.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter {

    private static final int TYPE_DETAIL = 0;

    private static final int TYPE_TRAILER = 1;

    private Movie mMovie;

    private List<Video> mVideos;

    private Listener mListener;

    public void setMovie(Movie movie) {
        mMovie = movie;
        notifyDataSetChanged();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            default:
            case TYPE_DETAIL:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_movie_detail, parent, false);
                MovieInfoViewHolder mivh = new MovieInfoViewHolder(v);
                return mivh;

            case TYPE_TRAILER:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_video, parent, false);
                TrailerViewHolder tvh = new TrailerViewHolder(v);
                return tvh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position == 0) {
            ((MovieInfoViewHolder) holder).populate(mMovie);
        } else {
            ((TrailerViewHolder) holder).populate(mVideos.get(position - 1));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onVideoSelected(mVideos.get(position - 1));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 1 + ((mVideos == null) ? 0 : mVideos.size());
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? TYPE_DETAIL : TYPE_TRAILER;
    }

    public interface Listener {

        public void onVideoSelected(Video video);
    }

}
