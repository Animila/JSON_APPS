package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private Context movieContext;
    private List<MovieModelClass> movieData;

    public AdapterMovie(Context movieContext, List<MovieModelClass> movieData) {
        this.movieContext = movieContext;
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(movieContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(movieData.get(position).getId());
        holder.name.setText(movieData.get(position).getName());

        // Воспользуемся библиотекой Glide для отображения url картинки
        Glide.with(movieContext)
                .load(movieData.get(position).getImage_url())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }


    // расширяем стандартный RecyclerView собстенный MyViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.name_txt);
            name = itemView.findViewById(R.id.id_txt);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
