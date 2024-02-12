package com.example.kirillina;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private List<FilmModel> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button2);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        films = new ArrayList<>();
        filmAdapter = new FilmAdapter(films);
        recyclerView.setAdapter(filmAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Favorite.class);
                startActivity(intent);
                finish();
            }
        });


        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://kinopoiskapiunofficial.tech/api/v2.2/films/collections?type=TOP_POPULAR_MOVIES&page=1\r\n" + "")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();

                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                intent.putExtra("error_message", "Произошла ошибка при загрузке данных, проверьте подключение к сети");
                startActivity(intent);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parseAndDisplayMovies(myResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
    }

    private void parseAndDisplayMovies(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray moviesArray = jsonObject.getJSONArray("items");

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            String movieTitle = movieObject.getString("nameRu");
            JSONArray genresArray = movieObject.getJSONArray("genres");
            List<String> movieGenres = new ArrayList<>();
            for (int j = 0; j < genresArray.length(); j++) {
                JSONObject genreObject = genresArray.getJSONObject(j);
                movieGenres.add(genreObject.getString("genre"));
            }
            String movieYear = movieObject.getString("year");
            String moviePosterUrl = movieObject.getString("posterUrl");

            JSONArray countriesArray = movieObject.getJSONArray("countries");
            List<String> movieCountries = new ArrayList<>();
            for (int j = 0; j < countriesArray.length(); j++) {
                JSONObject countryObject = countriesArray.getJSONObject(j);
                movieCountries.add(countryObject.getString("country"));
            }

            FilmModel film = new FilmModel(movieTitle, movieYear, moviePosterUrl, movieGenres, movieCountries);
            films.add(film);
        }

        filmAdapter.notifyDataSetChanged();
    }
}
