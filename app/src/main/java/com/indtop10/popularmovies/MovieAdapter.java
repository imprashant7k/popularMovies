package com.indtop10.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movies;

    private Context cx;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView textView;


        public MyViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            Movie movie = movies.get(position);

            Intent intent = new Intent(cx,MovieDetails.class);

            intent.putExtra("original_title",movie.getOriginal_title());
            intent.putExtra("poster_image",movie.getPoster_path());
            intent.putExtra("overview",movie.getOverview());
            intent.putExtra("user_ratings",movie.getVote_avg());
            intent.putExtra("release_date",movie.getRelease_date());

            cx.startActivity(intent);
        }
    }

    public MovieAdapter(List<Movie> movies,Context cx){

        this.movies = movies;
        this.cx = cx;

    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {

        Movie movie = movies.get(position);
        String poster_path = movie.getPoster_path();
        Picasso.get().load("http://image.tmdb.org/t/p/w500//"+poster_path).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();

    }

}
