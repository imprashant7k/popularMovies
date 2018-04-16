package com.indtop10.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadImages.AsyncResponse {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String output,url;
    private List<Movie> posters;
    private Parcelable mListState;
    private String LIST_STATE_KEY;

    //Please add your API Keys here
    private final String URL_POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular?";
    private final String URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?";

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        mListState = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if(state != null)
            mListState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.rcView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        downloadImagesTask(URL_TOP_RATED);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =getMenuInflater();

        menuInflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.popularMovies)
        {
            downloadImagesTask(URL_POPULAR_MOVIES);
        }

        else{

            downloadImagesTask(URL_TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void processFinish(String output) {

        posters = new ArrayList<>();
        this.output = output;
        if(output != null)
        {
            try{
                JSONObject jsonObject = new JSONObject(output);
                JSONArray results = jsonObject.getJSONArray("results");

                for(int i=0;i<results.length();i++)
                {
                    JSONObject poster = results.getJSONObject(i);
                    Movie movie = new Movie();

                    movie.setOriginal_title(poster.getString("original_title"));
                    movie.setPoster_path(poster.getString("poster_path"));
                    movie.setOverview(poster.getString("overview"));
                    movie.setVote_avg(poster.getString("vote_average"));
                    movie.setRelease_date(poster.getString("release_date"));

                    posters.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        mAdapter = new MovieAdapter(posters,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    //To check internet connectivity is available
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();}

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void downloadImagesTask(String url){

        if(isNetworkAvailable() && isOnline())
        {
            DownloadImages downloadImages = new DownloadImages(this,url);
            downloadImages.execute();
        }
        else{

            Toast.makeText(getApplicationContext(),"Internet Connection Not Available",Toast.LENGTH_LONG).show();
        }

    }

}
