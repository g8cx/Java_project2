package com.example.money;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText amountInput;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(16, 16, 16, 16);

        amountInput = new EditText(this);
        amountInput.setHint("Введите сумму в RUB");
        layout.addView(amountInput);

        Button convertButton = new Button(this);
        convertButton.setText("Конвертировать");
        layout.addView(convertButton);

        resultView = new TextView(this);
        layout.addView(resultView);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        setContentView(layout);
    }

    private void convertCurrency() {
        String input = amountInput.getText().toString();

        // Проверка на пустой ввод
        if (input.isEmpty()) {
            resultView.setText("Введите сумму!");
            return;
        }

        // Проверка на корректный формат числа
        if (!isNumeric(input)) {
            Toast.makeText(this, "Неверный формат суммы!", Toast.LENGTH_SHORT).show();
            return;
        }

        double amountInRUB = Double.parseDouble(input);
        double exchangeRateU = 80.98; // Курс USD
        double exchangeRateE = 94.08; // Курс EUR
        double exchangeRateT = 0.15; // Курс KZT
        double exchangeRateY = 11.35; // Курс CNY

        double convertedAmountUSD = amountInRUB / exchangeRateU;
        double convertedAmountEUR = amountInRUB / exchangeRateE;
        double convertedAmountKZT = amountInRUB / exchangeRateT;
        double convertedAmountCNY = amountInRUB / exchangeRateY;

        String resultText = String.format("Сумма в USD: %.2f\nСумма в EUR: %.2f\nСумма в KZT: %.2f\nСумма в CNY: %.2f",
                convertedAmountUSD, convertedAmountEUR, convertedAmountKZT, convertedAmountCNY);
        resultView.setText(resultText);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

