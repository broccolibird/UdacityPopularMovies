package com.katmitchell.udacitypopularmovies.adapter.detail;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kat on 11/15/15.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView mAuthorTextView;

    private TextView mContentTextView;


    public ReviewViewHolder(View itemView) {
        super(itemView);
        mAuthorTextView = (TextView) itemView.findViewById(R.id.author_textview);
        mContentTextView = (TextView) itemView.findViewById(R.id.content_textview);
    }

    public void populate(Review review) {
        mAuthorTextView.setText(review.getAuthor());
        mContentTextView.setText(review.getContent());
    }
}
