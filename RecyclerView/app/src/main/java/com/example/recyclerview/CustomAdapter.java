package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    // 1 - Создадим массив для вывода
    ArrayList<String> personNames;
    ArrayList<String> emailIds;
    ArrayList<String> mobileNumbers;

    Context ctx;


    // 2 - Создадим конструктор

    public CustomAdapter(ArrayList<String> personNames, ArrayList<String> emailIds, ArrayList<String> mobileNumbers, Context ctx) {
        this.personNames = personNames;
        this.emailIds = emailIds;
        this.mobileNumbers = mobileNumbers;
        this.ctx = ctx;
    }

    // 3 - viewHolders

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Реализация элементов листа
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // установким данные в элементы
        holder.name.setText(personNames.get(position));
        holder.email.setText(emailIds.get(position));
        holder.mobileNo.setText(mobileNumbers.get(position));

        // Добавим событие onClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Выведем сообщение о имени
                Toast.makeText(ctx, personNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    // 4 - Создадим класс и конструктор
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Виджеты
        TextView name, email, mobileNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobileNo = itemView.findViewById(R.id.mobileNO);
        }
    }
}
