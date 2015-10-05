package com.katmitchell.udacitypopularmovies.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Kat on 10/4/15.
 */
public class PosterImageView extends ImageView {

    private static final double STANDARD_POSTER_WIDTH = 27;

    private static final double STANDARD_POSTER_HEIGHT = 40;

    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, (int) (width / STANDARD_POSTER_WIDTH * STANDARD_POSTER_HEIGHT));
    }
}
