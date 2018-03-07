package com.yoursungjin.sjpopmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoursungjin.sjpopmovies.data.Result;
import com.yoursungjin.sjpopmovies.util.MovieDataUtil;

import java.util.List;


public class PopMoviesAdapter extends RecyclerView.Adapter<PopMoviesAdapter.PopMovieViewHolder> {

    private List<Result> movies;
    private Context context;

    public PopMoviesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public PopMovieViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_movie, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = ((RecyclerView) parent).getChildLayoutPosition(view);
                Result movie = movies.get(itemPosition);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_Id", movie.getId());
                context.startActivity(intent);
            }
        });
        return new PopMovieViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PopMovieViewHolder holder, int position) {
        Result movie = movies.get(position);
        ImageView movieImageView = holder.movieImageView;
        Picasso.with(movieImageView.getContext()).load(MovieDataUtil.posterURL(movie.getPosterPath())).into(movieImageView);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }


    public void setPopMoviesList(List<Result> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    public static class PopMovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movieImageView;

        public PopMovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
        }
    }
}
