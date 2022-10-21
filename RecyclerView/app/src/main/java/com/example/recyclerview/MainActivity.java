package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1. Виджеты
    RecyclerView recyclerView;

    // 2. Копирование файла JSON в наш каталог ассетов

    // 3. Анализ файла JSON
    ArrayList<String> personaNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // 4. Настройка RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // 5. Получим JSON
        try {
            // получаем JSON объект из JSON файл
            JSONObject obj = new JSONObject(loadJSONfromAssets());

            // 7 - Извлекем массив пользователей
            JSONArray userArray = obj.getJSONArray("users");

            // 8 - Создадим цикл для получения каждого пользователя
            for(int i=0; i<userArray.length(); i++){

                // 9 - Создадим объект JSON для получения данных об одном пользователе
                JSONObject userDetail = userArray.getJSONObject(i);

                // 10 - Вытащим и добавим в массив имя, почту
                personaNames.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));

                // 11 - получим объект контактных данных
                JSONObject contact =  userDetail.getJSONObject("contact");

                // 12 - Вытащим мобильный телефон и адрес в массив
                mobileNumbers.add(contact.getString("mobile"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 13 - Вызовем CustomAdapter для вывода информации
        CustomAdapter customAdapter = new CustomAdapter(personaNames, emailIds, mobileNumbers, MainActivity.this);
        recyclerView.setAdapter(customAdapter);
    }
    // 6. Создадим метод для загрузки JSON из Ассетов
    private String loadJSONfromAssets() {
        String json = null;
        try {
            InputStream is = getAssets().open("accounts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}