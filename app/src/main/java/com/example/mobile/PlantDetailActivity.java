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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        temperatureTextView = findViewById(R.id.temperatureTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        moistureTextView = findViewById(R.id.moistureTextView);
        memoEditText = findViewById(R.id.memoEditText);
        buttonEdit = findViewById(R.id.button_edit);
        buttonDelete = findViewById(R.id.button_delete);
    }

    private void fetchPlantDetails() {
        // Intent로부터 식물 정보를 받아온다고 가정
        String plantName = getIntent().getStringExtra("plantName");
        String plantDating = getIntent().getStringExtra("plantDating");

        // UI에 식물 정보 표시
        plantNameTextView.setText("식물 이름 : " + plantName);
        plantDateTextView.setText("심은 날짜 : " + plantDating);

        // TODO: 온도, 습도, 땅 습도 등 추가 정보 설정 필요
    }

    private void editPlantDetails() {
        OkHttpClient client = new OkHttpClient();
        String url = api_url.UPDATEPLANT.getValue();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("newPlantName", plantNameTextView.getText().toString())
                .add("newPlantDating", plantDateTextView.getText().toString())
                .add("newImage", ""); // 혹은 null 대신 실제 이미지 데이터

        Request request = new Request.Builder()
                .url(url)
                .put(formBuilder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // 에러 처리
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                runOnUiThread(() -> {
                    try {
                        String result = response.body().string();
                        // 결과에 따라 UI 업데이트
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private void deletePlantDetails() {
        OkHttpClient client = new OkHttpClient();
        String url = api_url.DELETEPLANT.getValue();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("plantId", "식물 ID");

        Request request = new Request.Builder()
                .url(url)
                .delete(formBuilder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // 에러 처리
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                runOnUiThread(() -> {
                    try {
                        String result = response.body().string();
                        // 결과에 따라 UI 업데이트
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}