package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1 - генерация JSON ссыллки
    private static String JSON_URL = "https://run.mocky.io/v3/b7f77fbd-4c14-473b-adf4-9aa35124d7e7";

    List<MovieModelClass> movieList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);


        GetData getData = new GetData();
        getData.execute();

    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("myLog", "Начинаем загрузку");

            try {
                Log.d("myLog", "Загружаем JSON");
                JSONObject jsonObject = new JSONObject(s);
                JSONArray movies = jsonObject.getJSONArray("Movies");

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject jsonObject1 = movies.getJSONObject(i);
                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("name"));
                    model.setImage_url(jsonObject1.getString("image"));

                    movieList.add(model);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecuclerView(movieList);
        }
    }

    private void PutDataIntoRecuclerView(List<MovieModelClass> movieList) {
        AdapterMovie adapterMovie = new AdapterMovie(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapterMovie);
    }
}