package com.indtop10.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class MainActivity extends AppCompatActivity implements DownloadImages.AsyncResponse {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Movie> posters;
    private static final String KEY_FOR_PARCELABLE = "KEY_FOR_PARCELABLE";


    //Please add your API Keys here
    private final String URL_POPULAR_MOVIES = "http://api.themoviedb.org/3/movie/popular?";
    private final String URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?";

    // Saving RecyclerView State
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      outState.putParcelableArrayList(KEY_FOR_PARCELABLE, posters);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById(R.id.rcView);
        mRecyclerView.setHasFixedSize(true);

        //For Dynamically setting GridView Span count
        int mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext());

        mLayoutManager = new GridLayoutManager(getApplicationContext(),mNoOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(savedInstanceState != null)
        {
            posters = savedInstanceState.getParcelableArrayList(KEY_FOR_PARCELABLE);
            mAdapter = new MovieAdapter(posters,this);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {

            downloadImagesTask(URL_TOP_RATED);
        }

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


    //Populating results from DownloadImages AsyncTask
    @Override
    public void processFinish(String output) {

        posters = new ArrayList<>();
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
    private Boolean isNetworkAvailable()  {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();}

    private Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal==0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //Call to downloadImages if connectivity is available
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


