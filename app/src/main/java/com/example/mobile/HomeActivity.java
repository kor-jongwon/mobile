package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    //Todo:
    //메인화며기능구현

    public Button btn_register_plant;
    public Button btn_sensor_control;// 센서 제어 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_register_plant = findViewById(R.id.registerButton);
        btn_register_plant.setVisibility(View.VISIBLE);
        btn_register_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegister_plant();
            }

        });

        btn_sensor_control = findViewById(R.id.button_sensor);
        btn_sensor_control.setVisibility(View.VISIBLE);

        btn_sensor_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegister_plant();
            }
        });

    }

    private void launchRegister_plant() {
        // "아이디 찾기" 텍스트를 클릭했을 때 호출되는 메서드
        Intent intent = new Intent(this, RegisterPlantActivity.class);
        startActivity(intent);
    }
    private void launchSensorRegisterActivity() {
        // "아이디 찾기" 텍스트를 클릭했을 때 호출되는 메서드
        Intent intent = new Intent(this, SensorRegisterActivity.class);
        startActivity(intent);
    }
}