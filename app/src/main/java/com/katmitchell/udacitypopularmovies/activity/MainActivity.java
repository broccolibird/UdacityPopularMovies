package com.katmitchell.udacitypopularmovies.activity;

import com.katmitchell.udacitypopularmovies.adapter.SortSpinnerAdapter;
import com.katmitchell.udacitypopularmovies.fragment.FragmentListener;
import com.katmitchell.udacitypopularmovies.adapter.MovieAdapter;
import com.katmitchell.udacitypopularmovies.fragment.MovieDetailFragment;
import com.katmitchell.udacitypopularmovies.fragment.PosterGridFragment;
import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.Movie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.Listener, FragmentListener {


    private Spinner mSortSpinner;

    private PosterGridFragment mPosterGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
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

        mPosterGridFragment = PosterGridFragment.newInstance();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, mPosterGridFragment);
        ft.commit();

    }


    @Override
    public void onMovieSelected(Movie movie) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, MovieDetailFragment.newInstance(movie));
        ft.addToBackStack(null);
        ft.commit();

        mSortSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            mSortSpinner.setVisibility(View.VISIBLE);
            mPosterGridFragment.resort((Integer) mSortSpinner.getSelectedItem());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}
