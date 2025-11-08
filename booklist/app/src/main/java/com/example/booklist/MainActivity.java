package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText bookInput;
    private Button addButton, deleteButton;
    private ListView listView;
    private TextView countView;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> books = new ArrayList<>();

    private static final String PREFS_NAME = "BookPrefs";
    private static final String KEY_BOOKS = "books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookInput = findViewById(R.id.bookInput);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);
        countView = findViewById(R.id.countView);

        loadBooks();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, books);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        updateCount();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = bookInput.getText().toString().trim();
                if (!bookName.isEmpty()) {
                    books.add(bookName);
                    adapter.notifyDataSetChanged();
                    bookInput.setText("");
                    updateCount();
                    saveBooks();
                } else {
                    Toast.makeText(MainActivity.this, "Введите название книги", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = listView.getCheckedItemPosition();
                if (pos != ListView.INVALID_POSITION) {
                    books.remove(pos);
                    adapter.notifyDataSetChanged();
                    listView.clearChoices();
                    updateCount();
                    saveBooks();
                } else {
                    Toast.makeText(MainActivity.this, "Выберите книгу для удаления", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateCount() {
        countView.setText("Всего книг: " + books.size());
    }

    private void saveBooks() {
        JSONArray jsonArray = new JSONArray();
        for (String book : books) {
            jsonArray.put(book);
        }
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(KEY_BOOKS, jsonArray.toString())
                .apply();
    }

    private void loadBooks() {
        String json = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getString(KEY_BOOKS, null);
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    books.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("books", books);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        books = savedInstanceState.getStringArrayList("books");
        if (books != null) {
            adapter.clear();
            adapter.addAll(books);
            updateCount();
        }
    }
}
