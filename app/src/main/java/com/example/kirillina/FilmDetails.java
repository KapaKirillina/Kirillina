package com.example.kirillina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FilmDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        ImageView back = findViewById(R.id.back);

        // Получение данных о фильме из Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("film_title");
        String genres = intent.getStringExtra("film_genres");
        String year = intent.getStringExtra("film_year");
        String posterUrl = intent.getStringExtra("film_poster_url");
        String countries = intent.getStringExtra("film_countries");

        // Отображение данных о фильме
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView genresTextView = findViewById(R.id.genresTextView);
        ImageView posterImageView = findViewById(R.id.posterImageView);
        TextView countriesTextView = findViewById(R.id.countriesTextView);


        titleTextView.setText(title);
        genresTextView.setText("Жанры: " + genres);
        countriesTextView.setText("Страна: " + countries);


        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.img)
                .into(posterImageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilmDetails.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
}