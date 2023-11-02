package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlantActivity extends AppCompatActivity {
    private RecyclerView plantsRecyclerView;
    private PlantsAdapter plantsAdapter;
    private List<Plant> plants;
    private Toolbar toolbar;
    private Button addPlantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 홈 아이콘 설정
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons_plant);

        addPlantButton = findViewById(R.id.addPlantButton);
        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantActivity.this, RegisterPlantActivity.class); // RegisterPlantActivity는 register_plant.xml을 사용하는 액티비티의 이름입니다.
                startActivity(intent);
            }
        });

        plantsRecyclerView = findViewById(R.id.plantsRecyclerView);
        plants = new ArrayList<>();
        //plants 에다가 api 넣기

        plantsAdapter = new PlantsAdapter(this, plants);
        plantsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantsRecyclerView.setAdapter(plantsAdapter);

        // BottomNavigationView 클릭 이벤트
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_button1:
                        Toast.makeText(PlantActivity.this, "버튼1 클릭", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_button2:
                        Toast.makeText(PlantActivity.this, "버튼2 클릭", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_button3:
                        Toast.makeText(PlantActivity.this, "버튼3 클릭", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_button4:
                        Toast.makeText(PlantActivity.this, "버튼4 클릭", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    // home 버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // 홈 액티비티로 이동하는 코드 추가
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
