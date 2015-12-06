package com.katmitchell.udacitypopularmovies.movie;

import com.katmitchell.udacitypopularmovies.R;
import com.katmitchell.udacitypopularmovies.model.SortOrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Kat on 10/11/15.
 */
public class SortSpinnerAdapter extends BaseAdapter {

    private int[] sortOptions;

    public SortSpinnerAdapter() {
        sortOptions = new int[3];
        sortOptions[0] = SortOrder.POPULARITY;
        sortOptions[1] = SortOrder.USER_RATING;
        sortOptions[2] = SortOrder.FAVORITES;
    }

    @Override
    public int getCount() {
        return sortOptions.length;
    }

    @Override
    public Object getItem(int position) {
        return sortOptions[position];
    }

    @Override
    public long getItemId(int position) {
        return sortOptions[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_spinner_sort, parent, false);

        TextView textView = (TextView) root.findViewById(R.id.text);
        setText(textView, position);
        return root;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_spinner_sort, parent, false);

        TextView textView = (TextView) root.findViewById(R.id.text);
        setText(textView, position);
        return root;
    }

    private void setText (TextView textView, int position) {

        switch (sortOptions[position]) {
            case SortOrder.POPULARITY:
                textView.setText("Popularity");
                break;
            case SortOrder.USER_RATING:
                textView.setText("User rating");
                break;
            case SortOrder.FAVORITES:
                textView.setText("Favorites");
                break;
        }
    }
}
