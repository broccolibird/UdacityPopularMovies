package com.katmitchell.udacitypopularmovies.detail.view;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Video;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kat on 11/1/15.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleTextView;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.video_title);
    }

    public void populate(Video video) {
        mTitleTextView.setText(video.getName());
    }
}
