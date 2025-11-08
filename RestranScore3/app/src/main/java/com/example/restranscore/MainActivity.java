package com.example.restranscore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dishes = new ArrayList<>();
    private ArrayList<Double> prices = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private double totalSum = 0.0;


    private static final String KEY_DISHES = "saved_dishes";
    private static final String KEY_PRICES = "saved_prices";
    private static final String KEY_TOTAL = "saved_total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editDish = findViewById(R.id.editDish);
        EditText editPrice = findViewById(R.id.editPrice);
        Button addButton = findViewById(R.id.btnAdd);
        TextView totalText = findViewById(R.id.textTotal);
        ListView listView = findViewById(R.id.listView);

        if (savedInstanceState != null) {
            dishes = savedInstanceState.getStringArrayList(KEY_DISHES);
            totalSum = savedInstanceState.getDouble(KEY_TOTAL, 0.0);

            double[] savedPrices = savedInstanceState.getDoubleArray(KEY_PRICES);
            if (savedPrices != null) {
                prices.clear();
                for (double price : savedPrices) {
                    prices.add(price);
                }
            }

            if (dishes == null) {
                dishes = new ArrayList<>();
            }
            if (prices == null) {
                prices = new ArrayList<>();
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dishes);
        listView.setAdapter(adapter);

        totalText.setText("Сумма: " + String.format("%.2f ₽", totalSum));

        addButton.setOnClickListener(v -> {
            String dish = editDish.getText().toString().trim();
            String priceStr = editPrice.getText().toString().trim();

            if (dish.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Введите блюдо и цену", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Неверная цена", Toast.LENGTH_SHORT).show();
                return;
            }

            dishes.add(dish + " — " + String.format("%.2f ₽", price));
            prices.add(price);
            adapter.notifyDataSetChanged();

            totalSum += price;
            totalText.setText("Сумма: " + String.format("%.2f ₽", totalSum));

            editDish.setText("");
            editPrice.setText("");
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            double price = prices.get(position);
            totalSum -= price;
            totalText.setText("Сумма: " + String.format("%.2f ₽", totalSum));

            dishes.remove(position);
            prices.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Блюдо удалено", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList(KEY_DISHES, dishes);

        double[] priceArray = new double[prices.size()];
        for (int i = 0; i < prices.size(); i++) {
            priceArray[i] = prices.get(i);
        }
        outState.putDoubleArray(KEY_PRICES, priceArray);

        outState.putDouble(KEY_TOTAL, totalSum);
    }
}