package com.example.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Register_plant extends Activity {

    private ImageView imageViewPlant;
    private EditText editTextPlantName;
    private DatePicker datePickerPlantDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_plant);

        imageViewPlant = findViewById(R.id.imageViewPlant);
        editTextPlantName = findViewById(R.id.editTextPlantName);
        datePickerPlantDate = findViewById(R.id.date_picker_textview);
    }

    // 이미지를 선택하는 메서드 (이미지 처리는 추가 작업이 필요합니다)
    public void selectImage(View view) {
        // 이미지 선택 로직을 추가하세요.
        Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
    }

    // 저장 버튼을 눌렀을 때 호출되는 메서드 (데이터 저장 로직을 추가하세요)
    public void savePlant(View view) {
        String plantName = editTextPlantName.getText().toString();
        int year = datePickerPlantDate.getYear();
        int month = datePickerPlantDate.getMonth() + 1; // 월은 0부터 시작하므로 1을 더합니다.
        int day = datePickerPlantDate.getDayOfMonth();

        // 데이터 저장 로직을 추가하세요.
        // 예: 데이터베이스에 저장하거나 다른 작업 수행

        Toast.makeText(this, "식물 이름: " + plantName + "\n심은 날짜: " + year + "년 " + month + "월 " + day + "일", Toast.LENGTH_SHORT).show();


    }
}