package com.example.mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {

    private List<Plant> plants;
    private Context context;

    public PlantsAdapter(Context context, List<Plant> plants) {
        this.context = context;
        this.plants = plants;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plants.get(position);
        holder.plantNameTextView.setText(plant.getName());
        holder.plantDateTextView.setText(plant.getDate());

        // glide로 이미지 불러오는 작업인데 아직 못하는중 getDateGlide.with(context).load(plant.getImageUrl()).into(holder.plantImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlantDetailActivity.class);
                intent.putExtra("plantId", plant.getPlantId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImageView;
        TextView plantNameTextView;
        TextView plantDateTextView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImageView = itemView.findViewById(R.id.plantImageView);
            plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
            plantDateTextView = itemView.findViewById(R.id.plantDateTextView);
        }
    }
}
