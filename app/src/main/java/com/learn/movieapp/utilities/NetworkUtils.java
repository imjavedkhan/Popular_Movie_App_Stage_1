package com.learn.movieapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String MOVIE_DATA_API = "https://api.themoviedb.org/3/movie/";
    public static final String API_KEY_PARAM = "api_key";

    /******************** ENTER YOUR API KEY HERE ********************/

    public static final String API_KEY_VALUE = "API_KEY_HERE";
    public static final String MOVIE_ITEM_KEY = "movie_item_key";
    public static final String DEFAULT_RATING = "10";


    public static URL buildMovieDataUrl(String sortOrder) {
        Uri builtUri = Uri.parse(MOVIE_DATA_API + sortOrder).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
