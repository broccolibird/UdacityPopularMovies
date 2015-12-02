package com.katmitchell.udacitypopularmovies.data;

import com.katmitchell.udacitypopularmovies.model.Movie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kat on 11/29/15.
 *
 * Based on example by Udacity
 * https://github.com/udacity/android-content-provider/blob/master/app/src/main/java/com/sam_chordas/android/androidflavors/data/FlavorsDBHelper.java
 */
public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final String TAG = "MovieDBHelper";

    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES + "("
                + MovieContract.FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.FavoriteMovieEntry.COLUMN_API_ID + " INTEGER, " +
                MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieContract.FavoriteMovieEntry.COLUMN_DATE + " INTEGER, " +
                MovieContract.FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
                MovieContract.FavoriteMovieEntry.COLUMN_TITLE + " TEXT, " +
                MovieContract.FavoriteMovieEntry.COLUMN_VIDEO + " INTEGER, " +
                MovieContract.FavoriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL);";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                + ". OLD DATA WILL BE DESTROYED");
        db.execSQL(
                "DROP TABLE IF EXISTS " + MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                + MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES + "'");

        onCreate(db);
    }
}
