package com.example.subscription_tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView totalMonthlyTextView;
    private EditText nameEditText;
    private EditText priceEditText;
    private Button addSubscriptionButton;
    private ListView subscriptionsListView;

    private double totalMonthlyCost = 0.0;
    private ArrayList<String> subscriptionsList;
    private ArrayAdapter<String> subscriptionsAdapter;

    private static final String KEY_TOTAL_COST = "total_cost";
    private static final String KEY_SUBSCRIPTIONS = "subscriptions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalMonthlyTextView = findViewById(R.id.totalMonthlyTextView);
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        addSubscriptionButton = findViewById(R.id.addSubscriptionButton);
        subscriptionsListView = findViewById(R.id.subscriptionsListView);

        subscriptionsList = new ArrayList<>();

        if (savedInstanceState != null) {
            totalMonthlyCost = savedInstanceState.getDouble(KEY_TOTAL_COST, 0.0);
            ArrayList<String> savedSubscriptions = savedInstanceState.getStringArrayList(KEY_SUBSCRIPTIONS);
            if (savedSubscriptions != null) {
                subscriptionsList.addAll(savedSubscriptions);
            }
        } else {
            loadDataFromSharedPreferences();
        }

        subscriptionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subscriptionsList);
        subscriptionsListView.setAdapter(subscriptionsAdapter);

        updateTotalDisplay();

        addSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subscriptionName = nameEditText.getText().toString();
                String priceText = priceEditText.getText().toString();

                if (!subscriptionName.isEmpty() && !priceText.isEmpty()) {
                    try {
                        double subscriptionPrice = Double.parseDouble(priceText);
                        if (subscriptionPrice > 0) {
                            totalMonthlyCost += subscriptionPrice;
                            updateTotalDisplay();
                            addToSubscriptionsList(subscriptionName, subscriptionPrice);
                            nameEditText.setText("");
                            priceEditText.setText("");
                            saveDataToSharedPreferences();
                        } else {
                            showToast("Стоимость должна быть больше 0");
                        }
                    } catch (NumberFormatException e) {
                        showToast("Введите корректную стоимость");
                    }
                } else {
                    showToast("Заполните все поля");
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(KEY_TOTAL_COST, totalMonthlyCost);
        outState.putStringArrayList(KEY_SUBSCRIPTIONS, subscriptionsList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToSharedPreferences();
    }

    private void updateTotalDisplay() {
        String totalText = String.format(Locale.getDefault(), "%.0f ₽", totalMonthlyCost);
        totalMonthlyTextView.setText(totalText);
    }

    private void addToSubscriptionsList(String name, double price) {
        String subscriptionRecord = String.format(Locale.getDefault(), "%s - %.0f ₽/мес", name, price);
        subscriptionsList.add(0, subscriptionRecord);
        subscriptionsAdapter.notifyDataSetChanged();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveDataToSharedPreferences() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("SubscriptionData", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(KEY_TOTAL_COST, (float) totalMonthlyCost);
        editor.putInt("subscriptions_count", subscriptionsList.size());

        for (int i = 0; i < subscriptionsList.size(); i++) {
            editor.putString("subscription_" + i, subscriptionsList.get(i));
        }

        editor.apply();
    }

    private void loadDataFromSharedPreferences() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences("SubscriptionData", MODE_PRIVATE);

        totalMonthlyCost = sharedPreferences.getFloat(KEY_TOTAL_COST, 0.0f);

        int subscriptionsCount = sharedPreferences.getInt("subscriptions_count", 0);
        if (subscriptionsCount > 0) {
            subscriptionsList.clear();
            for (int i = 0; i < subscriptionsCount; i++) {
                String subscription = sharedPreferences.getString("subscription_" + i, "");
                if (!subscription.isEmpty()) {
                    subscriptionsList.add(subscription);
                }
            }
        }

        updateTotalDisplay();
    }
}