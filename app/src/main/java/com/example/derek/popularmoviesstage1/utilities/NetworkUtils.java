package com.example.derek.popularmoviesstage1.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    // "http://api.themoviedb.org/3/movie/popular?api_key=f07503*******";
// https://api.themoviedb.org/3/movie/550?api_key=f075*****
// https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=f075*****

    //http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
    final static String TMDB_BASE_URL =
            "https://api.themoviedb.org/3";
    final static String SEGMENT_DISCOVER = "discover/movie";

    final static String PARAM_SORT = "sort_by";
    final static String sortByPopularity = "popularity.desc";
    final static String sortByRating = "vote_average.desc";

    final static String PARAM_API_KEY = "api_key";
    final static String apiKey = "f07*********";


    //poster
    final static String TMDB_POSTER_URL =
            "http://image.tmdb.org/t/p/w185";


    //Uri.Builder
    public static URL buildUrl(String tmdbSortOrder) {

        String sortBy;
        if (tmdbSortOrder == "popularity") { sortBy = sortByPopularity; }
        else { sortBy = sortByRating; }

        Uri builtUri =
                Uri.parse(TMDB_BASE_URL).buildUpon()
                        .appendPath(SEGMENT_DISCOVER)
                        .appendQueryParameter(PARAM_SORT, sortBy)
                        .appendQueryParameter(PARAM_API_KEY, apiKey)
                        .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");

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







