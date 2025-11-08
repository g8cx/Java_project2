package com.example.sovet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView adviceTextView;
    private Button adviceButton;
    private String[] advices = {
            "Не бойся начать сначала — это шанс построить что-то лучше.",
            "Иногда тишина — лучший ответ.",
            "Делай сегодня то, о чём завтра скажешь себе спасибо.",
            "Сохраняй спокойствие. Всё приходит в своё время.",
            "Улыбка — самый короткий путь к сердцу человека.",
            "Не жди идеального момента, создай его сам."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adviceTextView = findViewById(R.id.adviceTextView);
        adviceButton = findViewById(R.id.adviceButton);

        adviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int index = random.nextInt(advices.length);
                adviceTextView.setText(advices[index]);
            }
        });
    }
}