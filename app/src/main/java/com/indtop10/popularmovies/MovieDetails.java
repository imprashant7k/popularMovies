package com.indtop10.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView tv_original_title;
        ImageView iv_poster_image;
        TextView tv_overview;
        TextView tv_user_ratings;
        TextView tv_release_date;


        tv_original_title = findViewById(R.id.tv_original_title);
        iv_poster_image = findViewById(R.id.iv_poster_image);
        tv_overview = findViewById(R.id.tv_overview);
        tv_user_ratings =  findViewById(R.id.tv_user_ratings);
        tv_release_date =  findViewById(R.id.tv_release_date);


        tv_original_title.setText(getIntent().getStringExtra("original_title"));
        Picasso.get().load("http://image.tmdb.org/t/p/w500//"+getIntent().getStringExtra("poster_image")).into(iv_poster_image);
        tv_overview.setText(getIntent().getStringExtra("overview"));
        tv_user_ratings.setText(getIntent().getStringExtra("user_ratings"));
        tv_release_date.setText(getIntent().getStringExtra("release_date"));

    }
}
