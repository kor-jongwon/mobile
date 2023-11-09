package com.example.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SensorRegisterList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_register_list); // 레이아웃 파일의 이름에 맞게 변경해야 합니다.

        // 여기에 화면 초기화 및 기타 동작을 설정할 수 있습니다.
        LinearLayout containerLayout = findViewById(R.id.container_layout);

// 센서 리스트에서 요소 수를 가져온다고 가정

        //int sensorCount = getSensorCount();
        int plantCount = 10;

        for (int i = 0; i < plantCount; i++) {
            // 새로운 뷰를 인플레이트하고 설정
            View sensorView = getLayoutInflater().inflate(R.layout.sensor_item, null);
            // 이미지 뷰에 이미지 설정
            // 마진 설정
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            layoutParams.setMargins(0, 20, 0, 20); // 여기에서 마진을 조절하세요 (좌, 상, 우, 하)

            sensorView.setLayoutParams(layoutParams);
            ImageView sensorImageView = sensorView.findViewById(R.id.sensorImageView);
            sensorImageView.setImageResource(R.drawable.default_plant_img);

// 센서 이름 설정
            TextView sensorNameTextView = sensorView.findViewById(R.id.sensorNameTextView);
            sensorNameTextView.setText("Sensor Name");
            // 뷰를 컨테이너 레이아웃에 추가
            containerLayout.addView(sensorView);
        }

    }
    public void onAddButtonClick_Home(View view) {

        // 현재의 액티비티 컨텍스트인 SensorRegisterList에서 시작하여 SensorRegisterActivity로 인텐트를 생성
        Intent intent = new Intent(SensorRegisterList.this,  Plant_RegisterList.class);

        // 인텐트를 시작하여 새 액티비티로 이동
        startActivity(intent);

        // 버튼 클릭 시 실행할 로직
    }
    // 센서 추가 버튼이 클릭됐을 때 호출될 메소드
    public void onAddButtonClick_Register(View view) {

        // 현재의 액티비티 컨텍스트인 SensorRegisterList에서 시작하여 SensorRegisterActivity로 인텐트를 생성
        Intent intent = new Intent(SensorRegisterList.this, SensorRegisterActivity.class);

        // 인텐트를 시작하여 새 액티비티로 이동
        startActivity(intent);

        // 버튼 클릭 시 실행할 로직
    }
}
