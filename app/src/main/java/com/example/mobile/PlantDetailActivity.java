package com.example.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PlantDetailActivity extends AppCompatActivity {

    private ImageView plantImageView;
    private TextView plantNameTextView;
    private TextView plantDateTextView;
    private TextView plantNumberTextView;
    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView moistureTextView;
    private EditText memoEditText;
    private Button buttonEdit;
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_detail);

        initializeViews();
        fetchPlantDetails();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPlantDetails();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlantDetails();
            }
        });
    }

    private void initializeViews() {
        plantImageView = findViewById(R.id.plantImageView);
        plantNameTextView = findViewById(R.id.plantNameTextView);
        plantDateTextView = findViewById(R.id.plantDateTextView);
        plantNumberTextView = findViewById(R.id.plantNumberTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        moistureTextView = findViewById(R.id.moistureTextView);
        memoEditText = findViewById(R.id.memoEditText);
        buttonEdit = findViewById(R.id.button_edit);
        buttonDelete = findViewById(R.id.button_delete);
    }

    private void fetchPlantDetails() {
        //todo 식물 정보 불러오기 로직

    }

    private void editPlantDetails() {
        UpdatePlant_RequestHttpURLConnection httpURLConnection = new UpdatePlant_RequestHttpURLConnection();

        String url = api_url.UPDATEPLANT.getValue();
        Map<String, String> params = new HashMap<>();
        params.put("plantId", "식물 ID");
        params.put("newPlantData", "새로운 식물 데이터");

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return httpURLConnection.requestPost(url, params);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    Toast.makeText(PlantDetailActivity.this, "식물 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlantDetailActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void deletePlantDetails() {
        DeletePlant_RequestHttpURLConnection httpURLConnection = new DeletePlant_RequestHttpURLConnection();

        String url = api_url.DELETEPLANT.getValue();
        Map<String, String> params = new HashMap<>();
        params.put("plantId", "식물 ID");

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return httpURLConnection.requestDelete(url, params);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    Toast.makeText(PlantDetailActivity.this, "식물이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlantDetailActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}