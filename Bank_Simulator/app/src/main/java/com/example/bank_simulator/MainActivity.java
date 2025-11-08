package com.example.bank_simulator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView totalBalanceTextView;
    private EditText cardNameEditText, cardBalanceEditText, amountEditText;
    private Button addCardButton, addIncomeButton, addExpenseButton;
    private ListView cardsListView, historyListView;
    private Spinner cardsSpinner, categorySpinner;

    private double totalBalance = 1000.0;
    private ArrayList<BankCard> cardsList;
    private ArrayList<String> historyList;
    private ArrayAdapter<String> cardsAdapter, historyAdapter;
    private ArrayAdapter<String> cardsSpinnerAdapter;

    private SharedPreferences sharedPreferences;
    private Gson gson;

    class BankCard {
        String name;
        double balance;

        BankCard(String name, double balance) {
            this.name = name;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return name + ": " + balance + " ₽";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("BankData", MODE_PRIVATE);
        gson = new Gson();

        initViews();
        setupAdapters();
        loadData();
        setupClickListeners();
        updateAllDisplays();
    }

    private void initViews() {
        totalBalanceTextView = findViewById(R.id.totalBalanceTextView);
        cardNameEditText = findViewById(R.id.cardNameEditText);
        cardBalanceEditText = findViewById(R.id.cardBalanceEditText);
        amountEditText = findViewById(R.id.amountEditText);
        addCardButton = findViewById(R.id.addCardButton);
        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        cardsListView = findViewById(R.id.cardsListView);
        historyListView = findViewById(R.id.historyListView);
        cardsSpinner = findViewById(R.id.cardsSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
    }

    private void setupAdapters() {
        cardsList = new ArrayList<>();
        historyList = new ArrayList<>();

        cardsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        cardsListView.setAdapter(cardsAdapter);

        cardsSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        cardsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardsSpinner.setAdapter(cardsSpinnerAdapter);

        String[] categories = {"Зарплата", "Продукты", "Транспорт", "Развлечения", "Одежда", "Другое"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(historyAdapter);
    }

    private void loadData() {
        totalBalance = sharedPreferences.getFloat("totalBalance", 1000.0f);

        String cardsJson = sharedPreferences.getString("cardsList", "");
        if (!cardsJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<BankCard>>(){}.getType();
            ArrayList<BankCard> savedCards = gson.fromJson(cardsJson, type);
            if (savedCards != null) {
                cardsList.clear();
                cardsList.addAll(savedCards);
            }
        }

        String historyJson = sharedPreferences.getString("historyList", "");
        if (!historyJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> savedHistory = gson.fromJson(historyJson, type);
            if (savedHistory != null) {
                historyList.clear();
                historyList.addAll(savedHistory);
            }
        }

        if (cardsList.isEmpty()) {
            BankCard initialCard = new BankCard("Основная карта", 1000.0);
            cardsList.add(initialCard);
            addToHistory("Начальный баланс", 1000.0, true, "Основная карта");
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat("totalBalance", (float) totalBalance);

        String cardsJson = gson.toJson(cardsList);
        editor.putString("cardsList", cardsJson);

        String historyJson = gson.toJson(historyList);
        editor.putString("historyList", historyJson);

        editor.apply();
    }

    private void setupClickListeners() {
        addCardButton.setOnClickListener(v -> addNewCard());
        addIncomeButton.setOnClickListener(v -> addTransaction(true));
        addExpenseButton.setOnClickListener(v -> addTransaction(false));
    }

    private void addNewCard() {
        String cardName = cardNameEditText.getText().toString();
        String balanceText = cardBalanceEditText.getText().toString();

        if (cardName.isEmpty() || balanceText.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double balance = Double.parseDouble(balanceText);
            BankCard newCard = new BankCard(cardName, balance);
            cardsList.add(newCard);
            totalBalance += balance;

            updateAllDisplays();
            cardNameEditText.setText("");
            cardBalanceEditText.setText("");
            saveData();

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка ввода", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTransaction(boolean isIncome) {
        if (cardsList.isEmpty()) {
            Toast.makeText(this, "Сначала добавьте карту", Toast.LENGTH_SHORT).show();
            return;
        }

        String amountText = amountEditText.getText().toString();
        if (amountText.isEmpty()) {
            Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                Toast.makeText(this, "Сумма должна быть больше 0", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedCardPosition = cardsSpinner.getSelectedItemPosition();
            if (selectedCardPosition < 0) selectedCardPosition = 0;

            String selectedCategory = categorySpinner.getSelectedItem().toString();
            BankCard selectedCard = cardsList.get(selectedCardPosition);

            if (isIncome) {
                selectedCard.balance += amount;
                totalBalance += amount;
                addToHistory(selectedCategory, amount, true, selectedCard.name);
            } else {
                if (selectedCard.balance >= amount) {
                    selectedCard.balance -= amount;
                    totalBalance -= amount;
                    addToHistory(selectedCategory, amount, false, selectedCard.name);
                } else {
                    Toast.makeText(this, "Недостаточно средств", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            updateAllDisplays();
            amountEditText.setText("");
            saveData();

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка ввода", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAllDisplays() {
        updateCardsDisplay();
        updateTotalBalance();
        historyAdapter.notifyDataSetChanged();
    }

    private void updateCardsDisplay() {
        cardsAdapter.clear();
        cardsSpinnerAdapter.clear();

        for (BankCard card : cardsList) {
            cardsAdapter.add(card.toString());
            cardsSpinnerAdapter.add(card.name);
        }
    }

    private void updateTotalBalance() {
        totalBalanceTextView.setText(totalBalance + " ₽");
    }

    private void addToHistory(String category, double amount, boolean isIncome, String cardName) {
        String time = new SimpleDateFormat("dd.MM HH:mm", Locale.getDefault()).format(new Date());
        String sign = isIncome ? "+" : "-";
        String record = sign + " " + amount + " ₽ | " + category + " | " + cardName + " | " + time;
        historyList.add(0, record);
    }
}