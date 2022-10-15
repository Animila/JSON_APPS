package com.example.jsonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Виджеты
    TextView name;
    TextView salary;
    String JSON_STRING = "{\"employee\":{\"name\": \"Илья Христофоров\", \"salary\":5000}}";

    String getName, getSalary;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //получение данных из textview
        name = findViewById(R.id.name);
        salary = findViewById(R.id.salary);

        //получение объектов JSON
        try {
            //вытаскиваем JSON объекты из JSON файла
            JSONObject obj = new JSONObject(JSON_STRING);

            //вытаскиваем информацию об работнике
            JSONObject employee = obj.getJSONObject("employee");

            //получим информацию о имени и зарплате сотрудника
            getName = employee.getString("name");
            getSalary = employee.getString("salary");

            //Выводим значения в textView
            name.setText("Имя: " + getName);
            salary.setText("Зарплата: " + getSalary);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}