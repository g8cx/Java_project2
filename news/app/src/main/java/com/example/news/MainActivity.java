package com.example.news;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private Spinner citySpinner;
    private List<Event> allEvents;
    private List<Event> filteredEvents;
    private String[] cities = {"Все города", "Москва", "Тверь", "Санкт-Петербург"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupEvents();
        setupSpinner();
        setupRecyclerView();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.eventsRecyclerView);
        citySpinner = findViewById(R.id.citySpinner);
    }

    private void setupEvents() {
        allEvents = new ArrayList<>();

        allEvents.add(new Event(
                "«Цирковой квартал»",
                " Открытый Всероссийский цирковой фестиваль-конкурс «Цирковой квартал»!»",
                "08.11.2025",
                "с 10:00.",
                "Дворец культуры «Синтетик», Тверь, посёлок Химинститута, 31",
                "Тверь"
        ));

        allEvents.add(new Event(
                "Концерт",
                "Концертная программа \"Русская душа в музыке\", в исполнении пианистки Илоны Мазан",
                "08.11.2025",
                "в 15:00",
                "Стадион Лужники",
                "Тверь"
        ));

        allEvents.add(new Event(
                "Ярмарка ремесел",
                "Мастер-класс по изготовлению осеннего декора",
                "07.11.2025",
                "в 12:00.",
                "Тверская областная библиотека им. А.С. Пушкина, Тверь, ул. Советская, д. 64",
                "Тверь"
        ));

        allEvents.add(new Event(
                "65-лет",
                "Праздничное мероприятие к 65 -летнему юбилею», в исполнении Алексея Зинатулина",
                "08.11.2025",
                "в 16:00.",
                "Библиотека семейного чтения им. Евгения Сигарёва, Тверь, Кольцевая улица, 76",
                "Тверь"
        ));

        allEvents.add(new Event(
                "Судьба и Родина — едины!",
                "Праздничная программа «Судьба и Родина — едины!», посвящённая Дню народного единства",
                "04.11.2025",
                "в 16:00.",
                "МБУ \"Дворец культуры поселка Литвинки\", п. Литвинки, 22",
                "Тверь"
        ));

        allEvents.add(new Event(
                "Юна-Фест",
                "Выставка-пристройство кошек и собак из приюта «Юна-Фест»",
                "15.11.2025",
                "в 12:00",
                "в ТРЦ МЕГА Белая Дача, 1-й Покровский пр., 5",
                "Москва"
        ));

        allEvents.add(new Event(
                "БЕСПЛАТНОЕ посещение",
                "день бесплатного посещения музея Космонавтики на ВДНХ и дома-музея академика С.П. Королёва.1",
                "13.11.2025",
                "в 10:00 — 21:00.",
                "Проспект Мира, дом 111",
                "Москва"
        ));


        allEvents.add(new Event(
                "Бесплатная экскурсия",
                "Жемчужины Тверского района: Кузнецкий, Столешников, Камергерский",
                "21.11.2025",
                "в 12:00",
                "в ТРЦ МЕГА Белая Дача, 1-й Покровский пр., 5",
                "Москва"
        ));


        allEvents.add(new Event(
                "Фонтан любви, фонтан живой",
                "состоится концертная программа хоровой музыки «Фонтан любви, фонтан живой»",
                "7.11.2025",
                "с 19:00 до 20:30",
                " ул. Летняя, дом 1, стр.1",
                "Москва"
        ));


        allEvents.add(new Event(
                "Корпоративный турнир",
                "Корпоративный турнир Правительства Москвы 2025. StarCraft II 1х1",
                "12.11.2025",
                "в 23:55",
                "\tOnline",
                "Москва"
        ));

        allEvents.add(new Event(
                "Выставка «Анхела Копелло. Совершенство» в Эрарте",
                "Бескрайняя мощь и величие природы предстаёт на полотнах аргентинской фотохудожницы Анхелы Копелло (посещение музея целый год — 1250 рублей (при оформлении на кассе))",
                "17.10.2025 – 15 февраля 2026",
                "пн, ср–вс 11:00–23:00",
                "29-я линия В. О., д. 2",
                "Санкт-Петербург"
        ));


        allEvents.add(new Event(
                "Иммерсивная инсталляция «Пропавшие в кинохронике» 6+",
                "ПОЛНОЕ ПОГРУЖЕНИЕ В ИСТОРИЮ! «Пропавшие в кинохронике» — уникальный рассказ о кинодокументалистах ХХ века от команды «Невский баталист».(550 рублей)",
                "до 02.01.2026",
                "вт–вс 12:00–20:00",
                "Каменноостровский просп, д. 10 литера",
                "Санкт-Петербург"
        ));

        allEvents.add(new Event(
                "Выставка живописи Дмитрия Кустановича 0+",
                "В Галерее Кустановича в постоянном режиме работает выставка картин мастера пространственного реализма.",
                "Круглый год",
                "Круглый год",
                "ул. Б. Конюшенная, д. 11 (второй двор Капеллы)",
                "Санкт-Петербург"
        ));

        allEvents.add(new Event(
                "Выставка Mist 0+",
                "Проект исследует границы восприятия и памяти, предлагая зрителю увидеть мир в состоянии между очевидностью. (от 100 до 200 рублей)",
                "7.11.2025 - 11.01.2026",
                "вт–вс 12:00–20:00",
                "ул. Жуковского, д. 28",
                "Санкт-Петербург"
        ));

        allEvents.add(new Event(
                "Выставка «Жилой среде — комфорт и уют» 0+",
                "Экспозиция знакомит с наследием выдающегося ленинградского архитектора и художника Евгения Полторацкого(от 150 до 250 рублей)",
                "24.10.2025 - 1.02.2026",
                "пн, чт–вс 11:00–18:00",
                "Петропавловская крепость, д. 3",
                "Санкт-Петербург"
        ));




        filteredEvents = new ArrayList<>(allEvents);
    }

    private void setupSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cities
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(spinnerAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = cities[position];
                filterEvents(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new EventAdapter(filteredEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void filterEvents(String selectedCity) {
        filteredEvents.clear();

        if (selectedCity.equals("Все города")) {
            // Показываем все события
            filteredEvents.addAll(allEvents);
        } else {
            // Фильтруем по выбранному городу
            for (Event event : allEvents) {
                if (event.getCity().equals(selectedCity)) {
                    filteredEvents.add(event);
                }
            }
        }

        // ОБНОВЛЯЕМ АДАПТЕР
        adapter.updateEvents(filteredEvents);
    }
}