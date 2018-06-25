package com.learn.movieapp;


import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.learn.movieapp.utilities.NetworkUtils;


public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        if (savedInstanceState == null) {

            Movie movieItem = getIntent().getExtras().getParcelable(NetworkUtils.MOVIE_ITEM_KEY);
            MovieDetailFragment fragment = MovieDetailFragment.newInstance(movieItem);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
