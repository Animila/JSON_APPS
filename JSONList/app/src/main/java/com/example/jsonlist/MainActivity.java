package com.example.jsonlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Виджеты
    ListView lists;

    // JSON STRING

    String json_string =
        "   {\n" +
        "       \"title\":\"Урок JSON парсинга\",\n"+
        "       \"array\":[\n" +
        "           {\n" +
        "               \"company\":\"Google\"\n" +
        "           },\n" +
        "           {\n" +
        "               \"company\":\"Facebook\"\n" +
        "           },\n" +
        "           {\n" +
        "              \"company\":\"LinkedIn\"\n" +
        "           },\n" +
        "           {\n" +
        "               \"company\":\"Microsoft\"\n" +
        "           },\n" +
        "           {\n" +
        "               \"company\":\"Apple\"\n" +
        "           }\n" +
        "       ],\n" +
        "       \"nested\":{\n" +
        "           \"flag\": true,\n" +
        "           \"random_number\":1\n" +
        "       }\n" +
        "   }";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получение объектов JSON
        try {
            lists = findViewById(R.id.list_view);

            // 1. Создание списка для хранения элементов
            List<String> items = new ArrayList<>();

            // 2. Создание JSON объекта
            JSONObject root = new JSONObject(json_string);

            // 3. Получения данных массива из объекта
            JSONArray array = root.getJSONArray("array");
            Log.d("prog", ""+array);

            // 4. Получим и установим заголовок для приложения
            this.setTitle(root.getString("title"));

            // 5. Цикл для получения объектов массива
            for (int i = 0; i < array.length(); i++) {
                JSONObject objects = array.getJSONObject(i);
                items.add(objects.getString("company"));
            }

            // 6. Создадим адаптер для элементов listview
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

            if(lists != null) {
                lists.setAdapter(adapter);
            }

            // Получение вложенных объектов из базового объекта
            JSONObject nested = root.getJSONObject("nested");
            Log.d("TAGS", "flag value " + nested.getBoolean("flag"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}