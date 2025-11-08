package com.example.kino2;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {



        private MovieAdapter adapter;
        private ArrayList<Movie> movies = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Создаем список фильмов
            movies.add(new Movie("inter", "Фантастика", "2014", R.drawable.movie1));
            movies.add(new Movie("Начало", "Триллер", "2010", R.drawable.movie2));
            movies.add(new Movie("Крестный отец", "Криминал", "1972", R.drawable.movie3));
            movies.add(new Movie("Форрест Гамп", "Драма", "1994", R.drawable.movie4));
            movies.add(new Movie("Матрица", "Фантастика", "1999", R.drawable.movie5));

            // Настраиваем адаптер
            adapter = new MovieAdapter(this, movies);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            // Поиск
            SearchView searchView = findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) { return false; }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return true;
                }
            });
        }
    }