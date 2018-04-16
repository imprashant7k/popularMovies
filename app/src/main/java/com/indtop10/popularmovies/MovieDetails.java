package com.indtop10.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private TextView tv_original_title;
    private ImageView iv_poster_image;
    private TextView tv_overview;
    private TextView tv_user_ratings;
    private TextView tv_release_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        tv_original_title = (TextView) findViewById(R.id.tv_original_title);
        iv_poster_image = (ImageView) findViewById(R.id.iv_poster_image);
        tv_overview = (TextView) findViewById(R.id.tv_overview);
        tv_user_ratings = (TextView) findViewById(R.id.tv_user_ratings);
        tv_release_date = (TextView) findViewById(R.id.tv_release_date);


        tv_original_title.setText(getIntent().getStringExtra("original_title"));
        iv_poster_image = (ImageView) findViewById(R.id.iv_poster_image);
        Picasso.get().load("http://image.tmdb.org/t/p/w500//"+getIntent().getStringExtra("poster_image")).into(iv_poster_image);
        tv_overview.setText(getIntent().getStringExtra("overview"));
        tv_user_ratings.setText(getIntent().getStringExtra("user_ratings"));
        tv_release_date.setText(getIntent().getStringExtra("release_date"));


    }
}
