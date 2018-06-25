package com.learn.movieapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.learn.movieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {

    public static final String MOVIE_ITEM_ID = "movie_item_id";

    private Movie movieItem;

    public MovieDetailFragment(){

    }

    public static MovieDetailFragment newInstance(Movie movieItem) {

        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ITEM_ID, movieItem);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(MOVIE_ITEM_ID)) {
            movieItem = getArguments().getParcelable(MOVIE_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_movie_detail, container, false);
        if (movieItem != null) {
            ((TextView) rootView.findViewById(R.id.tv_title)).setText(movieItem.getTitle());
            //((TextView) rootView.findViewById(R.id.tv_image_title)).setText(movieItem.getTitle());
            ((TextView) rootView.findViewById(R.id.tv_release_date)).setText(movieItem.getReleaseDate());
            ((TextView) rootView.findViewById(R.id.tv_overview)).setText(movieItem.getOverview());
            ((TextView) rootView.findViewById(R.id.tv_rating)).setText(String.valueOf(movieItem.getVoteAverage())
                    + "/" + NetworkUtils.DEFAULT_RATING);
            String url = NetworkUtils.BASE_IMAGE_URL + movieItem.getPosterPath();
            Picasso.with(getActivity()).load(url).placeholder(R.drawable.ic_launcher_foreground)
                    .into((ImageView) rootView.findViewById(R.id.tv_poster));
        }

        return rootView;
    }
}
