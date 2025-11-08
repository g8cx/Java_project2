package com.example.my_view_list;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView totalEpisodesTextView, ratingText;
    private EditText titleEditText, episodesEditText;
    private Button addWatchedButton;
    private ListView historyListView;
    private Spinner categorySpinner;
    private RatingBar ratingBar;

    private int totalEpisodesWatched = 0;
    private ArrayList<WatchItem> watchHistoryList;
    private ArrayAdapter<String> historyAdapter;
    private ArrayAdapter<String> categoryAdapter;

    private static final String KEY_TOTAL_EPISODES = "total_episodes";
    private static final String KEY_HISTORY = "watch_history";

    // Делаем класс статическим
    static class WatchItem {
        String title;
        int episodes;
        String category;
        int rating;
        String date;

        WatchItem(String title, int episodes, String category, int rating, String date) {
            this.title = title;
            this.episodes = episodes;
            this.category = category;
            this.rating = rating;
            this.date = date;
        }

        String toDisplayString() {
            String stars = "⭐".repeat(rating) + "☆".repeat(5 - rating);
            return String.format(Locale.getDefault(), "%s - %d серий\n%s | %s | %s",
                    title, episodes, category, stars, date);
        }

        String toSaveString() {
            return title + "|" + episodes + "|" + category + "|" + rating + "|" + date;
        }

        static WatchItem fromString(String data) {
            String[] parts = data.split("\\|");
            if (parts.length == 5) {
                String title = parts[0];
                int episodes = Integer.parseInt(parts[1]);
                String category = parts[2];
                int rating = Integer.parseInt(parts[3]);
                String date = parts[4];
                return new WatchItem(title, episodes, category, rating, date);
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupAdapters();

        if (savedInstanceState != null) {
            restoreDataFromBundle(savedInstanceState);
        } else {
            loadDataFromSharedPreferences();
        }

        updateTotalDisplay();
        updateHistoryDisplay();
        setupListeners();
    }

    private void initViews() {
        totalEpisodesTextView = findViewById(R.id.totalEpisodesTextView);
        titleEditText = findViewById(R.id.titleEditText);
        episodesEditText = findViewById(R.id.episodesEditText);
        addWatchedButton = findViewById(R.id.addWatchedButton);
        historyListView = findViewById(R.id.historyListView);
        categorySpinner = findViewById(R.id.categorySpinner);
        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.ratingText);
    }

    private void setupAdapters() {
        watchHistoryList = new ArrayList<>();

        String[] categories = {"Смотрю", "Просмотрено", "Планирую", "Брошено", "Любимое"};
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        historyListView.setAdapter(historyAdapter);
    }

    private void setupListeners() {
        addWatchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWatchedItem();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText(String.format(Locale.getDefault(), "%.0f/5", rating));
            }
        });

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WatchItem item = watchHistoryList.get(position);
                String message = String.format(Locale.getDefault(),
                        "%s\n%d серий | %s | Рейтинг: %d/5",
                        item.title, item.episodes, item.category, item.rating);
                showToast(message);
            }
        });
    }

    private void addWatchedItem() {
        String title = titleEditText.getText().toString();
        String episodesText = episodesEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        int rating = (int) ratingBar.getRating();

        if (!title.isEmpty() && !episodesText.isEmpty()) {
            try {
                int episodes = Integer.parseInt(episodesText);
                if (episodes > 0) {
                    totalEpisodesWatched += episodes;

                    String currentTime = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
                    WatchItem newItem = new WatchItem(title, episodes, category, rating, currentTime);
                    watchHistoryList.add(0, newItem);

                    updateTotalDisplay();
                    updateHistoryDisplay();
                    clearInputFields();
                    saveDataToSharedPreferences();

                } else {
                    showToast("Количество серий должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                showToast("Введите корректное число");
            }
        } else {
            showToast("Заполните все поля");
        }
    }

    private void updateTotalDisplay() {
        String totalText = String.format(Locale.getDefault(), "%d", totalEpisodesWatched);
        totalEpisodesTextView.setText(totalText);
    }

    private void updateHistoryDisplay() {
        ArrayList<String> displayList = new ArrayList<>();
        for (WatchItem item : watchHistoryList) {
            displayList.add(item.toDisplayString());
        }
        historyAdapter.clear();
        historyAdapter.addAll(displayList);
        historyAdapter.notifyDataSetChanged();
    }

    private void clearInputFields() {
        titleEditText.setText("");
        episodesEditText.setText("");
        ratingBar.setRating(3);
        ratingText.setText("3/5");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void restoreDataFromBundle(Bundle savedInstanceState) {
        totalEpisodesWatched = savedInstanceState.getInt(KEY_TOTAL_EPISODES, 0);

        ArrayList<String> savedHistory = savedInstanceState.getStringArrayList(KEY_HISTORY);
        if (savedHistory != null) {
            watchHistoryList.clear();
            for (String savedItem : savedHistory) {
                WatchItem item = WatchItem.fromString(savedItem);
                if (item != null) {
                    watchHistoryList.add(item);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TOTAL_EPISODES, totalEpisodesWatched);

        ArrayList<String> historyData = new ArrayList<>();
        for (WatchItem item : watchHistoryList) {
            historyData.add(item.toSaveString());
        }
        outState.putStringArrayList(KEY_HISTORY, historyData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToSharedPreferences();
    }

    private void saveDataToSharedPreferences() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("WatchTrackerData", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_TOTAL_EPISODES, totalEpisodesWatched);
        editor.putInt("history_count", watchHistoryList.size());

        for (int i = 0; i < watchHistoryList.size(); i++) {
            editor.putString("history_" + i, watchHistoryList.get(i).toSaveString());
        }

        editor.apply();
    }

    private void loadDataFromSharedPreferences() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("WatchTrackerData", MODE_PRIVATE);

        totalEpisodesWatched = sharedPreferences.getInt(KEY_TOTAL_EPISODES, 0);

        int historyCount = sharedPreferences.getInt("history_count", 0);
        if (historyCount > 0) {
            watchHistoryList.clear();
            for (int i = 0; i < historyCount; i++) {
                String savedItem = sharedPreferences.getString("history_" + i, "");
                if (!savedItem.isEmpty()) {
                    WatchItem item = WatchItem.fromString(savedItem);
                    if (item != null) {
                        watchHistoryList.add(item);
                    }
                }
            }
        }
    }
}