package com.example.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Plant_RegisterList extends Activity {

    private RecyclerView recyclerView;
    private PlantsAdapter adapter;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_register_list);

        recyclerView = findViewById(R.id.plantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton.findViewById(R.id.button_plant_add);

        adapter = new PlantsAdapter();
        adapter.setOnItemClickListener(new PlantsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                new GetPlantByNameTask().execute(plant.getPlantName());
            }
        });

        recyclerView.setAdapter(adapter);

        // 서버로부터 식물 목록 가져오기
        String url = api_url.GETPLANTS.getValue();
        new GetPlantsTask().execute(url);
    }

    // 식물 목록을 가져오는 AsyncTask
    private class GetPlantsTask extends AsyncTask<String, Void, List<Plant>> {
        @Override
        protected List<Plant> doInBackground(String... urls) {
            return new PlantsHttpRequest().getPlants(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Plant> plants) {
            if (plants != null) {
                adapter.setPlants(plants);
            } else {
                Toast.makeText(Plant_RegisterList.this, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 식물 상세 정보를 가져오는 AsyncTask
    private class GetPlantByNameTask extends AsyncTask<String, Void, Plant> {
        @Override
        protected Plant doInBackground(String... plantNames) {
            return new PlantDetailsHttpRequest().getPlantByName(plantNames[0]);
        }

        @Override
        protected void onPostExecute(Plant plant) {
            if (plant != null) {
                Intent intent = new Intent(Plant_RegisterList.this, PlantDetailActivity.class);
                intent.putExtra("plantName", plant.getPlantName());
                intent.putExtra("plantDating", plant.getPlantDating());
                startActivity(intent);
            } else {
                Toast.makeText(Plant_RegisterList.this, "식물 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onAddButtonClick_Register(View view) {
        Intent intent = new Intent(Plant_RegisterList.this, RegisterPlantActivity.class);
        startActivity(intent);

    }

    public void onAddButtonClick_Home(View view) {
        Intent intent = new Intent(Plant_RegisterList.this,  HomeActivity.class);
        startActivity(intent);

    }

    public void onAddButtonClick_SensorControl(View view) {
        Intent intent = new Intent(Plant_RegisterList.this, SensorRegisterList.class);
        startActivity(intent);
    }

}
