package com.example.kirillina;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<FilmModel> films;

    public FilmAdapter(List<FilmModel> films) {
        this.films = films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilmModel film = films.get(position);
        holder.bind(film);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FilmDetails.class);
                intent.putExtra("film_title", film.getTitle());
                intent.putExtra("film_genres", TextUtils.join(", ", film.getGenres()));
                intent.putExtra("film_year", film.getYear());
                intent.putExtra("film_poster_url", film.getPosterUrl());
                intent.putExtra("film_countries", TextUtils.join(", ", film.getCountries()));

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView countriesTextView;
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView genresTextView;
        private TextView yearTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.title);
            genresTextView = itemView.findViewById(R.id.genre);
            yearTextView = itemView.findViewById(R.id.year);
            countriesTextView = itemView.findViewById(R.id.country);
        }

        public void bind(FilmModel film) {
            titleTextView.setText(film.getTitle());
            genresTextView.setText(formatGenres(film.getGenres()));
            yearTextView.setText("(" + film.getYear() + ")");

            Glide.with(itemView.getContext())
                    .load(film.getPosterUrl())
                    .placeholder(R.drawable.img)
                    .into(posterImageView);
            countriesTextView.setText(formatCountries(film.getCountries()));
        }
        private String formatGenres(List<String> genres) {
            StringBuilder genresStringBuilder = new StringBuilder();
            for (int i = 0; i < Math.min(genres.size(), 2); i++) {
                genresStringBuilder.append(genres.get(i)).append(", ");
            }
            if (genresStringBuilder.length() > 0) {
                genresStringBuilder.deleteCharAt(genresStringBuilder.length() - 2);
            }
            return genresStringBuilder.toString();
        }

        private String formatCountries(List<String> countries) {
            StringBuilder countiresStringBuilder = new StringBuilder();
            for (int i = 0; i < Math.min(countries.size(), 2); i++) {
                countiresStringBuilder.append(countries.get(i)).append(", ");
            }
            if (countiresStringBuilder.length() > 0) {
                countiresStringBuilder.deleteCharAt(countiresStringBuilder.length() - 2);
            }
            return countiresStringBuilder.toString();
        }
    }
}

