package com.example.generates;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textViewIdea;
    private Button buttonGenerate;

    //  идеи тут
    private String[] ideas = {
            "Прогулка на свежем воздухе",
            "Чтение книги",
            "Посидеть в телефоне",
            "Послушать музыку",
            "Занятие спортом",
            "Рисование",
            "Изучение нового языка",
            "Покушать",
            "Подумать о смысле жизни",
            "Впасть в депрессию",
            "Поспать",
            "Посмотреть новый сериал",
            "Убраться во всем доме",
            "Пикник в парке",
            "Просмотр документального фильма",
            "Создание блога или влога",
            "Занятие йогой или медитацией"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIdea = findViewById(R.id.textViewIdea);
        buttonGenerate = findViewById(R.id.buttonGenerate);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateIdea();
            }
        });
    }

    // Метод для генерации случайной идеи
    private void generateIdea() {
        Random random = new Random();
        int index = random.nextInt(ideas.length);
        textViewIdea.setText(ideas[index]);
    }
}
