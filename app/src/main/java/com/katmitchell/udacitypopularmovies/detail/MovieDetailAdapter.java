package com.katmitchell.udacitypopularmovies.detail;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.data.MovieContract;
import com.katmitchell.udacitypopularmovies.data.MoviesProvider;
import com.katmitchell.udacitypopularmovies.detail.view.MovieInfoViewHolder;
import com.katmitchell.udacitypopularmovies.detail.view.ReviewHeaderViewHolder;
import com.katmitchell.udacitypopularmovies.detail.view.ReviewViewHolder;
import com.katmitchell.udacitypopularmovies.detail.view.TrailerHeaderViewHolder;
import com.katmitchell.udacitypopularmovies.detail.view.TrailerViewHolder;
import com.katmitchell.udacitypopularmovies.model.Movie;
import com.katmitchell.udacitypopularmovies.model.Review;
import com.katmitchell.udacitypopularmovies.model.Video;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by Kat on 11/1/15.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter {

    private static final String TAG = MovieDetailAdapter.class.getSimpleName();

    private static final int TYPE_DETAIL = 0;

    private static final int TYPE_TRAILER = 1;

    private static final int TYPE_REVIEW = 2;

    private static final int TYPE_TRAILER_HEADER = 3;

    private static final int TYPE_REVIEW_HEADER = 4;

    private Movie mMovie;

    private boolean isFavorite;

    private List<Video> mVideos;

    private List<Review> mReviews;

    private Listener mListener;

    public void setMovie(Context context, Movie movie) {
        mMovie = movie;
        Cursor cursor = context.getContentResolver()
                .query(
                        MovieContract.FavoriteMovieEntry.CONTENT_URI,
                        new String[]{MovieContract.FavoriteMovieEntry._ID,
                                MovieContract.FavoriteMovieEntry.COLUMN_API_ID},
                        MovieContract.FavoriteMovieEntry.COLUMN_API_ID + " = ?",
                        new String[]{"" + mMovie.getId()},
                        null);
        isFavorite = cursor.moveToFirst();
        cursor.close();

        notifyDataSetChanged();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
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
                final MovieInfoViewHolder mivh = new MovieInfoViewHolder(v);
                mivh.mFavoriteButton.setChecked(isFavorite);
                mivh.mFavoriteButton.setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                    boolean isChecked) {
                                if (isChecked) {
                                    mivh.itemView.getContext().getContentResolver()
                                            .insert(
                                                    MovieContract.FavoriteMovieEntry.CONTENT_URI,
                                                    MoviesProvider.movieToContentValues(mMovie));
                                    isFavorite = true;
                                } else {
                                    mivh.itemView.getContext().getContentResolver()
                                            .delete(
                                                    MovieContract.FavoriteMovieEntry.CONTENT_URI,
                                                    MovieContract.FavoriteMovieEntry.COLUMN_API_ID
                                                            + "= ?",
                                                    new String[]{Integer.toString(mMovie.getId())});
                                    isFavorite = false;
                                }
                            }
                        });
                return mivh;

            case TYPE_TRAILER:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_video, parent, false);
                TrailerViewHolder tvh = new TrailerViewHolder(v);
                return tvh;
            case TYPE_REVIEW:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_review, parent, false);
                ReviewViewHolder rvh = new ReviewViewHolder(v);
                return rvh;

            case TYPE_TRAILER_HEADER:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_video_header, parent, false);
                TrailerHeaderViewHolder thvh = new TrailerHeaderViewHolder(v);
                return thvh;
            case TYPE_REVIEW_HEADER:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_review_header, parent, false);
                ReviewHeaderViewHolder rhvh = new ReviewHeaderViewHolder(v);
                return rhvh;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int numVideoRows = (mVideos != null && mVideos.size() > 0) ? mVideos.size() + 1 : 0;
        int numReviewRows = (mReviews != null && mReviews.size() > 0) ? mReviews.size() + 1 : 0;

        Log.d(TAG, "position: " + position + ", numvideorows: " + numVideoRows + ", num reviews: "
                + numReviewRows);
        if (position == 0) {

            ((MovieInfoViewHolder) holder).populate(mMovie);

        } else if (mVideos != null && position == 1) {

            // Trailers header

        } else if (mVideos != null && position <= numVideoRows) {

            final Video video = mVideos.get(position - 2);
            ((TrailerViewHolder) holder).populate(video);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onVideoSelected(video);
                    }
                }
            });
        } else if (position == numVideoRows + 1) {

            // Reviews header

        } else {

            int index = position - 1;
            index -= numVideoRows + 1;

            Review review = mReviews.get(index);
            ((ReviewViewHolder) holder).populate(review);

        }
    }

    @Override
    public int getItemCount() {
        return 1 +
                ((mVideos != null && mVideos.size() > 0) ? mVideos.size() + 1 : 0) +
                ((mReviews != null && mReviews.size() > 0) ? mReviews.size() + 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_DETAIL;
        }

        int numVideoRows = (mVideos != null && mVideos.size() > 0) ? mVideos.size() + 1 : 0;

        if (mVideos != null && position == 1) {
            return TYPE_TRAILER_HEADER;
        } else if (mVideos != null && position <= numVideoRows) {
            return TYPE_TRAILER;
        } else if (position == numVideoRows + 1) {
            return TYPE_REVIEW_HEADER;
        } else {
            return TYPE_REVIEW;
        }
    }

    public interface Listener {

        void onVideoSelected(Video video);
    }

}
