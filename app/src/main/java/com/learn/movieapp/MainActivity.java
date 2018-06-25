package com.learn.movieapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.learn.movieapp.utilities.MovieJSONUtils;
import com.learn.movieapp.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private static final int MOVIE_DATA_LOADER_ID = 0;
    public String currentSortOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler);
        assert mRecyclerView != null;
        callRecyclerView(mRecyclerView);
        currentSortOrder = PreferenceManager.getDefaultSharedPreferences(this).getString("sort_order", "popular");
        if (isOnline()) getSupportLoaderManager().initLoader(MOVIE_DATA_LOADER_ID, null, this);
    }

    private void callRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        mRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, this);
        recyclerView.setAdapter(movieAdapter);
    }

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {
            List<Movie> mMovieItem;

            @Override
            protected void onStartLoading() {

                if (mMovieItem != null) {
                    deliverResult(mMovieItem);
                } else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<Movie> loadInBackground() {
                URL movieDataUrl = NetworkUtils.buildMovieDataUrl(currentSortOrder);
                try {
                    String jsonMovieData = NetworkUtils.getResponseFromHttpUrl(movieDataUrl);
                    mMovieItem = MovieJSONUtils.getMovieItemsFromJson(MainActivity.this, jsonMovieData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return mMovieItem;
            }

            @Override
            public void deliverResult(@Nullable List<Movie> data) {
                mMovieItem = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        movieAdapter.setMovieData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }

    //menu inflated on main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pop_mov) {
            // load popular movies

            currentSortOrder = "popular";
            getSupportLoaderManager().restartLoader(MOVIE_DATA_LOADER_ID, null, this);

            //Intent intent= new Intent(this, MainActivity.class);
            //startActivity(intent);
            //finish();

            return true;
        } else if (id == R.id.high_rated) {
            // load top rated movies

            currentSortOrder = "top_rated";
            getSupportLoaderManager().restartLoader(MOVIE_DATA_LOADER_ID, null, this);

        }
        return super.onOptionsItemSelected(item);

    }

    //This method shows detail of movie item clicked.

    @Override
    public void onClick(Movie movieItem) {
            Intent intent = new Intent(this, MovieDetail.class);
            intent.putExtra(NetworkUtils.MOVIE_ITEM_KEY, movieItem);
            startActivity(intent);
        }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
