package com.example.mobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlantDetailActivity extends AppCompatActivity {

    private ImageView plantImageView;
    private TextView plantNameTextView;
    private TextView plantDateTextView;
    private TextView plantNumberTextView;
    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView moistureTextView;
    private EditText memoEditText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_detail); // xml 파일 이름

        initializeViews();

        // API에서 정보를 불러온다고 가정
        fetchPlantDetails();

        // 툴바 설정
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 툴바의 왼쪽에 홈으로 돌아가는 버튼
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 툴바의 오른쪽에 수정 버튼 (예: menu item으로 가정)
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_edit) {
                    // 수정 코드 작성
                    editPlantDetails();
                    return true;
                }
                return false;
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
        toolbar = findViewById(R.id.toolbar); // toolbar의 id를 가정함
    }

    // API로부터 정보를 불러와 각 뷰에 정보를 설정하는 가상의 메서드
    private void fetchPlantDetails() {
        // 예: API 응답으로부터 식물의 정보를 가져왔다고 가정
        String plantName = "○○○○";
        String plantDate = "2023/07/01";
        String plantNumber = "xxx-xxx1234";
        String temperature = "27°C";
        String humidity = "60%";
        String moisture = "70%";
        String memo = "메모 내용";

        // 뷰에 정보 설정
        plantNameTextView.setText("식물 이름 : " + plantName);
        plantDateTextView.setText("심은 날짜 : " + plantDate);
        plantNumberTextView.setText("전화번호 : " + plantNumber);
        temperatureTextView.setText("현재 온도 : " + temperature);
        humidityTextView.setText("현재 습도 : " + humidity);
        moistureTextView.setText("땅 습도 : " + moisture);
        memoEditText.setText(memo);
    }

    // 식물 정보를 수정하는 가상의 메서드
    private void editPlantDetails() {
        // 여기에 수정 로직 작성
        // 예: 메모 내용을 수정하게 되면 API 호출로 정보 업데이트
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plant_detail, menu);
        return true;
    }
}