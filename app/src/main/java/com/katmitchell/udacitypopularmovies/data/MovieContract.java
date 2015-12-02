package com.katmitchell.udacitypopularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kat on 11/29/15.
 *
 * Based on example provided by Udacity
 * https://github.com/udacity/android-content-provider/blob/master/app/src/main/java/com/sam_chordas/android/androidflavors/data/FlavorsContract.java
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.katmitchell.udacitypopularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final String TABLE_FAVORITE_MOVIES = "FavoriteMovies";

        public static final String _ID = "_id";

        public static final String COLUMN_API_ID = "api_id";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_DATE = "date";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_VIDEO = "video";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_FAVORITE_MOVIES).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + TABLE_FAVORITE_MOVIES;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + TABLE_FAVORITE_MOVIES;

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
