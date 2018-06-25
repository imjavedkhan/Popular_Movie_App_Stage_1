package com.learn.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learn.movieapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final Context mContext;
    private MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private List<Movie> movieItems;

    public MovieAdapter(Context mContext, MovieAdapterOnClickHandler movieAdapterOnClickHandler){
        this.mContext = mContext;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movieItem);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieAdapterViewHolder holder, int position) {
        String url = NetworkUtils.BASE_IMAGE_URL + movieItems.get(position).getPosterPath();
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.movie_image);
    }

    @Override
    public int getItemCount() {
        if (null == movieItems) return 0;
        return movieItems.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView movie_image;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            this.movie_image = itemView.findViewById(R.id.image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieAdapterOnClickHandler.onClick(movieItems.get(getAdapterPosition()));
        }

    }

    public void setMovieData(List<Movie> movieData) {
        this.movieItems = movieData;
        notifyDataSetChanged();
    }
}
