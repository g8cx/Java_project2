package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView balanceTextView;
    private TextView budgetRemainingTextView;
    private EditText incomeEditText;
    private EditText expenseEditText;
    private Spinner expenseCategorySpinner;
    private Button addIncomeButton;
    private Button addExpenseButton;

    private double balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTextView = findViewById(R.id.balanceTextView);
        budgetRemainingTextView = findViewById(R.id.budgetRemainingTextView);
        incomeEditText = findViewById(R.id.incomeEditText);
        expenseEditText = findViewById(R.id.expenseEditText);
        expenseCategorySpinner = findViewById(R.id.expenseCategorySpinner);
        addIncomeButton = findViewById(R.id.addIncomeButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);


        String[] expenseCategories = getResources().getStringArray(R.array.expense_categories); // Убедитесь, что массив определен в ресурсах
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, expenseCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(adapter);


        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String incomeInput = incomeEditText.getText().toString();
                if (!incomeInput.isEmpty()) {
                    double income = Double.parseDouble(incomeInput);
                    balance += income;
                    updateBalanceDisplay();
                    incomeEditText.setText("");
                }
            }
        });


        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseInput = expenseEditText.getText().toString();
                if (!expenseInput.isEmpty()) {
                    double expense = Double.parseDouble(expenseInput);
                    balance -= expense;
                    updateBalanceDisplay();
                    expenseEditText.setText(""); // Очистка поля ввода
                }
            }
        });
    }

    private void updateBalanceDisplay() {
        balanceTextView.setText("Баланс: " + balance);
        budgetRemainingTextView.setText("Остаток бюджета: " + balance);
    }
}
