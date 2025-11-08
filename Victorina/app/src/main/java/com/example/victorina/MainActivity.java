package com.example.victorina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView questionText;
    private RadioGroup radioGroup;
    private Button submitButton;
    private Button restartButton; // Кнопка для перезапуска викторины
    private TextView resultText;

    private String[] questions = {
            "Какой язык программирования используется для разработки Android приложений?",
            "Какой метод используется для запуска приложения в Android?",
            "Какой самый популярный жанр игр в мире?",
            "Что не является языком программирования?",
            "Сколько часов нужно спать программисту?",
            "В чем смысл жизни?"
    };

    private String[][] options = {
            {"Java", "Python", "C++", "JavaScript"},
            {"onCreate", "start", "init", "launch"},
            {"Шутеры", "Стратегии", "Инди", "MOBA"},
            {"Java", "Python", "Dota 2", "C++"},
            {"2", "6", "Спать?", "8"},
            {"В программировании", "В грибах", "В деньгах", "В любви"}
    };

    // Индексы правильных ответов для всех вопросов
    private int[] correctAnswers = {0, 0, 0, 2, 2, 0};

    private int currentQuestionIndex = 0; // Индекс текущего вопроса
    private int score = 0; // Счетчик правильных ответов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Создание основного LinearLayout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Создание TextView для вопроса
        questionText = new TextView(this);
        layout.addView(questionText);

        // Создание RadioGroup для вариантов ответов
        radioGroup = new RadioGroup(this);
        layout.addView(radioGroup);

        // Создание кнопки для отправки ответа
        submitButton = new Button(this);
        submitButton.setText("Отправить");
        layout.addView(submitButton);

        // Создание TextView для результата
        resultText = new TextView(this);
        layout.addView(resultText);

        // Создание кнопки для перезапуска викторины
        restartButton = new Button(this);
        restartButton.setText("Пройти еще раз");
        restartButton.setVisibility(View.GONE); // Скрываем кнопку изначально
        layout.addView(restartButton);

        setContentView(layout);

        loadQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    int selectedIndex = radioGroup.indexOfChild(selectedRadioButton);

                    if (selectedIndex == correctAnswers[currentQuestionIndex]) {
                        score++; // Увеличиваем счетчик правильных ответов
                        resultText.setText("Правильно!");
                    } else {
                        resultText.setText("Неправильно! Правильный ответ: " + options[currentQuestionIndex][correctAnswers[currentQuestionIndex]]);
                    }

                    // Переход к следующему вопросу
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.length) {
                        loadQuestion();
                    } else {
                        resultText.setText("Викторина завершена! Правильные ответы: " + score + "/" + questions.length);
                        submitButton.setEnabled(false); // Отключаем кнопку после завершения
                        restartButton.setVisibility(View.VISIBLE); // Показываем кнопку перезапуска
                    }
                } else {
                    resultText.setText("Пожалуйста, выберите ответ.");
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetQuiz(); // Сброс состояния викторины
            }
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestionIndex]);
        radioGroup.removeAllViews(); // Удаляем старые RadioButton

        for (int i = 0; i < options[currentQuestionIndex].length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options[currentQuestionIndex][i]);
            radioGroup.addView(radioButton);
        }
        resultText.setText(""); // Сброс текста результата
    }

    private void resetQuiz() {
        currentQuestionIndex = 0; // Сбрасываем индекс текущего вопроса
        score = 0; // Сбрасываем счетчик правильных ответов
        submitButton.setEnabled(true); // Включаем кнопку отправки
        restartButton.setVisibility(View.GONE); // Скрываем кнопку перезапуска
        loadQuestion(); // Загружаем первый вопрос
    }
}


