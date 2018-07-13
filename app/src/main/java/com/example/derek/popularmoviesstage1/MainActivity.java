package com.example.derek.popularmoviesstage1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.derek.popularmoviesstage1.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieViewAdapter mAdapter;
    private RecyclerView mMoviesList;

    String sortOrder = "popularity";



    String URL_JSON = "http://api.themoviedb.org/3/movie/popular?api_key=f075*******";
    JsonArrayRequest ObjectRequest;
    RequestQueue requestQueue;
    List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = findViewById(R.id.rv_movies);
        makeTMDBQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_sort){
            Context context = MainActivity.this;
            sortOrder = "popularity";
            Toast.makeText(context, "Sorting by "+ sortOrder, Toast.LENGTH_SHORT).show();
            makeTMDBQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void makeTMDBQuery() {
        sortOrder = "popularity";
        URL tmdbDiscoverUrl = NetworkUtils.buildUrl(sortOrder);
        String mJSONResponse = tmdbDiscoverUrl.toString();
        new TMDBTask().execute(tmdbDiscoverUrl);
    }

    public class TMDBTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL discoverUrl = urls[0];
            String tmdbDiscoverResults = null;
            try {
                tmdbDiscoverResults = NetworkUtils.getResponseFromHttpUrl(discoverUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmdbDiscoverResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                //do nothing
            }
            super.onPostExecute(s);
        }
    }

    public void parseJSON(String jsonUrl) throws JSONException {

        JSONObject rootObject = new JSONObject(jsonUrl);
        JSONArray moviesArray = rootObject.getJSONArray("results");

        for (int i=0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String title = movieObject.getString("title");
            String date = movieObject.getString("release_date");
            String vote = movieObject.getString("vote_average");
            String plot = movieObject.getString("plot_synopsis");
            String path = movieObject.getString("poster_path");
            Movie movie = new Movie(title, date, path, vote, plot);
            movieList.add(movie);
        }
        setManagerAdapter(movieList);

    }


public void setManagerAdapter (List<Movie> list){

    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    mMoviesList.setLayoutManager(layoutManager);
    mMoviesList.setHasFixedSize(true);

    mAdapter = new MovieViewAdapter(list);
    mMoviesList.setAdapter(mAdapter);
}

}
