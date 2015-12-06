package com.katmitchell.udacitypopularmovies.data;

import com.katmitchell.udacitypopularmovies.model.Movie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Kat on 11/29/15.
 *
 * Based on example by Udacity
 * https://github.com/udacity/android-content-provider/blob/master/app/src/main/java/com/sam_chordas/android/androidflavors/data/FlavorsProvider.java
 */
public class MoviesProvider extends ContentProvider {

    private static final String TAG = "MoviesProvider";

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MoviesDBHelper mOpenHelper;

    private static final int MOVIE = 100;

    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES, MOVIE);
        matcher.addURI(authority, MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES + "/#",
                MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                return retCursor;

            case MOVIE_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES,
                        projection,
                        MovieContract.FavoriteMovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContract.FavoriteMovieEntry.CONTENT_DIR_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.FavoriteMovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                long _id = db.insert(MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES, null,
                        values);
                if (_id > 0) {
                    returnUri = MovieContract.FavoriteMovieEntry.buildMoviesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case MOVIE:
                numDeleted = db.delete(
                        MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES, selection,
                        selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES + "'");
                break;
            case MOVIE_WITH_ID:
                numDeleted = db.delete(MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES,
                        MovieContract.FavoriteMovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                numUpdated = db.update(MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES,
                        values,
                        selection,
                        selectionArgs);
                break;

            case MOVIE_WITH_ID:
                numUpdated = db.update(MovieContract.FavoriteMovieEntry.TABLE_FAVORITE_MOVIES,
                        values,
                        MovieContract.FavoriteMovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

    public static ContentValues movieToContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_API_ID, movie.getId());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_DATE, movie.getDate().getTime());
        contentValues
                .put(MovieContract.FavoriteMovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.FavoriteMovieEntry.COLUMN_VIDEO, movie.isVideo() ? 1 : 0);
        contentValues
                .put(MovieContract.FavoriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        return contentValues;
    }

    public static Movie cursorToMovie(Cursor cursor) {
        Movie movie = new Movie();

        movie.setId(cursor.getInt(1));
        movie.setOverview(cursor.getString(2));
        movie.setDate(new Date(cursor.getLong(3)));
        movie.setPosterPath(cursor.getString(4));
        movie.setTitle(cursor.getString(5));
        movie.setVideo((cursor.getInt(6) == 1));
        movie.setVoteAverage(cursor.getDouble(7));

        return movie;
    }
}
