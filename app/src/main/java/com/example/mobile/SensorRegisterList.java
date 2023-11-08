package com.example.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SensorRegisterList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_register_list); // 레이아웃 파일의 이름에 맞게 변경해야 합니다.

        // 여기에 화면 초기화 및 기타 동작을 설정할 수 있습니다.
    }

    // 센서 추가 버튼이 클릭됐을 때 호출될 메소드
    public void onAddButtonClick(View view) {

        // 현재의 액티비티 컨텍스트인 SensorRegisterList에서 시작하여 SensorRegisterActivity로 인텐트를 생성
        Intent intent = new Intent(SensorRegisterList.this, SensorRegisterActivity.class);

        // 인텐트를 시작하여 새 액티비티로 이동
        startActivity(intent);

        // 버튼 클릭 시 실행할 로직
    }
}
