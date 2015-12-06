package com.katmitchell.udacitypopularmovies.movie;

import com.katmitchell.udacitypopularmovies.detail.DetailActivity;
import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.detail.MovieDetailFragment;
import com.katmitchell.udacitypopularmovies.model.Movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.Listener {

    private static final String TAG = "MovieActivity";

    private boolean mTwoPane;

    private Spinner mSortSpinner;

    private PosterGridFragment mPosterGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSortSpinner = (Spinner) findViewById(R.id.sort_spinner);
        mSortSpinner.setAdapter(new SortSpinnerAdapter());
        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mPosterGridFragment != null) {
                    mPosterGridFragment
                            .resort((Integer) parent.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mPosterGridFragment = (PosterGridFragment) getFragmentManager()
                .findFragmentById(R.id.movie_grid_fragment);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, MovieDetailFragment.newInstance())
                        .commit();
            }
        }

    }


    @Override
    public void onMovieSelected(Movie movie) {
        if (mTwoPane) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, MovieDetailFragment.newInstance(movie))
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }

}
