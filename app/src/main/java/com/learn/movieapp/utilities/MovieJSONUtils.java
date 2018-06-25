package com.learn.movieapp.utilities;

import android.content.Context;

import com.learn.movieapp.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJSONUtils {

    private static final String KEY_RESULT = "results";

    private static final String KEY_TITLE = "title";

    private static final String KEY_POSTER_PATH = "poster_path";

    private static final String KEY_OVERVIEW = "overview";

    private static final String KEY_VOTE_AVERAGE = "vote_average";

    private static final String KEY_RELEASE_DATE = "release_date";

    public static List<Movie> getMovieItemsFromJson(Context context, String movieJsonStr) throws JSONException{

        List<Movie> movieItems = new ArrayList<>();

        JSONObject movieJson = new JSONObject(movieJsonStr);

        if(!movieJson.has(KEY_RESULT)) return movieItems;

        JSONArray movieDetailArray = movieJson.getJSONArray(KEY_RESULT);

        for (int i = 0; i <= movieDetailArray.length() - 1; i++){

            JSONObject movieDetail = movieDetailArray.getJSONObject(i);

            Movie movieItem = new Movie();

            movieItem.setTitle(movieDetail.getString(KEY_TITLE));
            movieItem.setPosterPath(movieDetail.getString(KEY_POSTER_PATH));
            movieItem.setOverview(movieDetail.getString(KEY_OVERVIEW));
            movieItem.setVoteAverage(movieDetail.getLong(KEY_VOTE_AVERAGE));
            movieItem.setReleaseDate(movieDetail.getString(KEY_RELEASE_DATE));

            movieItems.add(movieItem);
        }

        return movieItems;
    }
}
