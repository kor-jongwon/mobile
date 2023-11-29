package com.example.mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener listener;

    public void setPlants(List<Plant> newPlants) {
        this.plants = newPlants;
        notifyDataSetChanged(); // 데이터가 변경되었음을 알림
    }

    // 클릭 이벤트를 처리하기 위한 인터페이스
    public interface OnItemClickListener {
        void onItemClick(Plant plant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlantsAdapter() {
        this.plants = plants;
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {
        Plant plant = plants.get(position);
        holder.plantName.setText(plant.getPlantName());
        // 이미지 로딩 로직 추가 (예: Glide 라이브러리 사용)
        // 예: Glide.with(holder.itemView.getContext()).load(plant.getImageUrl()).into(holder.plantImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(plant);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return plants != null ? plants.size() : 0;
    }

    // ViewHolder 클래스
    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        public TextView plantName;
        public ImageView plantImage;

        public PlantViewHolder(View itemView) {
            super(itemView);
            plantName = itemView.findViewById(R.id.plantName);
            plantImage = itemView.findViewById(R.id.plantImage);
        }
    }
}