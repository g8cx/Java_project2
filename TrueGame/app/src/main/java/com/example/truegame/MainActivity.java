package com.example.truegame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button truthButton, dareButton;

    private String[] truths = {
            "Кого вы собираетесь отчислить?",
            "Почему любимые ученики делают не больше оставьной части класса?",
            "Поставите 5 группе за креатив?",
            "Какой ваш любимый монстр из преисподни?",
            "Что для вас наше человеческое право?",
            "Кто вы по знаку зодиака?",
            "За что вы так с нами господин?",
            "Есть ли у вас любимое животное?",
            "Как вы проявляете радость?",
            "За что вы заморозили Сашеньку в тг?"
    };

    private String[] dares = {
            "Переназови гриба в Папа Гриб",
            "Дотронься до носа",
            "Напой любимою песню",
            "Пройди 10 шагов вперёд",
            "Посмотри мультфильмы_фильмы дисней за последнее время ",
            "Проговори скороговорку  Шла Саша по шоссе и сосала сушку ",
            "Выйди на улицу и обними первого встречного.",
            "Станцуй для любого в комнате.",
            "Попробуй достать кончиком языка до носа",
            "Пусть твой сосед справа напишет пост на твоей странице в соцсети."
    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", textView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedText = savedInstanceState.getString("text");
        if (savedText != null) {
            textView.setText(savedText);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        truthButton = findViewById(R.id.truthButton);
        dareButton = findViewById(R.id.dareButton);

        Random random = new Random();

        truthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = random.nextInt(truths.length);
                textView.setText("Правда: " + truths[index]);
            }
        });

        dareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = random.nextInt(dares.length);
                textView.setText("Действие: " + dares[index]);
            }
        });
    }
}
