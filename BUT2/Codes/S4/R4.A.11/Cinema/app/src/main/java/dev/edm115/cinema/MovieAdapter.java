package dev.edm115.cinema;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Cinema> movies;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView, directorTextView, durationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_movie);
            titleTextView = itemView.findViewById(R.id.text_movie_title);
            directorTextView = itemView.findViewById(R.id.text_movie_director);
            durationTextView = itemView.findViewById(R.id.text_movie_duration);
        }
    }

    public MovieAdapter(Context context, ArrayList<Cinema> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cinema movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.directorTextView.setText(movie.getDirector());
        holder.durationTextView.setText(String.format(Locale.US, "%d mins", movie.getDuration()));
        Glide.with(context).load(movie.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
        // Set up onClickListener for zooming image.
        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, FullscreenImageActivity.class);
            intent.putExtra("image_url", movie.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Cinema getItem(int position) {
        return movies.get(position);
    }
}
