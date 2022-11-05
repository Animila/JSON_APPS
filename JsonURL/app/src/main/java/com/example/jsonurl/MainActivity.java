package com.example.jsonurl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // ПЕРЕД РАБОТОЙ НАСТРОИТЬ ЭМУЛЯТОР ДЛЯ РАБОТЫ С СЕТЬЮ
    // ИЛИ
    // ЗАПУСКАТЬ НА СВОЕМ ТЕЛЕФОНЕ

    private ListView lv;
    String name, age;
    private static String JSON_URL = "https://run.mocky.io/v3/b815de0b-6c03-4a36-a9d2-ed428cd488cc";
    ArrayList<HashMap<String,String>> friendsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendsList = new ArrayList<>();
        lv = findViewById(R.id.listview);

        // создадим экземпляр асинхронного процесса
        GetData getData = new GetData();
        getData.execute();


    }


    // Асинхронная задача
    // Выполнение задач в фоновом режиме
    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            // устанавливаем связь
            try {

                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    // инициализируем URL
                    url = new URL(JSON_URL);
                    // инифициализируем соединение
                    urlConnection = (HttpURLConnection) url.openConnection();
                    // Инициализируем входной поток данных из соединения
                    InputStream in =  urlConnection.getInputStream();
                    // Прочитываем все что происходит в потоке данных
                    InputStreamReader isr = new InputStreamReader(in);
                    // Получаем данные
                    int data = isr.read();
                    Log.d("myLog", ""+data);

                    // данные не пустые
                    while (data != -1) {
                        // записываем каждый символ в единую строку
                        current += (char) data;
                        data = isr.read();
                    }
                    //возвращаем строку
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    // ОБЯЗАТЕЛЬНО ЗАКРЫВАТЬ СОЕДИНЕНИЕ!
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                } catch(Exception e) {
                e.printStackTrace();

            }
            return current;

        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Friends");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    name = jsonObject1.getString("name");
                    age = jsonObject1.getString("age");

                    // Создаем карту строки
                    HashMap<String, String> friends = new HashMap<>();

                    friends.put("name", name);
                    friends.put("age", age);
                    friendsList.add(friends);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // отобразим результаты
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    friendsList,
                    R.layout.row_layout,
                    new String[] {"name", "age"},
                    new int[] {R.id.head, R.id.age}
            );
            lv.setAdapter(adapter);
        }
    }

}